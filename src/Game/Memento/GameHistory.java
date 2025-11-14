package Game.Memento;

import Config.GameConstants;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class GameHistory {
    private final Deque<GameStateMemento> undoStack = new ArrayDeque<>();
    private final Deque<GameStateMemento> redoStack = new ArrayDeque<>();

    public void saveState(GameStateMemento memento) {
        undoStack.push(memento);
        redoStack.clear();

        if (undoStack.size() > GameConstants.UNDO_REDO_MAX_HISTORY) {
            undoStack.removeLast();
        }
    }

    /*
    public GameStateMemento undo() {
        if (!canUndo()) return null;

        GameStateMemento currentState = undoStack.pop();
        redoStack.push(currentState);
        return undoStack.peek();
    }

    public GameStateMemento redo() {
        if (!canRedo()) return null;

        GameStateMemento state = redoStack.pop();
        undoStack.push(state);
        return state;
    }

     */

    public GameStateMemento undo() {
        if (undoStack.size() < 2) return null;
        GameStateMemento currentState = undoStack.peek();
        Iterator<GameStateMemento> iterator = undoStack.iterator();
        iterator.next();
        GameStateMemento previousState = iterator.next();

        redoStack.push(currentState);
        return previousState;
    }

    public GameStateMemento redo() {
        if (!canRedo()) return null;

        return redoStack.pop();
    }

    public boolean canUndo() {
        return undoStack.size() > 1;
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public void clearUndo() {
        undoStack.clear();
    }

    public void clearRedo() {
        redoStack.clear();
    }
}