package Units;

import Exceptions.UnitPositionException;

public class Range extends Unit {
    public Range(boolean isEnemy, String name) {
        super(isEnemy, name, 10, 0, 0, 20);
    }
    public Range() {}
    @Override
    public void act() {
        if (bleed()) return;
        if (isStunned) {
            isStunned = false;
            return;
        }
        try {
            switch (position) {
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

    public void cowardlyRetreat() { // DMG 1-2 + back(2) + isStunned + this.bleed 0(0)
        Unit enemy = instance.getUnit(1, !isEnemy);
        int baseDamage = 1;
        int maxDamage = 2;
        boolean isCritical = isCritical(criticalChance);
        isBleed = false;
        setBleedDamage(0, bleedDamage.length);
        int lastPosition = instance.getLastPosition(isEnemy);
        if (lastPosition > 3) lastPosition = 3;
        Unit lastFriend = instance.getUnit(lastPosition, isEnemy);
        position = lastPosition;
        lastFriend.position = 1;
        instance.setUnit(lastPosition, this, isEnemy);
        instance.setUnit(1, lastFriend, isEnemy);
        attack(baseDamage, maxDamage, isCritical, enemy);
    }

    public void uncertainShot() {   // DMG 3-4
        Unit enemy = instance.getUnit(1, !isEnemy);
        int baseDamage = 3;
        int maxDamage = 4;
        boolean isCritical = isCritical(criticalChance);
        attack(baseDamage, maxDamage, isCritical, enemy);
    }

    public void piercingBullet() {  // DMG 1-2 + bleed 2(3)
        Unit enemy = instance.getUnit(1, !isEnemy);
        int baseDamage = 1;
        int maxDamage = 2;
        boolean isCritical = isCritical(criticalChance);
        if (attack(baseDamage, maxDamage, isCritical, enemy)) return;
        enemy.isBleed = true;
        enemy.setBleedDamage(2, 3);
    }

    public void headshotToHead() {  // DMG 8-9 + criticalChance += 30%
        Unit enemy = instance.getUnit(1, !isEnemy);
        int baseDamage = 8;
        int maxDamage = 9;
        boolean isCritical = isCritical(criticalChance + 30);
        attack(baseDamage, maxDamage, isCritical, enemy);
    }
}
