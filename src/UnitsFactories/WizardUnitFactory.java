package UnitsFactories;

import Interfaces.AbstractUnitFactory;
import Units.Unit;
import Units.Wizard;

public class WizardUnitFactory implements AbstractUnitFactory {
    @Override
    public Unit createUnit(boolean isEnemy) {
        String name;
        if (isEnemy) name = "КОЛДУН-ВРАГ";
        else name = "КОЛДУН";
        return new Wizard(isEnemy, name);
    }
}