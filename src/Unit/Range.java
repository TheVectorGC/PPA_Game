package Unit;

import Config.UnitStats.RangeStats;
import Exception.UnitPositionException;
import Game.GameLogger;

public class Range extends Unit {
    public Range(boolean isEnemy) {
        super(
                isEnemy,
                isEnemy ? RangeStats.ENEMY_NAME : RangeStats.BASE_NAME,
                RangeStats.BASE_HP,
                RangeStats.BASE_DEFENCE,
                RangeStats.BASE_EVASION,
                RangeStats.BASE_CRIT_CHANCE
        );
    }

    public Range() {}

    @Override
    public void performAction() {
        switch (getPosition()) {
            case 1 -> {
                GameLogger.addLogEntry(String.format("%s (%d): ТРУСЛИВОЕ ОТСТУПЛЕНИЕ (DMG 1-2, back(2), isStunned)\n", this.getName(), this.getPosition()));
                cowardlyRetreat();
            }
            case 2 -> {
                GameLogger.addLogEntry(String.format("%s (%d): НЕУВЕРЕННЫЙ ВЫСТРЕЛ (DMG 3-5)\n", this.getName(), this.getPosition()));
                uncertainShot();
            }
            case 3 -> {
                GameLogger.addLogEntry(String.format("%s (%d): ПРОНЗАЮЩАЯ ПУЛЯ (DMG 1-2 +bleed 3(2))\n", this.getName(), this.getPosition()));
                piercingBullet();
            }
            case 4 -> {
                GameLogger.addLogEntry(String.format("%s (%d): ХЕДШОТ В ГОЛОВУ (CRT (+30%%) DMG 8-9)\n", this.getName(), this.getPosition()));
                headshotToHead();
            }
            default -> throw new UnitPositionException("Unit is not in one of the four positions");
        }
    }

    @Override
    public UnitType getUnitType() {
        return UnitType.UNIT_RANGE;
    }

    public void cowardlyRetreat() { // DMG 1-2 + back(2) + isStunned
        Unit enemy = instance.getUnit(1, !isEnemy());
        changePosition(this, -1 * RangeStats.COWARDLY_RETREAT_BACK_POSITIONS);
        boolean isCritical = isCritical(getCriticalChance());
        if (!isCritical) setStunned(true);
        if (isEvade(enemy.getEvasion())) return;
        int damage = calculateDamage(
                RangeStats.COWARDLY_RETREAT_BASE_DMG,
                RangeStats.COWARDLY_RETREAT_MAX_DMG,
                enemy.getDefence(),
                isCritical
        );
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void uncertainShot() {   // DMG 3-5
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        boolean isCritical = isCritical(getCriticalChance());
        int damage = calculateDamage(
                RangeStats.UNCERTAIN_SHOT_BASE_DMG,
                RangeStats.UNCERTAIN_SHOT_MAX_DMG,
                enemy.getDefence(),
                isCritical
        );
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void piercingBullet() {  // DMG 1-2 + bleed 3(2)
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        boolean isCritical = isCritical(getCriticalChance());
        int damage = calculateDamage(
                RangeStats.PIERCING_BULLET_BASE_DMG,
                RangeStats.PIERCING_BULLET_MAX_DMG,
                enemy.getDefence(),
                isCritical
        );
        int duration = RangeStats.PIERCING_BULLET_BLEED_DURATION;
        if (isCritical) duration++;
        enemy.setBleed(RangeStats.PIERCING_BULLET_BLEED_DMG, duration);
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void headshotToHead() {  // DMG 8-9 + criticalChance += 30%
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        boolean isCritical = isCritical(getCriticalChance() + RangeStats.HEADSHOT_TO_HEAD_CRIT_BONUS);
        int damage = calculateDamage(
                RangeStats.HEADSHOT_TO_HEAD_BASE_DMG,
                RangeStats.HEADSHOT_TO_HEAD_MAX_DMG,
                enemy.getDefence(),
                isCritical
        );
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }
}
