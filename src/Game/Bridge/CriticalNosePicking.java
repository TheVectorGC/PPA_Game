package Game.Bridge;

import Unit.Melee;
import Config.UnitStats.MeleeStats;

public class CriticalNosePicking implements NosePickingImpl {
    @Override
    public void pickNose(Melee melee) {
        if (melee.isEnemy()) {
            melee.setName(MeleeStats.NOSE_PICKING_CRIT_ENEMY_NAME);
        }
        else {
            melee.setName(MeleeStats.NOSE_PICKING_CRIT_BASE_NAME);
        }
    }
}