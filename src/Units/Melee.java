package Units;

import Exceptions.UnitPositionException;

public class Melee extends Unit {
    public Melee(boolean isEnemy, String name) {
        super(isEnemy, name, 20, 0, 30, 25);
    }
    public Melee() {}
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
                    sneakyBlow();
                    break;
                case 2:
                    sneakyThrow();
                    break;
                case 3:
                    knifeSharpening();
                    break;
                case 4:
                    nosePicking();
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

    public void sneakyBlow() {  // DMG 4-5 + bleed 2(3)
        Unit enemy = instance.getUnit(1, !isEnemy);
        int baseDamage = 4;
        int maxDamage = 5;
        boolean isCritical = isCritical(criticalChance);
        if (attack(baseDamage, maxDamage, isCritical, enemy)) return;
        enemy.isBleed = true;
        enemy.setBleedDamage(2, 3);
    }

    public void sneakyThrow() { // DMG 3-4 + bleed 1(3)
        Unit enemy = instance.getUnit(1, !isEnemy);
        int baseDamage = 3;
        int maxDamage = 4;
        boolean isCritical = isCritical(criticalChance);
        if (attack(baseDamage, maxDamage, isCritical, enemy)) return;
        enemy.isBleed = true;
        enemy.setBleedDamage(1, 3);
    }

    public void knifeSharpening() { // criticalChance += 10% (ever);
        if (isCritical(criticalChance)) criticalChance += 15;
        else criticalChance += 10;
    }
    public void nosePicking() { // nothing
        if (isCritical(criticalChance)) {
            // something funny
        }
    }

}
