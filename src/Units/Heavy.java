package Units;

import Exceptions.UnitPositionException;

public class Heavy extends Unit {
    public Heavy(boolean isEnemy, String name) {
        super(isEnemy, name, 30, 20, 0, 20);
    }
    public Heavy() {}
    private int plusDamage = 0;
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
                    kiiiiilllll();
                    break;
                case 2:
                    shootingTutorial();
                    break;
                case 3:
                    doping();
                    break;
                case 4:
                    weightGain();
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

    public void kiiiiilllll() { // DMG (4 + plusDamage)-(6 + plusDamage) + stun (40%)
        Unit enemy = instance.getUnit(1, !isEnemy);
        int baseDamage = 4 + plusDamage;
        int maxDamage = 6 + plusDamage;
        boolean isCritical = isCritical(criticalChance);
        if (attack(baseDamage, maxDamage, isCritical, enemy)) return;
        double random = Math.random() * 100;
        if ((isCritical && random < 40 * 1.5) || random < 40) enemy.isStunned = true;
    }

    public void shootingTutorial() {    // + DMG(1) + criticalChance += 5% (ever)
        if (isCritical(criticalChance)) plusDamage += 2;
        else plusDamage += 1;
        criticalChance += 5;
    }

    public void doping() {  //  -1 HP (cannot die) + defence += 5% (max 70%)
        if (isCritical(criticalChance)) {
            if (defence <= 63) defence += 7;
            else defence = 70;
        }
        else {
            if (healthPoints > 1) healthPoints -= 1;
            if (defence <= 65) defence += 5;
            else defence = 70;
        }
    }
    public void weightGain() {  // HP += 5;
        if (isCritical(criticalChance)) healthPoints += 8;
        else healthPoints += 5;
    }
}
