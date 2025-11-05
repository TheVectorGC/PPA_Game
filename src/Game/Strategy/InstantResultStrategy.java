package Game.Strategy;

import Game.GameBoard;

public class InstantResultStrategy implements GameTurnStrategy {
    private final GameBoard gameBoard = GameBoard.getInstance();

    @Override
    public void execute() {
        while (true) {
            gameBoard.executeTurn();
        }
    }
}
