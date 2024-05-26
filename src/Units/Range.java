package Units;

import Exceptions.UnitPositionException;

public class Range extends Unit {
    public Range(boolean isEnemy, String name) {
        super(isEnemy, name, 10, 0, 0, 20);
    }
    public Range() {}
    @Override
    public void act() {
        if (getBleedDuration() > 0) bleed();
        if (isStunned()) {
            setStunned(false);
            return;
        }
        try {
            switch (getPosition()) {
                case 1:
                    cowardlyRetreat();
                    break;
                case 2:
                    uncertainShot();
                    break;
                case 3:
                    piercingBullet();
                    break;
                case 4:
                    headshotToHead();
                    break;
                default:
                    throw new UnitPositionException("Unit is not in one of the four positions");
            }
        }
        catch (UnitPositionException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
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

    public void piercingBullet() {  // DMG 1-2 + bleed 2(3)
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        int baseDamage = 1;
        int maxDamage = 2;
        boolean isCritical = isCritical(getCriticalChance());
        int damage = calculateDamage(baseDamage, maxDamage, enemy.getDefence(), isCritical);
        int duration = 3;
        if (isCritical) duration++;
        enemy.setBleedDamage(2, duration);
        enemy.setBleedDuration(duration);
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
