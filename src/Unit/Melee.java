package Unit;

import Exception.UnitPositionException;
import Game.GameLogger;

public class Melee extends Unit {
    public Melee(boolean isEnemy, String name) {
        super(UnitType.UNIT_MELEE, isEnemy, name, 20, 0, 30, 25);
    }
    public Melee() {}
    @Override
    public void act(StringBuilder logBuilder) {
        switch (getPosition()) {
            case 1:
                logBuilder.append(String.format("%s (%d): ПОДЛЫЙ УДАР (DMG 4-5 +bleed 2(3))\n", this.getName(), this.getPosition()));
                GameLogger.addLogEntry(logBuilder.toString());
                sneakyBlow();
                break;
            case 2:
                logBuilder.append(String.format("%s (%d): КОВАРНЫЙ БРОСОК (DMG 3-4 +bleed 1(3))\n", this.getName(), this.getPosition()));
                GameLogger.addLogEntry(logBuilder.toString());
                sneakyThrow();
                break;
            case 3:
                logBuilder.append(String.format("%s (%d): ЗАТОЧКА НОЖЕЙ (+CRT (10%%))\n", this.getName(), this.getPosition()));
                GameLogger.addLogEntry(logBuilder.toString());
                knifeSharpening();
                break;
            case 4:
                logBuilder.append(String.format("%s (%d): КОВЫРЯНИЕ В НОСУ (nothing)\n", this.getName(), this.getPosition()));
                GameLogger.addLogEntry(logBuilder.toString());
                nosePicking();
                break;
            default:
                throw new UnitPositionException("Unit is not in one of the four positions");
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
        enemy.setBleed(2, duration);
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
        enemy.setBleed(1, duration);
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void knifeSharpening() { // criticalChance += 10% (ever);
        int criticalChance = getCriticalChance();
        if (isCritical(criticalChance)) setCriticalChance(criticalChance + 15);
        else setCriticalChance(criticalChance + 10);
    }
    public void nosePicking() { // nothing
        if (isCritical(getCriticalChance())) {
            if (isEnemy()) setName("КРИТИЧЕСКИЙ ОЛУХ-ВРАГ");
            else setName("КРИТИЧЕСКИЙ ОЛУХ");
        }
    }
}
