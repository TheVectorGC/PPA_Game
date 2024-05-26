package UnitsFactories;

import Interfaces.AbstractUnitFactory;
import Units.Heavy;
import Units.Unit;

public class HeavyUnitFactory implements AbstractUnitFactory {
    @Override
    public Unit createUnit(boolean isEnemy) {
        String name;
        if (isEnemy) name = "ХЭВИК-ВРАГ";
        else name = "ХЭВИК";
        return new Heavy(isEnemy, name);
    }
}
