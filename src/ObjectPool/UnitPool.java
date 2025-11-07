package ObjectPool;

import Unit.UnitType;
import UnitFactory.AbstractUnitFactory;
import Unit.Unit;
import UnitFactory.HeavyUnitFactory;
import UnitFactory.MeleeUnitFactory;
import UnitFactory.RangeUnitFactory;
import UnitFactory.WizardUnitFactory;
import java.util.HashMap;
import java.util.Map;

public class UnitPool {
    private final Map<UnitType, Unit> unitTemplates;
    private final Map<UnitType, Unit> enemyUnitTemplates;

    private static UnitPool instance;

    public static UnitPool getInstance() {
        if (instance == null) {
            instance = new UnitPool();
        }
        return instance;
    }

    private UnitPool() {
        unitTemplates = new HashMap<>();
        enemyUnitTemplates = new HashMap<>();

        AbstractUnitFactory meleeFactory = new MeleeUnitFactory();
        AbstractUnitFactory rangeFactory = new RangeUnitFactory();
        AbstractUnitFactory heavyFactory = new HeavyUnitFactory();
        AbstractUnitFactory wizardFactory = new WizardUnitFactory();

        unitTemplates.put(UnitType.UNIT_MELEE, meleeFactory.createUnit(false));
        unitTemplates.put(UnitType.UNIT_RANGE, rangeFactory.createUnit(false));
        unitTemplates.put(UnitType.UNIT_HEAVY, heavyFactory.createUnit(false));
        unitTemplates.put(UnitType.UNIT_WIZARD, wizardFactory.createUnit(false));

        enemyUnitTemplates.put(UnitType.UNIT_MELEE, meleeFactory.createUnit(true));
        enemyUnitTemplates.put(UnitType.UNIT_RANGE, rangeFactory.createUnit(true));
        enemyUnitTemplates.put(UnitType.UNIT_HEAVY, heavyFactory.createUnit(true));
        enemyUnitTemplates.put(UnitType.UNIT_WIZARD, wizardFactory.createUnit(true));
    }

    public Unit getUnit(UnitType unitType, boolean isEnemy) {
        Unit template;
        if (!isEnemy) { template = unitTemplates.get(unitType); }
        else { template = enemyUnitTemplates.get(unitType); }

        if (template != null) { return template.clone(); }
        else { throw new IllegalArgumentException("Unknown unit type: " + unitType); }
    }
}
