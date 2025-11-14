package Unit;

import Config.GameConstants;
import Exception.UnitPositionException;
import Game.GameBoard;
import Game.UnitListener;
import Game.GameLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Unit implements Cloneable {
    private BooleanProperty isEnemy = new SimpleBooleanProperty();
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty healthPoints = new SimpleIntegerProperty();
    private IntegerProperty defence = new SimpleIntegerProperty();
    private IntegerProperty evasion = new SimpleIntegerProperty();
    private IntegerProperty criticalChance = new SimpleIntegerProperty();
    private BooleanProperty isStunned = new SimpleBooleanProperty(false);
    private IntegerProperty position = new SimpleIntegerProperty(-1);
    private ObservableList<Integer> bleed = FXCollections.observableArrayList();

    protected Unit(boolean isEnemy, String name, int healthPoints, int defence, int evasion, int criticalChance) {
        this.isEnemy.set(isEnemy);
        this.name.set(name);
        this.healthPoints.set(healthPoints);
        this.defence.set(defence);
        this.evasion.set(evasion);
        this.criticalChance.set(criticalChance);
        UnitListener.addListeners(this);
    }

    protected Unit() {}

    @Override
    public Unit clone() {
        try {
            Unit cloned = (Unit) super.clone();
            cloned.isEnemy = new SimpleBooleanProperty(this.isEnemy.get());
            cloned.name = new SimpleStringProperty(this.name.get());
            cloned.healthPoints = new SimpleIntegerProperty(this.healthPoints.get());
            cloned.defence = new SimpleIntegerProperty(this.defence.get());
            cloned.evasion = new SimpleIntegerProperty(this.evasion.get());
            cloned.criticalChance = new SimpleIntegerProperty(this.criticalChance.get());
            cloned.isStunned = new SimpleBooleanProperty(this.isStunned.get());
            cloned.position = new SimpleIntegerProperty(this.position.get());
            cloned.bleed = FXCollections.observableArrayList(this.bleed);
            UnitListener.addListeners(cloned);
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new AssertionError();
        }
    }

    public void act() {
        GameLogger.addLogEntry("\n" + "-".repeat(50) + "\n");

        if (!getBleed().isEmpty()) {
            GameLogger.addLogEntry(String.format("%s (%d): истекает кровью", this.getName(), this.getPosition()));
            bleed();
        }

        if (isStunned()) {
            GameLogger.addLogEntry(String.format("%s (%d): оглушен и пропускает ход", this.getName(), this.getPosition()));
            setStunned(false);
            return;
        }

        performAction();
    }

    abstract public void performAction();
    abstract public UnitType getUnitType();

    public int stateHash() {
        return Objects.hash(
                getUnitType(),
                isEnemy(),
                getName(),
                getHealthPoints(),
                getDefence(),
                getEvasion(),
                getCriticalChance(),
                isStunned(),
                getPosition(),
                getBleed()
        );
    }

    public int calculateDamage(int baseDamage, int maxDamage, int defence, boolean isCritical) {
        if (isCritical) {
            baseDamage = (int)Math.round((double)baseDamage * GameConstants.CRIT_MULTIPLIER);
            maxDamage = (int)Math.round((double)maxDamage * GameConstants.CRIT_MULTIPLIER);
        }
        return (int)Math.round((baseDamage + Math.random() * (maxDamage - baseDamage)) * (100 - defence) / 100.0);
    }

    public void bleed() {
        ArrayList<Integer> currentBleed = getBleed();
        int damage = currentBleed.get(0);
        currentBleed.remove(0);
        bleed.setAll(currentBleed);
        setHealthPoints(getHealthPoints() - damage);
    }

    public void changePosition(Unit unit, int change) { // -change: back; +change: forward
        int oldPosition = unit.getPosition();
        int newPosition;
        if (change < 0) {
            int lastPosition = GameBoard.getInstance().getLastPosition(isEnemy());
            newPosition = Math.min(oldPosition - change, lastPosition);
        }
        else {
            newPosition = Math.max(oldPosition - change, 1);
        }
        GameBoard.getInstance().addUnit(unit, newPosition);
        GameBoard.getInstance().buryTheDead(unit);
        GameBoard.getInstance().setSquadPositions(unit.isEnemy());
    }

    public boolean isCritical(int criticalChance) {
        boolean isCritical = Math.random() * 100 < criticalChance;
        if (isCritical) GameLogger.addLogEntry("КРИТ!");
        return isCritical;
    }

    public boolean isEvade(int evasion) {
        boolean isEvade = Math.random() * 100 < evasion;
        if (isEvade)  GameLogger.addLogEntry("ЦЕЛЬ УКЛОНИЛАСЬ ОТ АТАКИ!");
        return isEvade;
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
        if (healthPoints <= 0) GameBoard.getInstance().buryTheDead(this);
    }

    public int getDefence() {
        return defence.get();
    }

    public void setDefence(int defence) {
        if (defence > GameConstants.MAX_DEFENCE) defence = GameConstants.MAX_DEFENCE;
        this.defence.set(defence);
    }

    public int getEvasion() {
        return evasion.get();
    }

    public void setEvasion(int evasion) {
        if (evasion > GameConstants.MAX_EVASION) evasion = GameConstants.MAX_EVASION;
        this.evasion.set(evasion);
    }

    public int getCriticalChance() {
        return criticalChance.get();
    }

    public void setCriticalChance(int criticalChance) {
        if (criticalChance > GameConstants.MAX_CRITICAL_CHANCE) criticalChance = GameConstants.MAX_CRITICAL_CHANCE;
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
        UnitPositionException.throwIfInvalidPosition(position);
        this.position.set(position);
    }

    public ArrayList<Integer> getBleed() {
        return new ArrayList<>(bleed);
    }

    public void setBleed(List<Integer> bleed) {
        this.bleed.setAll(bleed);
    }
    public void setBleed(int value, int duration) {
        ArrayList<Integer> currentBleed = getBleed();
        int size = currentBleed.size();
        if (currentBleed.size() < duration) {
            for (int i = 0; i < duration - size; i++) {
                currentBleed.add(0);
            }
        }
        for (int i = 0; i < duration; i++) {
            currentBleed.set(i, currentBleed.get(i) + value);
        }
        bleed.setAll(currentBleed);
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

    public ObservableList<Integer> getBleedProperty() { return bleed; }

    public BooleanProperty getIsStunnedProperty() {
        return isStunned;
    }

    public IntegerProperty getPositionProperty() {
        return position;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Unit unit
                && this.isEnemy.equals(unit.isEnemy)
                && this.position.equals(unit.position);
    }
}
