package Unit;

import Exception.UnitPositionException;
import Game.GameLogger;

public class Range extends Unit {
    private static final int COST = 100;

    public Range(boolean isEnemy, String name) {
        super(isEnemy, name, 10, 0, 0, 20);
    }
    public Range() {}
    @Override
    public void performAction(StringBuilder logBuilder) {
        switch (getPosition()) {
            case 1 -> {
                logBuilder.append(String.format("%s (%d): ТРУСЛИВОЕ ОТСТУПЛЕНИЕ (DMG 1-2, back(2), isStunned)\n", this.getName(), this.getPosition()));
                GameLogger.addLogEntry(logBuilder.toString());
                cowardlyRetreat();
            }
            case 2 -> {
                logBuilder.append(String.format("%s (%d): НЕУВЕРЕННЫЙ ВЫСТРЕЛ (DMG 3-5)\n", this.getName(), this.getPosition()));
                GameLogger.addLogEntry(logBuilder.toString());
                uncertainShot();
            }
            case 3 -> {
                logBuilder.append(String.format("%s (%d): ПРОНЗАЮЩАЯ ПУЛЯ (DMG 1-2 +bleed 3(2))\n", this.getName(), this.getPosition()));
                GameLogger.addLogEntry(logBuilder.toString());
                piercingBullet();
            }
            case 4 -> {
                logBuilder.append(String.format("%s (%d): ХЕДШОТ В ГОЛОВУ (CRT (+30%%) DMG 8-9)\n", this.getName(), this.getPosition()));
                GameLogger.addLogEntry(logBuilder.toString());
                headshotToHead();
            }
            default -> throw new UnitPositionException("Unit is not in one of the four positions");
        }
    }

    @Override
    public UnitType getUnitType() {
        return UnitType.UNIT_RANGE;
    }

    @Override
    public int getCost() {
        return COST;
    }

    public void cowardlyRetreat() { // DMG 1-2 + back(2) + isStunned
        Unit enemy = instance.getUnit(1, !isEnemy());
        changePosition(this, -2);
        boolean isCritical = isCritical(getCriticalChance());
        if (!isCritical) setStunned(true);
        if (isEvade(enemy.getEvasion())) return;
        int baseDamage = 1;
        int maxDamage = 2;
        int damage = calculateDamage(baseDamage, maxDamage, enemy.getDefence(), isCritical);
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void uncertainShot() {   // DMG 3-5
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        int baseDamage = 3;
        int maxDamage = 5;
        boolean isCritical = isCritical(getCriticalChance());
        int damage = calculateDamage(baseDamage, maxDamage, enemy.getDefence(), isCritical);
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void piercingBullet() {  // DMG 1-2 + bleed 3(2)
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        int baseDamage = 1;
        int maxDamage = 2;
        boolean isCritical = isCritical(getCriticalChance());
        int damage = calculateDamage(baseDamage, maxDamage, enemy.getDefence(), isCritical);
        int duration = 2;
        if (isCritical) duration++;
        enemy.setBleed(3, duration);
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void headshotToHead() {  // DMG 8-9 + criticalChance += 30%
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        int baseDamage = 8;
        int maxDamage = 9;
        boolean isCritical = isCritical(getCriticalChance() + 30);
        int damage = calculateDamage(baseDamage, maxDamage, enemy.getDefence(), isCritical);
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }
}
