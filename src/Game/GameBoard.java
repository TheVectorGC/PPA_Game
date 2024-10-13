package Game;
import Game.Strategy.AutoTurnStrategy;
import Game.Strategy.GameTurnStrategy;
import Game.Strategy.InstantResultStrategy;
import Game.Strategy.ManualTurnStrategy;
import Unit.Unit;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private static GameBoard instance;
    private final List<Unit> yourUnits;
    private final List<Unit> enemyUnits;

    private int yourUnitIndex = 0;
    private int enemyUnitIndex = 0;
    private boolean isYourUnitTurn = true;

    private int turnCounter = 0;

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

    public void performSingleTurn() {
        turnCounter++;
        if (isYourUnitTurn) {
            yourUnits.get(yourUnitIndex).act();
        }
        else {
            enemyUnits.get(enemyUnitIndex).act();
        }

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

    public Unit getUnit(int position, boolean isEnemy) {
        if (isEnemy) return enemyUnits.get(position - 1);
        else return yourUnits.get(position - 1);
    }

    public int getLastPosition(boolean isEnemy) {
        if (isEnemy) return Math.min(enemyUnits.size(), 4);
        else return Math.min(yourUnits.size(), 4);
    }

    public void setSquadPositions(boolean isEnemy) {
        for (int i = 0; i < (isEnemy ? Math.min(enemyUnits.size(), 4) : Math.min(yourUnits.size(), 4)); i++) {
            if (isEnemy) enemyUnits.get(i).setPosition(i + 1);
            else yourUnits.get(i).setPosition(i + 1);
        }
    }

    public void buryTheDead(Unit unit) {
        if (unit.isEnemy()) {
            enemyUnits.remove(unit.getPosition() - 1);
            setSquadPositions(true);
        }
        else {
            yourUnits.remove(unit.getPosition() - 1);
            setSquadPositions(false);
        }
        if (yourUnits.isEmpty() || enemyUnits.isEmpty()) {
            System.out.println("\n\nИГРА ЗАВЕРШЕНА\nКОЛИЧЕСТВО ХОДОВ: " + turnCounter);
            System.exit(0);
        }
    }
}
