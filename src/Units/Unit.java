package Units;

import Exceptions.UnitPositionException;
import Game.GameBoard;
import Game.UnitListeners;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Unit implements Cloneable {
    private final BooleanProperty isEnemy = new SimpleBooleanProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty healthPoints = new SimpleIntegerProperty();
    private final IntegerProperty defence = new SimpleIntegerProperty();
    private final IntegerProperty evasion = new SimpleIntegerProperty();
    private final IntegerProperty criticalChance = new SimpleIntegerProperty();
    private final BooleanProperty isStunned = new SimpleBooleanProperty(false);
    private final IntegerProperty position = new SimpleIntegerProperty(-1);
    private final IntegerProperty bleedDuration = new SimpleIntegerProperty(0);
    private final int[] bleedDamage = new int[] { 0, 0, 0, 0 };
    protected GameBoard instance = GameBoard.getInstance();

    protected Unit(boolean isEnemy, String name, int healthPoints, int defence, int evasion, int criticalChance) {
        this.isEnemy.set(isEnemy);
        this.name.set(name);
        this.healthPoints.set(healthPoints);
        this.defence.set(defence);
        this.evasion.set(evasion);
        this.criticalChance.set(criticalChance);
        UnitListeners.addListeners(this);
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

    public int calculateDamage(int baseDamage, int maxDamage, int defence, boolean isCritical) {
        if (isCritical) {
            baseDamage = (int)Math.ceil((double)baseDamage * 1.5);
            maxDamage = (int)Math.ceil((double)maxDamage * 1.5);
        }
        return (int)Math.ceil((baseDamage + Math.random() * (maxDamage - baseDamage)) * (100 - defence) / 100.0);
    }

    public void bleed() {
        int duration = getBleedDuration() - 1;
        int damage = getBleedDamage(duration);
        setBleedDamage(0, duration);
        setBleedDuration(duration);
        setHealthPoints(getHealthPoints() - damage);
    }

    public void changePosition(Unit unit, int change) { // -change: back; +change: forward
        int oldPosition = unit.getPosition();
        int newPosition;
        if (change < 0) {
            int lastPosition = instance.getLastPosition(isEnemy());
            newPosition = Math.min(oldPosition - change, lastPosition);
        }
        else {
            newPosition = Math.max(oldPosition - change, 1);
        }
        boolean isEnemy = unit.isEnemy();
        instance.addUnit(unit, isEnemy, newPosition);
        instance.buryTheDead(unit);
        instance.setSquadPositions(isEnemy);
    }

    public boolean isCritical(int criticalChance) {
        return Math.random() * 100 < criticalChance;
    }

    public boolean isEvade(int evasion) {
        return Math.random() * 100 < evasion;
    }

    public boolean isEnemy() {
        return isEnemy.get();
    }

    public void setEnemy(boolean isEnemy) {
        this.isEnemy.set(isEnemy);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getHealthPoints() {
        return healthPoints.get();
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints.set(healthPoints);
        if (healthPoints <= 0) instance.buryTheDead(this);
    }

    public int getDefence() {
        return defence.get();
    }

    public void setDefence(int defence) {
        if (defence > 70) defence = 70;
        this.defence.set(defence);
    }

    public int getEvasion() {
        return evasion.get();
    }

    public void setEvasion(int evasion) {
        if (evasion > 70) evasion = 70;
        this.evasion.set(evasion);
    }

    public int getCriticalChance() {
        return criticalChance.get();
    }

    public void setCriticalChance(int criticalChance) {
        if (criticalChance > 100) criticalChance = 100;
        this.criticalChance.set(criticalChance);
    }

    public boolean isStunned() {
        return isStunned.get();
    }

    public void setStunned(boolean isStunned) {
        this.isStunned.set(isStunned);
    }

    public int getPosition() {
        return position.get();
    }

    public void setPosition(int position) {
        try {
            if (!(position == 1 || position == 2 || position == 3 || position == 4)) {
                throw new UnitPositionException("Invalid position value in setPosition");
            }
        }
        catch (UnitPositionException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        this.position.set(position);
    }

    public int getBleedDuration() {
        return bleedDuration.get();
    }

    public void setBleedDuration(int bleedDuration) {
        this.bleedDuration.set(bleedDuration);
    }

    public int getBleedDamage(int index) {
        return bleedDamage[index];
    }

    public void setBleedDamage(int value, int duration) {
        for (int i = 0; i < duration; i++) {
            this.bleedDamage[i] = getBleedDamage(i) + value;
        }
    }

    public BooleanProperty getIsEnemyProperty() {
        return isEnemy;
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public IntegerProperty getHealthPointsProperty() {
        return healthPoints;
    }

    public IntegerProperty getDefenceProperty() {
        return defence;
    }

    public IntegerProperty getEvasionProperty() {
        return evasion;
    }

    public IntegerProperty getCriticalChanceProperty() {
        return criticalChance;
    }

    public IntegerProperty getBleedDurationProperty() {
        return bleedDuration;
    }

    public BooleanProperty getIsStunnedProperty() {
        return isStunned;
    }

    public IntegerProperty getPositionProperty() {
        return position;
    }

    abstract public void act();
}
