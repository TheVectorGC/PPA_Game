package Unit.Bridge.Implementation;

import Unit.Bridge.Abstraction.UnitBehavior;
import Unit.Bridge.Abstraction.UnitBridge;

public class WizardImpl extends UnitBridge {
    public WizardImpl(UnitBehavior behavior) {
        super(behavior);
    }

    @Override
    public void act() {
        System.out.print("Wizard юнит: ");
        behavior.act();
    }
}

