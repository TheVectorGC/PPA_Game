package Game;

import Game.Memento.GameHistory;
import Game.SaveLoad.DTO.GameSaveDTO;
import Game.SaveLoad.GameStateMapper;
import Game.SaveLoad.SaveLoadManager;
import Game.Strategy.GameTurnStrategy;
import Game.Strategy.ManualTurnStrategy;
import Unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class GameBoard {
    private static GameBoard instance;
    private List<Unit> yourUnits;
    private List<Unit> enemyUnits;

    private int yourUnitIndex = 0;
    private int enemyUnitIndex = 0;
    private boolean isYourUnitTurn = true;
    private int turnCounter = 0;
    private final GameHistory gameHistory = new GameHistory();
    private boolean gameOver = false;
    private final List<TurnListener> listeners = new CopyOnWriteArrayList<>();

    private GameBoard() {
        yourUnits = new ArrayList<>();
        enemyUnits = new ArrayList<>();
    }

    public static GameBoard getInstance() {
        if (instance == null) {
            instance = new GameBoard();
        }
        return instance;
    }

    public int stateHash() {
        return Objects.hash(
                yourUnitIndex,
                enemyUnitIndex,
                isYourUnitTurn,
                turnCounter,
                yourUnits.stream().map(Unit::stateHash).collect(Collectors.toList()),
                enemyUnits.stream().map(Unit::stateHash).collect(Collectors.toList())
        );
    }

    public interface TurnListener {
        void onBeforeAct(Unit actingUnit, boolean isYourTurn);
        void onAfterAct(Unit actedUnit, boolean isYourTurn);
        void onGameOver(boolean yourWon);
    }

    public void addTurnListener(TurnListener listener) {
        listeners.add(listener);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void executeTurnWithSave() {
        executeTurn();
    }

    public void executeTurn() {
        if (gameOver) return;

        turnCounter++;

        Optional<Unit> optActiveUnit = getActiveUnit();
        if (optActiveUnit.isEmpty()) {
            return;
        }
        Unit currentUnit = optActiveUnit.get();
        listeners.forEach(l -> l.onBeforeAct(currentUnit, isYourUnitTurn));
        currentUnit.act();
        listeners.forEach(l -> l.onAfterAct(currentUnit, isYourUnitTurn));

        incrementUnitIndex(isYourUnitTurn);
        isYourUnitTurn = !isYourUnitTurn;

        saveCurrentState();
    }

    public void undo() {
        if (gameHistory.canUndo()) {
            GameSaveDTO dto = gameHistory.undo();
            if (dto != null) {
                GameStateMapper.fromGameStateDTO(dto);

                int currentHash = stateHash();
                if (currentHash == dto.expectedStateHash()) {
                    GameLogger.addLogEntry("← ОТМЕНА ХОДА → (состояние корректно)");
                }
                else {
                    GameLogger.addLogEntry("← ОТМЕНА ХОДА → (ОШИБКА: состояние не совпадает!)");
                    GameLogger.addLogEntry("  Ожидаемый хэш: " + dto.expectedStateHash());
                    GameLogger.addLogEntry("  Фактический хэш: " + currentHash);
                }
            }
        }
        else {
            GameLogger.addLogEntry("← НЕТ СОСТОЯНИЙ ДЛЯ ОТМЕНЫ →");
        }
    }

    public void redo() {
        if (gameHistory.canRedo()) {
            GameSaveDTO dto = gameHistory.redo();
            if (dto != null) {
                GameStateMapper.fromGameStateDTO(dto);

                int currentHash = stateHash();
                if (currentHash == dto.expectedStateHash()) {
                    GameLogger.addLogEntry("→ ПОВТОР ХОДА → (состояние корректно)");
                }
                else {
                    GameLogger.addLogEntry("→ ПОВТОР ХОДА → (ОШИБКА: состояние не совпадает!)");
                    GameLogger.addLogEntry("  Ожидаемый хэш: " + dto.expectedStateHash());
                    GameLogger.addLogEntry("  Фактический хэш: " + currentHash);
                }
            }
        }
        else {
            GameLogger.addLogEntry("→ НЕТ СОСТОЯНИЙ ДЛЯ ПОВТОРА →");
        }
    }

    public void saveGame(String name) {
        SaveLoadManager.saveGame(name);
    }

    public void loadGame(String saveName) {
        SaveLoadManager.loadGame(saveName);
    }

    public void saveCurrentState() {
        GameSaveDTO dto = GameStateMapper.toGameStateDTO();
        gameHistory.saveState(dto);
    }

    public void game() {
        setSquadPositions(true);
        setSquadPositions(false);
        saveCurrentState();
        GameTurnStrategy gameTurnStrategy = new ManualTurnStrategy();
        gameTurnStrategy.execute();
    }

    public void addUnit(Unit unit) {
        if (unit.isEnemy()) enemyUnits.add(unit);
        else yourUnits.add(unit);
    }

    public void addUnit(Unit unit, int position) {
        if (unit.isEnemy()) enemyUnits.add(position - 1, unit);
        else yourUnits.add(position - 1, unit);
    }

    public Optional<Unit> getActiveUnit() {
        final boolean validUnitIndex = isYourUnitTurn ? yourUnits.size() > yourUnitIndex : enemyUnits.size() > enemyUnitIndex;
        if (!validUnitIndex) {
            return Optional.empty();
        }
        return isYourUnitTurn ? Optional.of(yourUnits.get(yourUnitIndex)) : Optional.of(enemyUnits.get(enemyUnitIndex));
    }

    public Unit getUnit(int position, boolean isEnemy) {
        try {
            if (isEnemy) return enemyUnits.get(position - 1);
            else return yourUnits.get(position - 1);
        }
        catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public int getLastPosition(boolean isEnemy) {
        if (isEnemy) return Math.min(enemyUnits.size(), 4);
        else return Math.min(yourUnits.size(), 4);
    }

    public int getArmySize(boolean isEnemy) {
        return isEnemy ? enemyUnits.size() : yourUnits.size();
    }

    public void setSquadPositions(boolean isEnemy) {
        for (int i = 0; i < (isEnemy ? Math.min(enemyUnits.size(), 4) : Math.min(yourUnits.size(), 4)); i++) {
            if (isEnemy) enemyUnits.get(i).setPosition(i + 1);
            else yourUnits.get(i).setPosition(i + 1);
        }
    }

    public void buryTheDead(Unit unit) {
        if (unit == null) return;

        List<Unit> list = unit.isEnemy() ? enemyUnits : yourUnits;
        list.remove(unit);
        setSquadPositions(unit.isEnemy());

        if (yourUnits.isEmpty() || enemyUnits.isEmpty()) {
            gameOver = true;
            boolean yourWon = !yourUnits.isEmpty();
            listeners.forEach(l -> l.onGameOver(yourWon));
        }
    }

    public void resetGameState() {
        yourUnits.clear();
        enemyUnits.clear();
        turnCounter = 0;
        yourUnitIndex = 0;
        enemyUnitIndex = 0;
        isYourUnitTurn = true;
        gameOver = false;
        gameHistory.clearAll();
    }

    private void incrementUnitIndex(boolean isYourTurn) {
        if (isYourTurn) {
            int maxUnits = Math.min(yourUnits.size(), 4);
            yourUnitIndex = maxUnits == 0 ? 0 : (yourUnitIndex + 1) % maxUnits;
        } else {
            int maxUnits = Math.min(enemyUnits.size(), 4);
            enemyUnitIndex = maxUnits == 0 ? 0 : (enemyUnitIndex + 1) % maxUnits;
        }
    }

    public List<Unit> getYourUnits() { return yourUnits; }
    public void setYourUnits(List<Unit> yourUnits) { this.yourUnits = yourUnits; }
    public List<Unit> getEnemyUnits() { return enemyUnits; }
    public void setEnemyUnits(List<Unit> enemyUnits) { this.enemyUnits = enemyUnits; }
    public int getYourUnitIndex() { return yourUnitIndex; }
    public void setYourUnitIndex(int yourUnitIndex) { this.yourUnitIndex = yourUnitIndex; }
    public int getEnemyUnitIndex() { return enemyUnitIndex; }
    public void setEnemyUnitIndex(int enemyUnitIndex) { this.enemyUnitIndex = enemyUnitIndex; }
    public boolean isYourUnitTurn() { return isYourUnitTurn; }
    public void setYourUnitTurn(boolean isYourUnitTurn) { this.isYourUnitTurn = isYourUnitTurn; }
    public int getTurnCounter() { return turnCounter; }
    public void setTurnCounter(int turnCounter) { this.turnCounter = turnCounter; }
}