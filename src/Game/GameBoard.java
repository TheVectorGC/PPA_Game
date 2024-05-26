package Game;
import Units.Unit;

import java.util.ArrayList;

public class GameBoard {
    private static GameBoard instance;
    private final ArrayList<Unit> yourUnits;
    private final ArrayList<Unit> enemyUnits;

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

    public void addUnit(Unit unit, boolean isEnemy) {
        if (isEnemy) enemyUnits.add(unit);
        else yourUnits.add(unit);
    }
    public void setUnit(int position, Unit unit, boolean isEnemy) {
        if (isEnemy) {
            int unitPosition = enemyUnits.size() - position;
            enemyUnits.set(unitPosition, unit);
        }
        else {
            int unitPosition = yourUnits.size() - position;
            yourUnits.set(unitPosition, unit);
        }
    }
    public Unit getUnit(int position, boolean isEnemy) {
        if (isEnemy) {
            int unitPosition = enemyUnits.size() - position;
            return enemyUnits.get(unitPosition);
        }
        else {
            int unitPosition = yourUnits.size() - position;
            return yourUnits.get(unitPosition);
        }
    }

    public int getLastPosition(boolean isEnemy) {
        if (isEnemy) return Math.min(enemyUnits.size(), 4);
        else return Math.min(yourUnits.size(), 4);
    }

    public void setSquadPositions(boolean isEnemy) {
        for (int i = 1; i < (isEnemy ? enemyUnits.size() : yourUnits.size()) + 1; i++) {
            if (isEnemy) enemyUnits.get(enemyUnits.size() - i).setPosition(i);
            else yourUnits.get(enemyUnits.size() - i).setPosition(i);
        }
    }

    public void buryTheDead(boolean isEnemy) {
        if (isEnemy) {
            enemyUnits.remove(enemyUnits.size() - 1);
            setSquadPositions(true);
        }
        else {
            yourUnits.remove(enemyUnits.size() - 1);
            setSquadPositions(false);
        }
    }
}
