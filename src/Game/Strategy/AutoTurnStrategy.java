package Game.Strategy;

import Game.GameBoard;

public class AutoTurnStrategy implements GameTurnStrategy {

    private volatile boolean running = false;

    @Override
    public void execute() {
        running = true;
        GameBoard gameBoard = GameBoard.getInstance();

        while (running && !gameBoard.isGameOver()) {
            gameBoard.executeTurnWithSave();
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                break;
            }
        }

        running = false;
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
