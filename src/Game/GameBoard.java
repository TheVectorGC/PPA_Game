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

    public void addUnit(Unit unit, boolean isEnemy, int position) {
        if (isEnemy) enemyUnits.add(enemyUnits.size() - position, unit);
        else yourUnits.add(yourUnits.size() - position, unit);
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
        for (int i = 1; i < (isEnemy ? Math.min(enemyUnits.size(), 4) : Math.min(yourUnits.size(), 4)) + 1; i++) {
            if (isEnemy) enemyUnits.get(enemyUnits.size() - i).setPosition(i);
            else yourUnits.get(enemyUnits.size() - i).setPosition(i);
        }
    }

    public void buryTheDead(Unit unit) {
        if (unit.isEnemy()) {
            enemyUnits.remove(enemyUnits.size() - unit.getPosition());
            setSquadPositions(true);
        }
        else {
            yourUnits.remove(enemyUnits.size() - unit.getPosition());
            setSquadPositions(false);
        }
    }
}
