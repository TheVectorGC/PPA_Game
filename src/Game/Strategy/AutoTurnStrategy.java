package Game.Strategy;

import Game.GameBoard;

public class AutoTurnStrategy implements GameTurnStrategy {

    private final GameBoard gameBoard = GameBoard.getInstance();

    @Override
    public void execute() {
        while (true) {
            gameBoard.executeTurnWithSave();
            try {
                Thread.sleep(1000);  // Пауза в 2 секунды между ходами
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
