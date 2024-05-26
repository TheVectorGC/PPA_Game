package Game;

public class EventLogger   {
    protected boolean isEnemy;
    protected String name;
    protected int healthPoints;
    protected int defence;
    protected int evasion;
    protected int criticalChance;
    protected int[] bleedDamage = new int[] {0, 0, 0, 0};
    protected boolean isBleed = false;
    protected boolean isStunned = false;
    protected int position = -1;
    protected boolean isDead;
    protected boolean isCritical;

    protected boolean isEvade;
}
