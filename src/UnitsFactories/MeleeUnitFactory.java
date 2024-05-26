package UnitsFactories;

import Interfaces.AbstractUnitFactory;
import Units.Melee;
import Units.Unit;

public class MeleeUnitFactory implements AbstractUnitFactory {
    @Override
    public Unit createUnit(boolean isEnemy) {
        String name;
        if (isEnemy) name = "БЛИЖНИК-ВРАГ";
        else name = "БЛИЖНИК";
        return new Melee(isEnemy, name);
    }
}