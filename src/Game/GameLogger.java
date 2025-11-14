package Game;

import Unit.Unit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class GameLogger {
    private static final ObservableList<String> logEntries = FXCollections.observableArrayList();
    private static final int MAX_LOG_ENTRIES = 200;
    private GameLogger(){}
    public static ObservableList<String> getLogEntries() {
        return logEntries;
    }

    public static void addLogEntry(String entry) {
        if (Platform.isFxApplicationThread()) {
            addEntrySafely(entry);
        } else {
            Platform.runLater(() -> addEntrySafely(entry));
        }
        System.out.println(entry);
    }

    private static void addEntrySafely(String entry) {
        logEntries.add(entry);

        if (logEntries.size() > MAX_LOG_ENTRIES) {
            logEntries.remove(0, logEntries.size() - MAX_LOG_ENTRIES);
        }
    }

    public static void logIsEnemyChange(Unit unit, boolean newValue) {
        if (newValue) {
            addLogEntry(String.format("%s (%d) теперь сражается на стороне врага", unit.getName(), unit.getPosition()));
        }
        else {
            addLogEntry(String.format("%s (%d) теперь сражается на вашей стороне", unit.getName(), unit.getPosition()));
        }
    }

    public static void logNameChange(Unit unit, String oldValue, String newValue) {
        addLogEntry(String.format("%s (%d) имя изменено -> %s (%d)", oldValue, unit.getPosition(), newValue, unit.getPosition()));
    }

    public static void logHealthPointsChange(Unit unit, int oldValue, int newValue) {
        StringBuilder logBuilder = new StringBuilder();
        if (newValue > oldValue) {
            logBuilder.append(String.format("%s (%d) увеличено здоровье: %d\n", unit.getName(), unit.getPosition(), newValue - oldValue));
        }
        else {
            logBuilder.append(String.format("%s (%d) получен урон: %d\n", unit.getName(), unit.getPosition(), oldValue - newValue));
        }
        logBuilder.append(String.format("%s (%d) здоровье %d -> %d", unit.getName(), unit.getPosition(), oldValue, newValue));
        if (newValue <= 0) {
            logBuilder.append(String.format("\n%s (%d): ЛОПАТЫ ВЗЯЛИ? ЕДЕМ НА ПОХОРОНЫ", unit.getName(), unit.getPosition()));
        }
        addLogEntry(logBuilder.toString());
    }

    public static void logDefenceChange(Unit unit, int oldValue, int newValue) {
        StringBuilder logBuilder = new StringBuilder();
        if (newValue > oldValue) {
            logBuilder.append(String.format("%s (%d) защита увеличена: %d\n", unit.getName(), unit.getPosition(), newValue - oldValue));
        }
        else {
            logBuilder.append(String.format("%s (%d) защита уменьшена: %d\n", unit.getName(), unit.getPosition(), oldValue - newValue));
        }
        logBuilder.append(String.format("%s (%d) защита %d -> %d", unit.getName(), unit.getPosition(), oldValue, newValue));
        addLogEntry(logBuilder.toString());
    }

    public static void logEvasionChange(Unit unit, int oldValue, int newValue) {
        StringBuilder logBuilder = new StringBuilder();
        if (newValue > oldValue) {
            logBuilder.append(String.format("%s (%d) уклонение увеличено: %d\n", unit.getName(), unit.getPosition(), newValue - oldValue));
        }
        else {
            logBuilder.append(String.format("%s (%d) уклонение уменьшено: %d\n", unit.getName(), unit.getPosition(), oldValue - newValue));
        }
        logBuilder.append(String.format("%s (%d) уклонение %d -> %d", unit.getName(), unit.getPosition(), oldValue, newValue));
        addLogEntry(logBuilder.toString());
    }

    public static void logCriticalChanceChange(Unit unit, int oldValue, int newValue) {
        StringBuilder logBuilder = new StringBuilder();
        if (newValue > oldValue) {
            logBuilder.append(String.format("%s (%d) шанс крита увеличен: %d\n", unit.getName(), unit.getPosition(), newValue - oldValue));
        }
        else {
            logBuilder.append(String.format("%s (%d) шанс крита уменьшен: %d\n", unit.getName(), unit.getPosition(), oldValue - newValue));
        }
        logBuilder.append(String.format("%s (%d) шанс крита %d -> %d", unit.getName(), unit.getPosition(), oldValue, newValue));
        addLogEntry(logBuilder.toString());
    }

    public static void logIsStunnedChange(Unit unit, boolean newValue) {
        StringBuilder logBuilder = new StringBuilder();
        if (!newValue) {
            logBuilder.append(String.format("%s (%d) пропускает ход\n", unit.getName(), unit.getPosition()));
        }
        logBuilder.append(String.format("%s (%d) оглушён -> %b", unit.getName(), unit.getPosition(), unit.isStunned()));
        addLogEntry(logBuilder.toString());
    }

    public static void logPositionChange(Unit unit, int oldValue, int newValue) {
        addLogEntry(String.format("%s (%d) позиция %d -> %d", unit.getName(), unit.getPosition(), oldValue, newValue));
    }

    public static void logBleedChange(Unit unit) {
        StringBuilder logBuilder = new StringBuilder();
        ArrayList<Integer> bleed = unit.getBleed();
        logBuilder.append(String.format("%s (%d) кровотечение -> { ", unit.getName(), unit.getPosition()));
        for (Integer bleedDamage : bleed) {
            logBuilder.append(bleedDamage).append(" ");
        }
        logBuilder.append("}");
        addLogEntry(logBuilder.toString());
    }
}
