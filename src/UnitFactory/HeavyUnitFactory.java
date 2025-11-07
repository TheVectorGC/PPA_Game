package UnitFactory;

import Unit.Heavy;
import Unit.Unit;

public class HeavyUnitFactory implements AbstractUnitFactory {
    @Override
    public Unit createUnit(boolean isEnemy) {
        return new Heavy(isEnemy);
    }
}
