package Game.Command;

import Unit.Melee;
import Unit.Unit;
import Game.GameBoard;
import Config.UnitStats.MeleeStats;

public class MeleeSneakyBlowCommand implements UnitCommand {
    private final Melee melee;

    public MeleeSneakyBlowCommand(Melee melee) {
        this.melee = melee;
    }

    @Override
    public void execute() {
        Unit enemy = GameBoard.getInstance().getUnit(1, !melee.isEnemy());
        if (melee.isEvade(enemy.getEvasion())) return;

        boolean isCritical = melee.isCritical(melee.getCriticalChance());
        int damage = melee.calculateDamage(
                MeleeStats.SNEAKY_BLOW_BASE_DMG,
                MeleeStats.SNEAKY_BLOW_MAX_DMG,
                enemy.getDefence(),
                isCritical
        );

        int duration = MeleeStats.SNEAKY_BLOW_BLEED_DURATION;
        if (isCritical) duration++;
        enemy.setBleed(MeleeStats.SNEAKY_BLOW_BLEED_DMG, duration);
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }
}