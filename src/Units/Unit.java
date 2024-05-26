package Units;

import Game.GameBoard;

public abstract class Unit implements Cloneable {

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
    protected GameBoard instance = GameBoard.getInstance();

    protected Unit(boolean isEnemy, String name, int healthPoints, int defence, int evasion, int criticalChance) {
        this.isEnemy = isEnemy;
        this.name = name;
        this.healthPoints = healthPoints;
        this.defence = defence;
        this.evasion = evasion;
        this.criticalChance = criticalChance;
    }

    protected Unit() {}

    @Override
    public Unit clone() {
        try {
            return (Unit) super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new AssertionError();
        }
    }

    public boolean bleed() {
        if (isBleed) {
            int lastPositiveElement = 0;
            for (int i = bleedDamage.length - 1; i > 0; i--) {
                if (bleedDamage[i] != 0) {
                    lastPositiveElement = i;
                    healthPoints -= bleedDamage[lastPositiveElement];
                    break;
                }
            }
            if (isDead()) return true;
            bleedDamage[lastPositiveElement] = 0;
            if (bleedDamage[0] == 0) isBleed = false;
        }
        return false;
    }

    public boolean attack(int baseDamage, int maxDamage, boolean isCritical, Unit unit) {
        if (isEvade(unit.evasion)) return false;
        if (isCritical) {
            baseDamage = (int)Math.ceil((double)baseDamage * 1.5);
            maxDamage = (int)Math.ceil((double)maxDamage * 1.5);
        }
        int damage = (int)Math.ceil((baseDamage + Math.random() * (maxDamage - baseDamage)) * (100 - unit.defence) / 100.0);
        unit.healthPoints -= damage;
        return isDead();
    }

    public boolean isDead() {
        if (healthPoints <= 0) {
            instance.buryTheDead(isEnemy);
            return true;
        }
        return false;
    }
    public boolean isCritical(int criticalChance) {
        return Math.random() * 100 < criticalChance;
    }

    private boolean isEvade(int evasion) {
        return Math.random() * 100 < evasion;
    }

    public void setIsEnemy(boolean isEnemy) {
        this.isEnemy = isEnemy;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }

    public void setCriticalChance(int criticalChance) {
        this.criticalChance = criticalChance;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setBleedDamage(int damage, int duration) {
        for (int i = 0; i < duration; i++) {
            bleedDamage[i] = bleedDamage[i] + damage;
        }
    }
    abstract public void act();
}
