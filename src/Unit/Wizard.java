package Unit;

import Config.GameConstants;
import Config.UnitStats.WizardStats;
import Exception.UnitPositionException;
import Game.GameLogger;

public class Wizard extends Unit {
    public Wizard(boolean isEnemy) {
        super(isEnemy,
                isEnemy ? WizardStats.ENEMY_NAME : WizardStats.BASE_NAME,
                WizardStats.BASE_HP,
                WizardStats.BASE_DEFENCE,
                WizardStats.BASE_EVASION,
                WizardStats.BASE_CRIT_CHANCE
        );
    }

    public Wizard() {};

    @Override
    public void performAction() {
        switch (getPosition()) {
            case 1 -> {
                GameLogger.addLogEntry(String.format("%s (%d): БОНЬК (DMG 1-2 +stun (75%%))\n", this.getName(), this.getPosition()));
                bonk();
            }
            case 2 -> {
                GameLogger.addLogEntry(String.format("%s (%d): ОГНЕННЫЙ ФАЕРБОЛ (DMG 0-15 || DMG 999999999 (10%%))\n", this.getName(), this.getPosition()));
                fireFireball();
            }
            case 3 -> {
                GameLogger.addLogEntry(String.format("%s (%d): ЧАРОДЕЙСКИЙ ТЕЛЕПОРТ (ENEMY POS1: back(random))\n", this.getName(), this.getPosition()));
                arcaneTeleport();
            }
            case 4 -> {
                GameLogger.addLogEntry(String.format("%s (%d): КОЛДУНСКОЕ ЛЕЧЕНИЕ (FRIEND POS1: +0-7 HP +bleed = false)\n", this.getName(), this.getPosition()));
                witcherTreatment();
            }
            default -> throw new UnitPositionException("Unit is not in one of the four positions");
        }
    }

    @Override
    public UnitType getUnitType() {
        return UnitType.UNIT_WIZARD;
    }

    public void bonk() {    // DMG 1-2 + stun 75%
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        boolean isCritical = isCritical(getCriticalChance());
        if (isCritical || Math.random() * 100 < WizardStats.BONK_STUN_CHANCE) enemy.setStunned(true);
        int damage = calculateDamage(
                WizardStats.BONK_BASE_DMG,
                WizardStats.BONK_MAX_DMG,
                enemy.getDefence(),
                isCritical
        );
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void fireFireball() { //DMG 0-15 + DMG 999999999 (10%)
        Unit enemy = instance.getUnit(1, !isEnemy());
        if (isEvade(enemy.getEvasion())) return;
        boolean isCritical = isCritical(getCriticalChance());
        double random = Math.random() * 100;
        int damage;
        if ((isCritical && random < WizardStats.FIREBALL_INSTAKILL_CHANCE * GameConstants.CRIT_MULTIPLIER) ||
                random < WizardStats.FIREBALL_INSTAKILL_CHANCE) damage = WizardStats.FIREBALL_INSTAKILL_DAMAGE;
        else damage = calculateDamage(
                WizardStats.FIREBALL_BASE_DMG,
                WizardStats.FIREBALL_MAX_DMG,
                enemy.getDefence(),
                isCritical
        );
        enemy.setHealthPoints(enemy.getHealthPoints() - damage);
    }

    public void arcaneTeleport() {  // ENEMY POS1: BACK(RANDOM)
        Unit enemy = instance.getUnit(1, !isEnemy());
        double random = Math.random() * 100;
        if (random <= WizardStats.ARCANE_TELEPORT_BACK_1_RANDOM) {
            enemy.changePosition(enemy, -1 * WizardStats.ARCANE_TELEPORT_BACK_1_POSITION);
        }
        else if (random <= WizardStats.ARCANE_TELEPORT_BACK_2_RANDOM) {
            enemy.changePosition(enemy, -1 * WizardStats.ARCANE_TELEPORT_BACK_2_POSITION);
        }
        else if (random <= WizardStats.ARCANE_TELEPORT_BACK_3_RANDOM) {
            enemy.changePosition(enemy, -1 * WizardStats.ARCANE_TELEPORT_BACK_3_POSITION);
        }
        else {
            setEvasion(WizardStats.ARCANE_TELEPORT_MAX_EVASION); // lucky :)
        }
    }

    public void witcherTreatment() {    // POS1: +0-10 HP + bleed 1(3)
        Unit friend = instance.getUnit(1, isEnemy());

        boolean isCritical = isCritical(getCriticalChance());
        int maxHeal = isCritical ? WizardStats.WITCHER_TREATMENT_MAX_HEAL_AMOUNT_CRIT : WizardStats.WITCHER_TREATMENT_MAX_HEAL_AMOUNT;
        int heal = (int)Math.round((WizardStats.WITCHER_TREATMENT_MIN_HEAL_AMOUNT + Math.random() * (maxHeal - WizardStats.WITCHER_TREATMENT_MIN_HEAL_AMOUNT)));
        friend.setBleed(WizardStats.WITCHER_TREATMENT_BLEED_DAMAGE, WizardStats.WITCHER_TREATMENT_BLEED_DURATION);
        friend.setHealthPoints(friend.getHealthPoints() + heal);
    }
}
