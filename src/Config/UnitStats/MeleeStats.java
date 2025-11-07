package Config.UnitStats;

public final class MeleeStats {
    private MeleeStats() {}

    public static final int BASE_HP = 20;
    public static final int BASE_DEFENCE = 0;
    public static final int BASE_EVASION = 30;
    public static final int BASE_CRIT_CHANCE = 35;
    public static final int COST = 100;
    public static final String BASE_NAME = "БЛИЖНИК";
    public static final String ENEMY_NAME = "БЛИЖНИК-ВРАГ";

    public static final int SNEAKY_BLOW_BASE_DMG = 4;
    public static final int SNEAKY_BLOW_MAX_DMG = 5;
    public static final int SNEAKY_BLOW_BLEED_DMG = 2;
    public static final int SNEAKY_BLOW_BLEED_DURATION = 3;

    public static final int SNEAKY_THROW_BASE_DMG = 3;
    public static final int SNEAKY_THROW_MAX_DMG = 4;
    public static final int SNEAKY_THROW_BLEED_DMG = 1;
    public static final int SNEAKY_THROW_BLEED_DURATION = 3;

    public static final int KNIFE_SHARPENING_CRIT_BONUS = 10;
    public static final int KNIFE_SHARPENING_CRIT_BONUS_CRIT = 15;

    public static final String NOSE_PICKING_CRIT_BASE_NAME = "КРИТИЧЕСКИЙ ОЛУХ";
    public static final String NOSE_PICKING_CRIT_ENEMY_NAME = "КРИТИЧЕСКИЙ ОЛУХ-ВРАГ";
}
