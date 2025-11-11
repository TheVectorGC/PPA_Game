package Game.SaveLoad;

import Game.GameLogger;
import Game.SaveLoad.DTO.GameSaveDTO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SaveLoadManager {
    private static final String SAVES_DIRECTORY = "saves";
    private static final Pattern SAVE_NAME_PATTERN = Pattern.compile("save_(\\d+)\\.save");

    static {
        try {
            Path savesPath = Paths.get(SAVES_DIRECTORY);
            if (!Files.exists(savesPath)) {
                Files.createDirectories(savesPath);
            }
        }
        catch (IOException e) {
            System.err.println("Не удалось создать папку для сохранений: " + e.getMessage());
        }
    }

    public static void saveGame(String customName) {
        try {
            String filename = (customName == null || customName.isBlank())
                    ? generateNextSaveName()
                    : customName.replaceFirst("\\.save$", "");
            Path filePath = Paths.get(SAVES_DIRECTORY, filename + ".save");

            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new BufferedOutputStream(Files.newOutputStream(filePath)))) {
                oos.writeObject(GameStateMapper.toGameStateDTO());
                GameLogger.addLogEntry("Игра сохранена: " + filename);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении игры: " + e.getMessage());
        }
    }

    public static void loadGame(String saveName) {
        try {
            String filename = saveName + ".save";
            Path filePath = Paths.get(SAVES_DIRECTORY, filename);

            if (!Files.exists(filePath)) {
                System.err.println("Файл сохранения не найден: " + filename);
                return;
            }

            try (ObjectInputStream ois = new ObjectInputStream(
                    new BufferedInputStream(Files.newInputStream(filePath)))) {
                GameSaveDTO gameSave = (GameSaveDTO) ois.readObject();
                GameLogger.addLogEntry("Игра загружена: " + filename);
                GameStateMapper.fromGameStateDTO(gameSave);
            }
        }
        catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке игры: " + e.getMessage());
        }
    }

    public static List<String> getAvailableSaves() {
        try {
            List<String> saves = new ArrayList<>();
            File savesDir = new File(SAVES_DIRECTORY);

            if (savesDir.exists() && savesDir.isDirectory()) {
                File[] files = savesDir.listFiles((dir, name) -> name.endsWith(".save"));
                if (files != null) {
                    for (File file : files) {
                        String name = file.getName();
                        saves.add(name.substring(0, name.length() - 6));
                    }
                }
            }
            return saves;
        }
        catch (Exception e) {
            System.err.println("Ошибка при получении списка сохранений: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void deleteSave(String saveName) {
        try {
            String filename = saveName + ".save";
            Path filePath = Paths.get(SAVES_DIRECTORY, filename);

            Files.deleteIfExists(filePath);
            GameLogger.addLogEntry("Сохранение удалено: " + filename);
        }
        catch (IOException e) {
            System.err.println("Ошибка при удалении сохранения: " + e.getMessage());
        }
    }

    private static String generateNextSaveName() {
        List<String> saves = getAvailableSaves();
        int maxNumber = 0;

        for (String save : saves) {
            var matcher = SAVE_NAME_PATTERN.matcher(save + ".save");
            if (matcher.matches()) {
                try {
                    int number = Integer.parseInt(matcher.group(1));
                    if (number > maxNumber) {
                        maxNumber = number;
                    }
                }
                catch (NumberFormatException e) {
                    System.err.println("Ошибка при генерации имени файла: " + e.getMessage());
                }
            }
        }

        return "save_" + (maxNumber + 1);
    }
}
