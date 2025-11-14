package Game.Memento;

import Game.SaveLoad.DTO.GameSaveDTO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Optional;

public class GameHistory {
    private static final String TEMP_BASE_PATH = "saves/temp";
    private static final String UNDO_PATH = TEMP_BASE_PATH + "/undo";
    private static final String REDO_PATH = TEMP_BASE_PATH + "/redo";

    public GameHistory() {
        createDirectories();
    }

    private void createDirectories() {
        try {
            Files.createDirectories(Paths.get(UNDO_PATH));
            Files.createDirectories(Paths.get(REDO_PATH));
        }
        catch (IOException e) {
            System.err.println("Не удалось создать папки для истории: " + e.getMessage());
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
            Optional<Path> lastUndoFile = getLastFile(UNDO_PATH);
            if (lastUndoFile.isPresent()) {
                Path undoFile = lastUndoFile.get();
                GameSaveDTO state = loadState(undoFile);

                String filename = "state_" + System.nanoTime() + ".save";
                Path redoFile = Paths.get(REDO_PATH, filename);
                Files.move(undoFile, redoFile, StandardCopyOption.REPLACE_EXISTING);

                return state;
            }
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

    private Optional<Path> getLastFile(String directory) {
        try {
            return Files.list(Paths.get(directory))
                    .filter(path -> path.toString().endsWith(".save"))
                    .max(Comparator.comparing(path -> {
                        try {
                            String filename = path.getFileName().toString();
                            String timestampStr = filename.substring(6, filename.length() - 5);
                            return Long.parseLong(timestampStr);
                        } catch (Exception e) {
                            return 0L;
                        }
                    }));
        }
        catch (IOException e) {
            return Optional.empty();
        }
    }

    public boolean canUndo() {
        return getLastFile(UNDO_PATH).isPresent();
    }

    public boolean canRedo() {
        return getLastFile(REDO_PATH).isPresent();
    }

    public void clearRedo() {
        try {
            Files.list(Paths.get(REDO_PATH))
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