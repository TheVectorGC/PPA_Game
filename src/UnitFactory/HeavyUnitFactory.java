package UnitFactory;

import Unit.Heavy;
import Unit.Unit;

public class HeavyUnitFactory implements AbstractUnitFactory {
    @Override
    public Unit createUnit(boolean isEnemy) {
        String name;
        if (isEnemy) name = "ХЭВИК-ВРАГ";
        else name = "ХЭВИК";
        return new Heavy(isEnemy, name);
    }
}
