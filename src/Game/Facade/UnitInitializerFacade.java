package Game.Facade;

import CustomIterator.UnitTypeIterator;
import Game.GameBoard;
import ObjectPool.UnitPool;
import Unit.Unit;
import Unit.UnitType;
import java.util.List;
import Exception.UnitTypeNotSupportedException;

public class UnitInitializerFacade {
    private static final GameBoard GAME_BOARD = GameBoard.getInstance();
    private static final UnitPool UNIT_POOL = UnitPool.getInstance();
    private static final Unit melee = UNIT_POOL.getUnit(UnitType.UNIT_MELEE, false);
    private static final Unit range = UNIT_POOL.getUnit(UnitType.UNIT_RANGE, false);
    private static final Unit heavy = UNIT_POOL.getUnit(UnitType.UNIT_HEAVY, false);
    private static final Unit wizard = UNIT_POOL.getUnit(UnitType.UNIT_WIZARD, false);

    private static final Unit meleeEnemy = UNIT_POOL.getUnit(UnitType.UNIT_MELEE, true);
    private static final Unit rangeEnemy = UNIT_POOL.getUnit(UnitType.UNIT_RANGE, true);
    private static final Unit heavyEnemy = UNIT_POOL.getUnit(UnitType.UNIT_HEAVY, true);
    private static final Unit wizardEnemy = UNIT_POOL.getUnit(UnitType.UNIT_WIZARD, true);

    public static void initializeUnits(List<UnitType> unitTypes, boolean isEnemy) {
        UnitTypeIterator unitTypeIterator = new UnitTypeIterator(unitTypes);

        while (unitTypeIterator.hasNext()) {
            UnitType unitType = unitTypeIterator.next();

            switch (unitType) {
                case UNIT_MELEE -> {
                    if (!isEnemy) {
                        GAME_BOARD.addUnit(melee.clone());
                    } else {
                        GAME_BOARD.addUnit(meleeEnemy.clone());
                    }
                }
                case UNIT_RANGE -> {
                    if (!isEnemy) {
                        GAME_BOARD.addUnit(range.clone());
                    } else {
                        GAME_BOARD.addUnit(rangeEnemy.clone());
                    }
                }
                case UNIT_HEAVY -> {
                    if (!isEnemy) {
                        GAME_BOARD.addUnit(heavy.clone());
                    } else {
                        GAME_BOARD.addUnit(heavyEnemy.clone());
                    }
                }
                case UNIT_WIZARD -> {
                    if (!isEnemy) {
                        GAME_BOARD.addUnit(wizard.clone());
                    } else {
                        GAME_BOARD.addUnit(wizardEnemy.clone());
                    }
                }
                default -> throw new UnitTypeNotSupportedException("Invalid unit type");
            }
        }
    }
}
