package Game.SaveLoad;

import Game.SaveLoad.DTO.GameSaveDTO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameHistory {
    private static final String TEMP_BASE_PATH = "saves/temp";
    private static final String UNDO_PATH = TEMP_BASE_PATH + "/undo";
    private static final String REDO_PATH = TEMP_BASE_PATH + "/redo";

    public GameHistory() {
        createDirectories();
    }

    private void createDirectories() {
        try {
            Path tempPath = Paths.get(TEMP_BASE_PATH);
            if (Files.exists(tempPath)) {
                deleteDirectory(tempPath);
            }

            Files.createDirectories(Paths.get(UNDO_PATH));
            Files.createDirectories(Paths.get(REDO_PATH));
        }
        catch (IOException e) {
            System.err.println("Не удалось создать папки для истории: " + e.getMessage());
        }
    }

    private void deleteDirectory(Path path) {
        try {
            Files.walk(path)
                    .sorted((p1, p2) -> -p1.compareTo(p2))
                    .forEach(p -> {
                        try {
                            Files.delete(p);
                        } catch (IOException e) {
                            System.err.println("Не удалось удалить: " + p);
                        }
                    });
        }
        catch (IOException e) {
            System.err.println("Ошибка при удалении папки: " + e.getMessage());
        }
    }

    public void saveState(GameSaveDTO gameState) {
        try {
            clearRedo();

            String filename = "state_" + System.nanoTime() + ".save";
            Path filePath = Paths.get(UNDO_PATH, filename);

            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new BufferedOutputStream(Files.newOutputStream(filePath)))) {
                oos.writeObject(gameState);
            }
        }
        catch (IOException e) {
            System.err.println("Ошибка при сохранении состояния истории: " + e.getMessage());
        }
    }

    public GameSaveDTO undo() {
        try {
            List<Path> undoFiles = getSortedFiles(UNDO_PATH);

            if (undoFiles.size() < 2) {
                return null;
            }

            Path previousStateFile = undoFiles.get(1);
            GameSaveDTO state = loadState(previousStateFile);

            Path currentStateFile = undoFiles.get(0);
            String filename = "state_" + System.nanoTime() + ".save";
            Path redoFile = Paths.get(REDO_PATH, filename);
            Files.move(currentStateFile, redoFile, StandardCopyOption.REPLACE_EXISTING);

            return state;
        }
        catch (IOException e) {
            System.err.println("Ошибка при отмене: " + e.getMessage());
        }
        return null;
    }

    public GameSaveDTO redo() {
        try {
            Optional<Path> lastRedoFile = getLastFile(REDO_PATH);
            if (lastRedoFile.isPresent()) {
                Path redoFile = lastRedoFile.get();
                GameSaveDTO state = loadState(redoFile);

                String filename = "state_" + System.nanoTime() + ".save";
                Path undoFile = Paths.get(UNDO_PATH, filename);
                Files.move(redoFile, undoFile, StandardCopyOption.REPLACE_EXISTING);

                return state;
            }
        }
        catch (IOException e) {
            System.err.println("Ошибка при повторе: " + e.getMessage());
        }
        return null;
    }

    private GameSaveDTO loadState(Path filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(Files.newInputStream(filePath)))) {
            return (GameSaveDTO) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке состояния: " + e.getMessage());
            return null;
        }
    }

    private List<Path> getSortedFiles(String directory) {
        Path dir = Paths.get(directory);
        if (!Files.exists(dir)) return List.of();

        try (var stream = Files.list(dir)) {
            return stream
                    .filter(p -> p.toString().endsWith(".save"))
                    .sorted((p1, p2) -> Long.compare(
                            extractTimestamp(p2.getFileName().toString()),
                            extractTimestamp(p1.getFileName().toString())
                    ))
                    .collect(Collectors.toList());
        }
        catch (IOException e) {
            return List.of();
        }
    }

    private long extractTimestamp(String filename) {
        if (filename == null) return 0L;
        int underscore = filename.indexOf('_');
        int dot = filename.lastIndexOf('.');
        if (underscore < 0 || dot < 0 || dot <= underscore) return 0L;
        String ts = filename.substring(underscore + 1, dot);
        try {
            return Long.parseLong(ts);
        }
        catch (NumberFormatException ignored) {
            return 0L;
        }
    }

    private Optional<Path> getLastFile(String directory) {
        List<Path> files = getSortedFiles(directory);
        return files.isEmpty() ? Optional.empty() : Optional.of(files.get(0));
    }

    public boolean canUndo() {
        return getSortedFiles(UNDO_PATH).size() >= 2;
    }

    public boolean canRedo() {
        return !getSortedFiles(REDO_PATH).isEmpty();
    }

    public void clearRedo() {
        clearDirectory(REDO_PATH);
    }

    public void clearAll() {
        clearDirectory(UNDO_PATH);
        clearDirectory(REDO_PATH);
    }

    private void clearDirectory(String directory) {
        try {
            Files.list(Paths.get(directory))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            System.err.println("Не удалось удалить файл: " + path);
                        }
                    });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}