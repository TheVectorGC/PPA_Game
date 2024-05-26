package Units;

import Exceptions.UnitPositionException;

public class Wizard extends Unit {
    public Wizard(boolean isEnemy, String name) {
        super(isEnemy, name, 15, 10, 10, 20);
    }
    public Wizard() {}
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
                    bonk();
                    break;
                case 2:
                    fireFireball();
                    break;
                case 3:
                    arcaneTeleport();
                    break;
                case 4:
                    witcherTreatment();
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

    public void bonk() {    // DMG 1-2 + stun 75%
        Unit enemy = instance.getUnit(1, !isEnemy);
        int baseDamage = 1;
        int maxDamage = 2;
        boolean isCritical = isCritical(criticalChance);
        if (attack(baseDamage, maxDamage, isCritical, enemy)) return;
        if (isCritical || Math.random() * 100 < 75) enemy.isStunned = true;
    }

    public void fireFireball() { //DMG 0-15 + DMG 999999999 (10%)
        Unit enemy = instance.getUnit(1, !isEnemy);
        int baseDamage = 0;
        int maxDamage = 15;
        boolean isCritical = isCritical(criticalChance);
        double random = Math.random() * 100;
        if ((isCritical && random < 10 * 1.5) || random < 10) { if(attack(999999999, 999999999, false, enemy)) return; }
        attack(baseDamage, maxDamage, isCritical, enemy);
    }

    public void arcaneTeleport() {  // change first && last enemy positions, bleed remains first position
        if (isCritical(criticalChance)) {
            // something fun
        }
        int lastPosition = instance.getLastPosition(!isEnemy);
        Unit firstEnemy = instance.getUnit(1, !isEnemy);
        Unit lastEnemy = instance.getUnit(lastPosition, !isEnemy);
        if (firstEnemy.isBleed) {
            lastEnemy.isBleed = true;
            lastEnemy.bleedDamage = firstEnemy.bleedDamage;
            firstEnemy.isBleed = false;
            firstEnemy.setBleedDamage(0, bleedDamage.length - 1);
        }
        firstEnemy.position = lastPosition;
        lastEnemy.position = 1;
        instance.setUnit(lastPosition, firstEnemy, !isEnemy);
        instance.setUnit(1, lastEnemy, !isEnemy);
    }

    public void witcherTreatment() {    // POS1: +0-7 HP + this.bleed 0(0)
        int maxHeal = 7;
        Unit friend = instance.getUnit(1, isEnemy);
        boolean isCritical = isCritical(criticalChance);
        if (isCritical) maxHeal = (int)Math.ceil((double)maxHeal * 1.5);
        int heal = (int)Math.ceil(Math.random() * maxHeal);
        friend.healthPoints += heal;
        friend.isBleed = false;
        friend.setBleedDamage(0, bleedDamage.length);
    }
}
