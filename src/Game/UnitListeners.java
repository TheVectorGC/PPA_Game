package Game;

import Units.Unit;
import javafx.collections.ListChangeListener;

public class UnitListeners {
    private static final StringBuilder logBuilder = new StringBuilder();
    private UnitListeners(){}
    public static void addListeners(Unit unit) {
        addIsEnemyListener(unit);
        addNameListener(unit);
        addHealthPointsListener(unit);
        addDefenceListener(unit);
        addEvasionListener(unit);
        addCriticalChanceListener(unit);
        addIsStunnedListener(unit);
        addPositionListener(unit);
        addBleedListener(unit);
    }

    private static void addIsEnemyListener(Unit unit) {
        unit.getIsEnemyProperty().addListener((observable, oldValue, newValue) -> {
            GameLogger.logIsEnemyChange(unit, oldValue, newValue);
        });
    }

    private static void addNameListener(Unit unit) {
        unit.getNameProperty().addListener((observable, oldValue, newValue) -> {
            GameLogger.logNameChange(unit, oldValue, newValue);
        });
    }

    private static void addHealthPointsListener(Unit unit) {
        unit.getHealthPointsProperty().addListener((observable, oldValue, newValue) -> {
            GameLogger.logHealthPointsChange(unit, (int)oldValue, (int)newValue);
        });
    }

    private static void addDefenceListener(Unit unit) {
        unit.getDefenceProperty().addListener((observable, oldValue, newValue) -> {
            GameLogger.logDefenceChange(unit, (int)oldValue, (int)newValue);
        });
    }

    private static void addEvasionListener(Unit unit) {
        unit.getEvasionProperty().addListener((observable, oldValue, newValue) -> {
            GameLogger.logEvasionChange(unit, (int)oldValue, (int)newValue);
        });
    }

    private static void addCriticalChanceListener(Unit unit) {
        unit.getCriticalChanceProperty().addListener((observable, oldValue, newValue) -> {
            GameLogger.logCriticalChanceChange(unit, (int)oldValue, (int)newValue);
        });
    }

    private static void addIsStunnedListener(Unit unit) {
        unit.getIsStunnedProperty().addListener((observable, oldValue, newValue) -> {
            GameLogger.logIsStunnedChange(unit, oldValue, newValue);
        });
    }

    private static void addPositionListener(Unit unit) {
        unit.getPositionProperty().addListener((observable, oldValue, newValue) -> {
            GameLogger.logPositionChange(unit, (int)oldValue, (int)newValue);
        });
    }

    private static void addBleedListener(Unit unit) {
        unit.getBleedProperty().addListener((ListChangeListener<Integer>) change -> {
            GameLogger.logBleedChange(unit);
        });
    }
}
