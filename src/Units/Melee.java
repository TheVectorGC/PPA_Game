package Units;

import Exceptions.UnitPositionException;

public class Melee extends Unit {
    public Melee(boolean isEnemy, String name) {
        super(isEnemy, name, 20, 0, 30, 25);
    }
    public Melee() {}
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
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        int baseDamage = 4;
        int maxDamage = 5;
        boolean isCritical = isCritical(getCriticalChance());
        int damage = calculateDamage(baseDamage, maxDamage, enemy.getDefence(), isCritical);
        int duration = 3;
        if (isCritical) duration++;
        enemy.setBleedDuration(duration);
        enemy.setBleedDamage(2, duration);
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void sneakyThrow() { // DMG 3-4 + bleed 1(3)
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        int baseDamage = 3;
        int maxDamage = 4;
        boolean isCritical = isCritical(getCriticalChance());
        int damage = calculateDamage(baseDamage, maxDamage, enemy.getDefence(), isCritical);
        int duration = 3;
        if (isCritical) duration++;
        enemy.setBleedDuration(duration);
        enemy.setBleedDamage(1, duration);
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void knifeSharpening() { // criticalChance += 10% (ever);
        int criticalChance = getCriticalChance();
        if (isCritical(criticalChance)) setCriticalChance(criticalChance + 15);
        else setCriticalChance(criticalChance + 10);
    }
    public void nosePicking() { // nothing
        if (isCritical(getCriticalChance())) {
            if (isEnemy()) setName("КРИТИЧЕСКИЙ БЕЗДЕЛЬНИК-ВРАГ");
            else setName("КРИТИЧЕСКИЙ БЕЗДЕЛЬНИК");
        }
    }

}
