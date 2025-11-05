package UnitFactory;

import Unit.Unit;
import Unit.Wizard;

public class WizardUnitFactory implements AbstractUnitFactory {
    @Override
    public Unit createUnit(boolean isEnemy) {
        return new Wizard(isEnemy);
    }
}