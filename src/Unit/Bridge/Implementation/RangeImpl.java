package Unit.Bridge.Implementation;

import Unit.Bridge.Abstraction.UnitBehavior;
import Unit.Bridge.Abstraction.UnitBridge;

public class RangeImpl extends UnitBridge {
    public RangeImpl(UnitBehavior behavior) {
        super(behavior);
    }

    @Override
    public void act() {
        System.out.print("Range юнит: ");
        behavior.act();
    }
}

