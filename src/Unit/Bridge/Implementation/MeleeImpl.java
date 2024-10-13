package Unit.Bridge.Implementation;

import Unit.Bridge.Abstraction.UnitBehavior;
import Unit.Bridge.Abstraction.UnitBridge;

public class MeleeImpl extends UnitBridge {
    public MeleeImpl(UnitBehavior behavior) {
        super(behavior);
    }

    @Override
    public void act() {
        System.out.print("Melee юнит: ");
        behavior.act();
    }
}

