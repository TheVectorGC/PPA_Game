package Game.Strategy;

import Game.GameBoard;

public class InstantResultStrategy implements GameTurnStrategy {

    private final GameBoard gameBoard = GameBoard.getInstance();

    @Override
    public void execute() {
        while (!gameBoard.isGameOver() && !Thread.currentThread().isInterrupted()) {
            gameBoard.performSingleTurn();
        }
    }
}
