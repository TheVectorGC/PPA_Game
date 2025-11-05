package Game.Strategy;

import Game.GameBoard;

public class AutoTurnStrategy implements GameTurnStrategy {

    private volatile boolean running = false;

    @Override
    public void execute() {
        running = true;
        GameBoard board = GameBoard.getInstance();

        while (running && !board.isGameOver()) {
            board.performSingleTurn();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
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
