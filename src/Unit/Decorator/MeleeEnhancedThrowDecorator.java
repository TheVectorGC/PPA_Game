package Unit.Decorator;

import Unit.Unit;
import Unit.Melee;
import Config.UnitStats.MeleeStats;
import Game.GameBoard;
import Game.GameLogger;

public class MeleeEnhancedThrowDecorator {
    private final Melee melee;

    public MeleeEnhancedThrowDecorator(Melee melee) {
        this.melee = melee;
    }

    // Декоратор убирает кровотечение, но добавляет +3 урона
    public void enhancedSneakyThrow() {
        Unit enemy = GameBoard.getInstance().getUnit(1, !melee.isEnemy());
        if (enemy == null) return;

        if (melee.isEvade(enemy.getEvasion())) return;

        boolean isCritical = melee.isCritical(melee.getCriticalChance());

        int enhancedBaseDmg = MeleeStats.SNEAKY_THROW_BASE_DMG + 3;
        int enhancedMaxDmg = MeleeStats.SNEAKY_THROW_MAX_DMG + 3;

        int damage = melee.calculateDamage(
                enhancedBaseDmg,
                enhancedMaxDmg,
                enemy.getDefence(),
                isCritical
        );

        enemy.setHealthPoints(enemy.getHealthPoints() - damage);

        GameLogger.addLogEntry("УСИЛЕННЫЙ БРОСОК: +3 урона, но без кровотечения");
    }

    public Melee getOriginal() {
        return melee;
    }
}