package Units;

import Exceptions.UnitPositionException;
import Game.GameLogger;

import java.util.ArrayList;

public class Wizard extends Unit {
    public Wizard(boolean isEnemy, String name) {
        super(isEnemy, name, 15, 10, 10, 20);
    }
    public Wizard() {}
    @Override
    public void act(StringBuilder logBuilder) {
        try {
            switch (getPosition()) {
                case 1:
                    logBuilder.append(String.format("%s (%d): БОНЬК (DMG 1-2 +stun (75%%))\n", this.getName(), this.getPosition()));
                    GameLogger.addLogEntry(logBuilder.toString());
                    bonk();
                    break;
                case 2:
                    logBuilder.append(String.format("%s (%d): ОГНЕННЫЙ ФАЕРБОЛ (DMG 0-15 || DMG 999999999 (10%%))\n", this.getName(), this.getPosition()));
                    GameLogger.addLogEntry(logBuilder.toString());
                    fireFireball();
                    break;
                case 3:
                    logBuilder.append(String.format("%s (%d): ЧАРОДЕЙСКИЙ ТЕЛЕПОРТ (ENEMY POS1: back(random))\n", this.getName(), this.getPosition()));
                    GameLogger.addLogEntry(logBuilder.toString());
                    arcaneTeleport();
                    break;
                case 4:
                    logBuilder.append(String.format("%s (%d): КОЛДУНСКОЕ ЛЕЧЕНИЕ (FRIEND POS1: +0-7 HP +bleed = false)\n", this.getName(), this.getPosition()));
                    GameLogger.addLogEntry(logBuilder.toString());
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
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        int baseDamage = 1;
        int maxDamage = 2;
        boolean isCritical = isCritical(getCriticalChance());
        if (isCritical || Math.random() * 100 < 75) enemy.setStunned(true);
        int damage = calculateDamage(baseDamage, maxDamage, enemy.getDefence(), isCritical);
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void fireFireball() { //DMG 0-15 + DMG 999999999 (10%)
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        int baseDamage = 0;
        int maxDamage = 15;
        boolean isCritical = isCritical(getCriticalChance());
        double random = Math.random() * 100;
        int damage;
        if ((isCritical && random < 10 * 1.5) || random < 10) damage = 999999999;
        else damage = calculateDamage(baseDamage, maxDamage, enemy.getDefence(), isCritical);
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void arcaneTeleport() {  // ENEMY POS1: BACK(RANDOM)
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isCritical(getCriticalChance())) {
            instance.addUnit(enemy, !enemy.isEnemy(), 1);
            instance.buryTheDead(enemy);
            enemy.setEnemy(!enemy.isEnemy());
        }
        double random = Math.random() * 100;
        if (random <= 33) enemy.changePosition(enemy, -1);
        else if (random <= 66) enemy.changePosition(enemy, -2);
        else if (random <= 99) enemy.changePosition(enemy, -3);
        else {
            setEvasion(70); // lucky :)
        }
    }

    public void witcherTreatment() {    // POS1: +0-7 HP + this.bleed 0(0)
        int maxHeal = 7;
        Unit friend = instance.getUnit(1, isEnemy());
        boolean isCritical = isCritical(getCriticalChance());
        if (isCritical) maxHeal = (int)Math.ceil((double)maxHeal * 1.5);
        int heal = (int)Math.ceil(Math.random() * maxHeal);
        friend.setBleed(new ArrayList<>(0));
        friend.setHealthPoints(friend.getHealthPoints() + heal);
    }
}
