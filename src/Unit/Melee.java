package Unit;

import Config.UnitStats.MeleeStats;
import Exception.UnitPositionException;
import Game.GameBoard;
import Game.GameLogger;

public class Melee extends Unit {

    public Melee(boolean isEnemy) {
        super(
                isEnemy,
                isEnemy ? MeleeStats.ENEMY_NAME : MeleeStats.BASE_NAME,
                MeleeStats.BASE_HP,
                MeleeStats.BASE_DEFENCE,
                MeleeStats.BASE_EVASION,
                MeleeStats.BASE_CRIT_CHANCE
        );
    }

    public Melee() {}

    @Override
    public void performAction() {
        switch (getPosition()) {
            case 1 -> {
                GameLogger.addLogEntry(String.format("%s (%d): ПОДЛЫЙ УДАР (DMG 4-5 +bleed 2(3))\n", this.getName(), this.getPosition()));
                sneakyBlow();
            }
            case 2 -> {
                GameLogger.addLogEntry(String.format("%s (%d): КОВАРНЫЙ БРОСОК (DMG 3-4 +bleed 1(3))\n", this.getName(), this.getPosition()));
                sneakyThrow();
            }
            case 3 -> {
                GameLogger.addLogEntry(String.format("%s (%d): ЗАТОЧКА НОЖЕЙ (+CRT (10%%))\n", this.getName(), this.getPosition()));
                knifeSharpening();
            }
            case 4 -> {
                GameLogger.addLogEntry(String.format("%s (%d): КОВЫРЯНИЕ В НОСУ (nothing)\n", this.getName(), this.getPosition()));
                nosePicking();
            }
            default -> throw new UnitPositionException("Unit is not in one of the four positions");
        }
    }

    @Override
    public UnitType getUnitType() {
        return UnitType.UNIT_MELEE;
    }

    public void sneakyBlow() {  // DMG 4-5 + bleed 2(3)
        Unit enemy = GameBoard.getInstance().getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        boolean isCritical = isCritical(getCriticalChance());
        int damage = calculateDamage(
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

    public void sneakyThrow() { // DMG 3-4 + bleed 1(3)
        Unit enemy = GameBoard.getInstance().getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        boolean isCritical = isCritical(getCriticalChance());
        int damage = calculateDamage(
                MeleeStats.SNEAKY_THROW_BASE_DMG,
                MeleeStats.SNEAKY_THROW_MAX_DMG,
                enemy.getDefence(),
                isCritical
        );
        int duration = MeleeStats.SNEAKY_THROW_BLEED_DURATION;
        if (isCritical) duration++;
        enemy.setBleed(MeleeStats.SNEAKY_THROW_BLEED_DMG, duration);
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void knifeSharpening() { // criticalChance += 10% (ever);
        int criticalChance = getCriticalChance();
        if (isCritical(criticalChance)) setCriticalChance(criticalChance + MeleeStats.KNIFE_SHARPENING_CRIT_BONUS_CRIT);
        else setCriticalChance(criticalChance + MeleeStats.KNIFE_SHARPENING_CRIT_BONUS);
    }
    public void nosePicking() { // nothing
        if (isCritical(getCriticalChance())) {
            if (isEnemy()) setName(MeleeStats.NOSE_PICKING_CRIT_ENEMY_NAME);
            else setName(MeleeStats.NOSE_PICKING_CRIT_BASE_NAME);
        }
    }
}
