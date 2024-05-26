package Units;

import Exceptions.UnitPositionException;

public class Heavy extends Unit {
    public Heavy(boolean isEnemy, String name) {
        super(isEnemy, name, 30, 20, 0, 20);
    }

    public Heavy() {
    }

    private int plusDamage = 0;

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
        } catch (UnitPositionException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    public void kiiiiilllll() { // DMG (4 + plusDamage)-(6 + plusDamage) + stun (40%)
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        int baseDamage = 4 + plusDamage;
        int maxDamage = 6 + plusDamage;
        boolean isCritical = isCritical(getCriticalChance());
        double random = Math.random() * 100;
        if ((isCritical && random < 40 * 1.5) || random < 40) enemy.setStunned(true);
        int damage = calculateDamage(baseDamage, maxDamage, enemy.getDefence(), isCritical);
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void shootingTutorial() {    // + plusDamage(1) + criticalChance += 5% (ever)
        int criticalChance = getCriticalChance();
        if (isCritical(criticalChance)) plusDamage += 2;
        else plusDamage += 1;
        setCriticalChance(criticalChance + 5);
    }

    public void doping() {  //  -1 HP + defence += 5% (max 70%)
        if (isCritical(getCriticalChance())) {
            setDefence(getDefence() + 7);
        }
        else {
        setHealthPoints(getHealthPoints() - 1);
        setDefence(getDefence() + 5);
        }
    }
    public void weightGain() {  // HP += 5;
        if (isCritical(getCriticalChance())) setHealthPoints(getHealthPoints() + 8);
        else setHealthPoints(getHealthPoints() + 5);
    }
}
