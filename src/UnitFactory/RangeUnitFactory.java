package UnitFactory;

import Unit.Range;
import Unit.Unit;

public class RangeUnitFactory implements AbstractUnitFactory {
    @Override
    public Unit createUnit(boolean isEnemy) {
        return new Range(isEnemy);
    }
}