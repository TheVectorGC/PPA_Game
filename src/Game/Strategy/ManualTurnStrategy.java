package Game.Strategy;

import Game.GameBoard;
import java.util.Scanner;

public class ManualTurnStrategy implements GameTurnStrategy{
    private final GameBoard gameBoard = GameBoard.getInstance();
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public void execute() {
        while (true) {
            gameBoard.executeTurnWithSave();
            scanner.nextLine();
        }
    }
}
