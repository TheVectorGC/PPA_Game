package Game.Bridge;

import Unit.Melee;

public class NosePickingBridge {
    private final NosePickingImpl implementation;

    public NosePickingBridge(NosePickingImpl implementation) {
        this.implementation = implementation;
    }

    public void performNosePicking(Melee melee) {
        implementation.pickNose(melee);
    }
}