package Unit.Bridge.Implementation;

import Unit.Bridge.Abstraction.UnitBehavior;
import Unit.Bridge.Abstraction.UnitBridge;

public class HeavyImpl extends UnitBridge {
    public HeavyImpl(UnitBehavior behavior) {
        super(behavior);
    }

    @Override
    public void act() {
        System.out.print("Heavy юнит: ");
        behavior.act();
    }
}

