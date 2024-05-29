package Units;

import Exceptions.UnitPositionException;
import Game.GameLogger;

public class Heavy extends Unit {
    public Heavy(boolean isEnemy, String name) {
        super(isEnemy, name, 30, 20, 0, 20);
    }

    public Heavy() {
    }

    private int plusDamage = 0;

    @Override
    public void act(StringBuilder logBuilder) {
        try {
            switch (getPosition()) {
                case 1:
                    logBuilder.append(String.format("%s (%d): УБИВАААТЬ (DMG %d-%d +stun (40%%))\n", this.getName(), this.getPosition(), 4 + plusDamage, 6 + plusDamage));
                    GameLogger.addLogEntry(logBuilder.toString());
                    kiiiiilllll();
                    break;
                case 2:
                    logBuilder.append(String.format("%s (%d): УЧЕБНИК ПО СТРЕЛЬБЕ (+DMG (1) +CRT (5%%))\n", this.getName(), this.getPosition()));
                    GameLogger.addLogEntry(logBuilder.toString());
                    shootingTutorial();
                    break;
                case 3:
                    logBuilder.append(String.format("%s (%d): ДОПИНГ (-1 HP +defence (5%%, max 70%%))\n", this.getName(), this.getPosition()));
                    GameLogger.addLogEntry(logBuilder.toString());
                    doping();
                    break;
                case 4:
                    logBuilder.append(String.format("%s (%d): НАБОР МАССЫ (+HP (5))\n", this.getName(), this.getPosition()));
                    GameLogger.addLogEntry(logBuilder.toString());
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
        if (isCritical(criticalChance)) {
            GameLogger.addLogEntry(String.format("%s (%d) дополнительный урон увеличен: 2\nдополнительный урон %d -> %d)\n", this.getName(), this.getPosition(), this.plusDamage, this.plusDamage + 2));
            plusDamage += 2;
        }
        else {
            GameLogger.addLogEntry(String.format("%s (%d) дополнительный урон увеличен: 1\nдополнительный урон %d -> %d)\n", this.getName(), this.getPosition(), this.plusDamage, this.plusDamage + 1));
            plusDamage += 1;
        }
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
