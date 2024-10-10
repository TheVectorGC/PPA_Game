package UnitFactory;

import Unit.Unit;
import Unit.Wizard;

public class WizardUnitFactory implements AbstractUnitFactory {
    @Override
    public Unit createUnit(boolean isEnemy) {
        String name;
        if (isEnemy) name = "КОЛДУН-ВРАГ";
        else name = "КОЛДУН";
        return new Wizard(isEnemy, name);
    }
}