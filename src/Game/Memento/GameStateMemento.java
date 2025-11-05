package Game.Memento;

import Unit.Unit;
import java.util.ArrayList;
import java.util.List;

public record GameStateMemento(
        List<Unit> yourUnits,
        List<Unit> enemyUnits,
        int yourUnitIndex,
        int enemyUnitIndex,
        boolean isYourUnitTurn,
        int turnCounter
) {
    public GameStateMemento(List<Unit> yourUnits, List<Unit> enemyUnits, int yourUnitIndex,
                            int enemyUnitIndex, boolean isYourUnitTurn, int turnCounter) {
        this.yourUnits = copyUnits(yourUnits);
        this.enemyUnits = copyUnits(enemyUnits);
        this.yourUnitIndex = yourUnitIndex;
        this.enemyUnitIndex = enemyUnitIndex;
        this.isYourUnitTurn = isYourUnitTurn;
        this.turnCounter = turnCounter;
    }

    private List<Unit> copyUnits(List<Unit> units) {
        List<Unit> copy = new ArrayList<>();
        for (Unit unit : units) {
            copy.add(unit.clone());
        }
        return copy;
    }
}