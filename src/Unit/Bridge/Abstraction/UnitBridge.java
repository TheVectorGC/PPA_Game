package Unit.Bridge.Abstraction;

public abstract class UnitBridge {
    protected UnitBehavior behavior;

    public UnitBridge(UnitBehavior behavior) {
        this.behavior = behavior;
    }

    public abstract void act();

    public void setBehavior(UnitBehavior behavior) {
        this.behavior = behavior;
    }
}
