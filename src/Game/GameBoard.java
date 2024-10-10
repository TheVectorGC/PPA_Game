package Game;
import Unit.Unit;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private static GameBoard instance;
    private final List<Unit> yourUnits;
    private final List<Unit> enemyUnits;

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

    public void game() {
        instance.setSquadPositions(true);
        instance.setSquadPositions(false);
        while (true) {
            for (int i = 0; i < 4; i++) {
                if (yourUnits.size() > i) {
                    yourUnits.get(i).act();
                }
                if (enemyUnits.size() > i) {
                    enemyUnits.get(i).act();
                }

            }
        }
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
        if (enemyUnits.size() == 0 || yourUnits.size() == 0) {
            System.exit(0);
        }
    }
}
