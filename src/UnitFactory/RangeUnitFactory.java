package UnitFactory;

import Unit.Range;
import Unit.Unit;

public class RangeUnitFactory implements AbstractUnitFactory {
    @Override
    public Unit createUnit(boolean isEnemy) {
        String name;
        if (isEnemy) name = "ДАЛЬНИК-ВРАГ";
        else name = "ДАЛЬНИК";
        return new Range(isEnemy, name);
    }
}