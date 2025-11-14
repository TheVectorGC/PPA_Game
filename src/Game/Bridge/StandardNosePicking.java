package Game.Bridge;

import Unit.Melee;
import Game.GameLogger;

public class StandardNosePicking implements NosePickingImpl {
    @Override
    public void pickNose(Melee melee) {
        GameLogger.addLogEntry("Ковыряется в носу без особого энтузиазма");
    }
}