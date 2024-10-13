package ObjectPool;

import Unit.UnitType;
import UnitFactory.AbstractUnitFactory;
import Unit.Unit;
import UnitBuilder.HeavyUnitBuilder;
import UnitBuilder.MeleeUnitBuilder;
import UnitBuilder.RangeUnitBuilder;
import UnitBuilder.WizardUnitBuilder;
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

        MeleeUnitBuilder meleeBuilder = new MeleeUnitBuilder(true);
        RangeUnitBuilder rangeBuilder = new RangeUnitBuilder(true);
        HeavyUnitBuilder heavyBuilder = new HeavyUnitBuilder(true);
        WizardUnitBuilder wizardBuilder = new WizardUnitBuilder(true);

        meleeBuilder.setHealthPoints(20);
        meleeBuilder.setDefence(0);
        meleeBuilder.setEvasion(30);
        meleeBuilder.setCriticalChance(25);

        rangeBuilder.setHealthPoints(10);
        rangeBuilder.setDefence(0);
        rangeBuilder.setEvasion(0);
        rangeBuilder.setCriticalChance(20);

        heavyBuilder.setHealthPoints(25);
        heavyBuilder.setDefence(25);
        heavyBuilder.setEvasion(0);
        heavyBuilder.setCriticalChance(20);

        wizardBuilder.setHealthPoints(15);
        wizardBuilder.setDefence(10);
        wizardBuilder.setEvasion(10);
        wizardBuilder.setCriticalChance(20);

        unitTemplates.put(UnitType.UNIT_MELEE, meleeFactory.createUnit(false));
        unitTemplates.put(UnitType.UNIT_RANGE, rangeFactory.createUnit(false));
        unitTemplates.put(UnitType.UNIT_HEAVY, heavyFactory.createUnit(false));
        unitTemplates.put(UnitType.UNIT_WIZARD, wizardFactory.createUnit(false));

        enemyUnitTemplates.put(UnitType.UNIT_MELEE, meleeBuilder.build());
        enemyUnitTemplates.put(UnitType.UNIT_RANGE, rangeBuilder.build());
        enemyUnitTemplates.put(UnitType.UNIT_HEAVY, heavyBuilder.build());
        enemyUnitTemplates.put(UnitType.UNIT_WIZARD, wizardBuilder.build());
    }

    public Unit getUnit(UnitType unitType, boolean isEnemy) {
        Unit template;
        if (!isEnemy) { template = unitTemplates.get(unitType); }
        else { template = enemyUnitTemplates.get(unitType); }
        if (template != null) {
            return template.clone();
        } else {
            throw new IllegalArgumentException("Unknown unit type: " + unitType);
        }
    }
}
