package Game;

import Units.Unit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class GameLogger {
    private static final ObservableList<String> logEntry = FXCollections.observableArrayList();
    private GameLogger(){}
    public static ObservableList<String> getLogEntries() {
        return logEntry;
    }

    public static void addLogEntry(String entry) {
        logEntry.add(entry);
        System.out.println(entry); // Для консольного вывода
    }

    public static void logIsEnemyChange(Unit unit, boolean oldValue, boolean newValue) {
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n\n").append("-".repeat(50)).append("\n\n");
        if (newValue) {
            logBuilder.append(String.format("%s (%d) теперь сражается на стороне врага", oldValue, unit.getPosition()));
        }
        else {
            logBuilder.append(String.format("%s (%d) теперь сражается на вашей стороне", oldValue, unit.getPosition()));
        }
        addLogEntry(logBuilder.toString());
    }

    public static void logNameChange(Unit unit, String oldValue, String newValue) {
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n\n").append("-".repeat(50)).append("\n\n");
        logBuilder.append(String.format("%s (%d) имя изменено -> %s (%d)", oldValue, unit.getPosition(), newValue, unit.getPosition()));
        logBuilder.setLength(0);
        addLogEntry(logBuilder.toString());
    }

    public static void logHealthPointsChange(Unit unit, int oldValue, int newValue) {
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n\n").append("-".repeat(50)).append("\n\n");
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
        logBuilder.append("\n\n").append("-".repeat(50)).append("\n\n");
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
        logBuilder.append("\n\n").append("-".repeat(50)).append("\n\n");
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
        logBuilder.append("\n\n").append("-".repeat(50)).append("\n\n");
        if (newValue > oldValue) {
            logBuilder.append(String.format("%s (%d) шанс крита увеличен: %d\n", unit.getName(), unit.getPosition(), newValue - oldValue));
        }
        else {
            logBuilder.append(String.format("%s (%d) шанс крита уменьшен: %d\n", unit.getName(), unit.getPosition(), oldValue - newValue));
        }
        logBuilder.append(String.format("%s (%d) шанс крита %d -> %d", unit.getName(), unit.getPosition(), oldValue, newValue));
        addLogEntry(logBuilder.toString());
    }

    public static void logIsStunnedChange(Unit unit, boolean oldValue, boolean newValue) {
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n\n").append("-".repeat(50)).append("\n\n");
        if (!newValue) {
            logBuilder.append(String.format("%s (%d) пропускает ход\n", unit.getName(), unit.getPosition()));
        }
        logBuilder.append(String.format("%s (%d) оглушён -> %b", unit.getName(), unit.getPosition(), unit.isStunned()));
        addLogEntry(logBuilder.toString());
    }

    public static void logPositionChange(Unit unit, int oldValue, int newValue) {
        String logBuilder = "\n\n" + "-".repeat(50) + "\n\n" + String.format("%s (%d) позиция %d -> %d", unit.getName(), unit.getPosition(), oldValue, newValue);
        addLogEntry(logBuilder);
    }

    public static void logBleedChange(Unit unit, int oldValue, int newValue) {
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n\n").append("-".repeat(50)).append("\n\n");
        logBuilder.append(String.format("%s (%d) кровотечение -> { ", unit.getName(), unit.getPosition()));
        for (int i = newValue; i > 0; i--) {
            logBuilder.append(unit.getBleedDamage(i - 1)).append(" ");
        }
        logBuilder.append("}");
        addLogEntry(logBuilder.toString());
    }
}
