package Game.Facade;

import CustomIterator.UnitTypeIterator;
import Game.GameBoard;
import ObjectPool.UnitPool;
import Unit.UnitType;
import Unit.Unit;

import java.util.List;

public class UnitInitializerFacade {
    private final GameBoard gameBoard;
    private final UnitPool unitPool;

    public UnitInitializerFacade() {
        this.gameBoard = GameBoard.getInstance();
        this.unitPool = UnitPool.getInstance();
    }

    public void initializeUnits(List<UnitType> unitTypes, boolean isEnemy) {
        UnitTypeIterator unitTypeIterator = new UnitTypeIterator(unitTypes);

        while (unitTypeIterator.hasNext()) {
            UnitType unitType = unitTypeIterator.next();
            Unit unit = unitPool.getUnit(unitType, isEnemy);
            gameBoard.addUnit(unit);
        }
    }
}