package Game;
import Game.Strategy.AutoTurnStrategy;
import Game.Strategy.GameTurnStrategy;
import Game.Strategy.InstantResultStrategy;
import Game.Strategy.ManualTurnStrategy;
import Unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameBoard {
    private static GameBoard instance;
    private final List<Unit> yourUnits;
    private final List<Unit> enemyUnits;

    private int yourUnitIndex = 0;
    private int enemyUnitIndex = 0;
    private boolean isYourUnitTurn = true;

    private int turnCounter = 0;

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

    public void performSingleTurn() {
        if (gameOver) return;
        turnCounter++;

        var optActiveUnit = getActiveUnit();
        if (optActiveUnit.isEmpty()) {
            return;
        }

        var currentUnit = optActiveUnit.get();
        listeners.forEach(l -> l.onBeforeAct(currentUnit, isYourUnitTurn));
        currentUnit.act();
        listeners.forEach(l -> l.onAfterAct(currentUnit, isYourUnitTurn));

        incrementUnitIndex(isYourUnitTurn);
        isYourUnitTurn = !isYourUnitTurn;
    }

    private void incrementUnitIndex(boolean isYourTurn) {
        if (isYourTurn) { yourUnitIndex++; }
        else { enemyUnitIndex++; }

        if (yourUnitIndex >= yourUnits.size() || yourUnitIndex >= 4) {
            yourUnitIndex = 0;
        }
        if (enemyUnitIndex >= enemyUnits.size() || enemyUnitIndex >= 4) {
            enemyUnitIndex = 0;
        }
    }
      public void game() {
        instance.setSquadPositions(true);
        instance.setSquadPositions(false);
        GameTurnStrategy gameTurnStrategy = new ManualTurnStrategy();
      //  GameTurnStrategy gameTurnStrategy = new InstantResultStrategy();
      //  GameTurnStrategy gameTurnStrategy = new AutoTurnStrategy();
        gameTurnStrategy.execute();
    }

    public void addUnit(Unit unit) {
        if (unit.isEnemy()) enemyUnits.add(unit);
        else yourUnits.add(unit);
    }

    public void addUnit(Unit unit, boolean isEnemy, int position) {
        if (isEnemy) enemyUnits.add(position - 1, unit);
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
        } catch (IndexOutOfBoundsException e) {
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
    }
}
