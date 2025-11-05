package Unit;

import Config.GameConstants;
import Config.UnitStats.HeavyStats;
import Exception.UnitPositionException;
import Game.GameLogger;

public class Heavy extends Unit {
    private int plusDamage = 0;

    public Heavy(boolean isEnemy) {
        super(isEnemy,
                isEnemy ? HeavyStats.ENEMY_NAME : HeavyStats.BASE_NAME,
                HeavyStats.BASE_HP,
                HeavyStats.BASE_DEFENCE,
                HeavyStats.BASE_EVASION,
                HeavyStats.BASE_CRIT_CHANCE
        );
    }

    public Heavy() {};

    @Override
    public void performAction() {
        switch (getPosition()) {
            case 1 -> {
                GameLogger.addLogEntry(String.format("%s (%d): УБИВАААТЬ (DMG %d-%d +stun (40%%))\n", this.getName(), this.getPosition(), 4 + plusDamage, 6 + plusDamage));
                kiiiiilllll();
            }
            case 2 -> {
                GameLogger.addLogEntry(String.format("%s (%d): УЧЕБНИК ПО СТРЕЛЬБЕ (+DMG (1) +CRT (3%%))\n", this.getName(), this.getPosition()));
                shootingTutorial();
            }
            case 3 -> {
                GameLogger.addLogEntry(String.format("%s (%d): ДОПИНГ (-1 HP +defence (3%%, max 70%%))\n", this.getName(), this.getPosition()));
                doping();
            }
            case 4 -> {
                GameLogger.addLogEntry(String.format("%s (%d): НАБОР МАССЫ (+HP (3))\n", this.getName(), this.getPosition()));
                weightGain();
            }
            default -> throw new UnitPositionException("Unit is not in one of the four positions");
        }
    }

    @Override
    public UnitType getUnitType() {
        return UnitType.UNIT_HEAVY;
    }

    public void kiiiiilllll() { // DMG (4 + plusDamage)-(6 + plusDamage) + stun (40%)
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        boolean isCritical = isCritical(getCriticalChance());
        double random = Math.random() * 100;


        if ((isCritical && random < HeavyStats.KIIIIILLLLL_STUN_CHANCE * GameConstants.CRIT_MULTIPLIER) ||
                random < HeavyStats.KIIIIILLLLL_STUN_CHANCE) enemy.setStunned(true);
        int damage = calculateDamage(
                HeavyStats.KIIIIILLLLL_BASE_DMG + plusDamage,
                HeavyStats.KIIIIILLLLL_MAX_DMG + plusDamage,
                enemy.getDefence(),
                isCritical
        );
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void shootingTutorial() {    // + plusDamage(1) + criticalChance += 3% (ever)
        int criticalChance = getCriticalChance();
        if (isCritical(criticalChance)) {
            setCriticalChance(criticalChance + HeavyStats.SHOOTING_TUTORIAL_CRIT_BONUS_NORMAL);
        }
        else {
            setCriticalChance(criticalChance + HeavyStats.SHOOTING_TUTORIAL_CRIT_BONUS_CRIT);
        }
        GameLogger.addLogEntry(String.format("%s (%d) дополнительный урон увеличен: " +
                        HeavyStats.SHOOTING_TUTORIAL_DAMAGE_BONUS +
                        "\nдополнительный урон %d -> %d)\n",
                this.getName(),
                this.getPosition(),
                this.plusDamage,
                this.plusDamage + HeavyStats.SHOOTING_TUTORIAL_DAMAGE_BONUS)
        );
        plusDamage += HeavyStats.SHOOTING_TUTORIAL_DAMAGE_BONUS;
    }

    public void doping() {  //  -1 HP + defence += 3% (max 70%)
        if (isCritical(getCriticalChance())) {
            setDefence(getDefence() + HeavyStats.DOPING_DEFENCE_BONUS_CRIT);
        }
        else {
            setHealthPoints(getHealthPoints() - HeavyStats.DOPING_HP_COST);
            setDefence(getDefence() + HeavyStats.DOPING_DEFENCE_BONUS_NORMAL);
        }
    }
    public void weightGain() {  // HP += 3;
        if (isCritical(getCriticalChance())) setHealthPoints(getHealthPoints() + HeavyStats.WEIGHT_GAIN_HP_BONUS_CRIT);
        else setHealthPoints(getHealthPoints() + HeavyStats.WEIGHT_GAIN_HP_BONUS_NORMAL);
    }

    @Override
    public Unit clone() {
        Heavy cloned = (Heavy) super.clone();
        cloned.plusDamage = this.plusDamage;
        return cloned;
    }
}
