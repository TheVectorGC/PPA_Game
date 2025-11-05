package UnitFactory;

import Unit.Melee;
import Unit.Unit;

public class MeleeUnitFactory implements AbstractUnitFactory {
    @Override
    public Unit createUnit(boolean isEnemy) {
        return new Melee(isEnemy);
    }
}