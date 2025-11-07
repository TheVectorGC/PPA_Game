package Config.UnitStats;

public final class WizardStats {
    private WizardStats() {}

    public static final int BASE_HP = 15;
    public static final int BASE_DEFENCE = 10;
    public static final int BASE_EVASION = 10;
    public static final int BASE_CRIT_CHANCE = 10;
    public static final int COST = 150;
    public static final String BASE_NAME = "КОЛДУН";
    public static final String ENEMY_NAME = "КОЛДУН-ВРАГ";

    public static final int BONK_BASE_DMG = 1;
    public static final int BONK_MAX_DMG = 2;
    public static final int BONK_STUN_CHANCE = 75;

    public static final int FIREBALL_BASE_DMG = 0;
    public static final int FIREBALL_MAX_DMG = 15;
    public static final int FIREBALL_INSTAKILL_CHANCE = 10;
    public static final int FIREBALL_INSTAKILL_DAMAGE = 999999999;

    public static final double ARCANE_TELEPORT_BACK_1_RANDOM = 33.0;
    public static final double ARCANE_TELEPORT_BACK_2_RANDOM = 66.0;
    public static final double ARCANE_TELEPORT_BACK_3_RANDOM = 99.0;
    public static final int ARCANE_TELEPORT_BACK_1_POSITION = 1;
    public static final int ARCANE_TELEPORT_BACK_2_POSITION = 2;
    public static final int ARCANE_TELEPORT_BACK_3_POSITION = 3;
    public static final int ARCANE_TELEPORT_MAX_EVASION = 70;

    public static final int WITCHER_TREATMENT_MIN_HEAL_AMOUNT = 0;
    public static final int WITCHER_TREATMENT_MAX_HEAL_AMOUNT = 10;
    public static final int WITCHER_TREATMENT_MAX_HEAL_AMOUNT_CRIT = 15;
    public static final int WITCHER_TREATMENT_BLEED_DURATION = 3;
    public static final int WITCHER_TREATMENT_BLEED_DAMAGE = 1;
}
