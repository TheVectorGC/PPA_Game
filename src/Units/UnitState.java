package Units;

public class UnitState {
    int healthPoints;
    int defence;
    int evasion;
    int criticalChance;
    int[] bleedDamage;
    boolean isBleed;
    boolean isStunned;
    int position;
    public UnitState(int healthPoints, int defence, int evasion, int criticalChance, int[] bleedDamage, boolean isBleed, boolean isStunned, int position) {
        this.healthPoints = healthPoints;
        this.defence = defence;
        this.evasion = evasion;
        this.criticalChance = criticalChance;
        this.bleedDamage = bleedDamage;
        this.isBleed = isBleed;
        this.isStunned = isStunned;
        this.position = position;
    }
}
