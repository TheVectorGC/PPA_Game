package Game;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class UnitListeners {
    public static void addListeners(Unit unit) {
        addIsEnemyListener(unit);
        addNameListener(unit);
        addHealthPointsListener(unit);
        addDefenceListener(unit);
        addEvasionListener(unit);
        addCriticalChanceListener(unit);
        addBleedDamageListener(unit);
        addIsBleedListener(unit);
        addIsStunnedListener(unit);
        addPositionListener(unit);
        addIsDeadListener(unit);
        addIsCriticalListener(unit);
        addIsEvadeListener(unit);
    }

    private static void addIsEnemyListener(Unit unit) {
        unit.isEnemyProperty().addListener((observable, oldValue, newValue) -> {
            // Логика при изменении значения isEnemy
        });
    }

    private static void addNameListener(Unit unit) {
        unit.nameProperty().addListener((observable, oldValue, newValue) -> {
            // Логика при изменении значения name
        });
    }

    private static void addHealthPointsListener(Unit unit) {
        unit.healthPointsProperty().addListener((observable, oldValue, newValue) -> {
            // Логика при изменении значения healthPoints
        });
    }

    private static void addDefenceListener(Unit unit) {
        unit.defenceProperty().addListener((observable, oldValue, newValue) -> {
            // Логика при изменении значения defence
        });
    }

    private static void addEvasionListener(Unit unit) {
        unit.evasionProperty().addListener((observable, oldValue, newValue) -> {
            // Логика при изменении значения evasion
        });
    }

    private static void addCriticalChanceListener(Unit unit) {
        unit.criticalChanceProperty().addListener((observable, oldValue, newValue) -> {
            // Логика при изменении значения criticalChance
        });
    }

    private static void addBleedDamageListener(Unit unit) {
        unit.bleedDamageProperty().addListener((observable, oldValue, newValue) -> {
            // Логика при изменении значения bleedDamage
        });
    }

    private static void addIsBleedListener(Unit unit) {
        unit.isBleedProperty().addListener((observable, oldValue, newValue) -> {
            // Логика при изменении значения isBleed
        });
    }

    private static void addIsStunnedListener(Unit unit) {
        unit.isStunnedProperty().addListener((observable, oldValue, newValue) -> {
            // Логика при изменении значения isStunned
        });
    }

    private static void addPositionListener(Unit unit) {
        unit.positionProperty().addListener((observable, oldValue, newValue) -> {
            // Логика при изменении значения position
        });
    }

    private static void addIsDeadListener(Unit unit) {
        unit.isDeadProperty().addListener((observable, oldValue, newValue) -> {
            // Логика при изменении значения isDead
        });
    }

    private static void addIsCriticalListener(Unit unit) {
        unit.isCriticalProperty().addListener((observable, oldValue, newValue) -> {
            // Логика при изменении значения isCritical
        });
    }

    private static void addIsEvadeListener(Unit unit) {
        unit.isEvadeProperty().addListener((observable, oldValue, newValue) -> {
            // Логика при изменении значения isEvade
        });
    }
}
