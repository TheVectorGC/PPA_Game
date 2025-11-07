import Game.Facade.UnitInitializerFacade;
import Game.GameBoard;

import ObjectPool.UnitPool;
import Unit.UnitType;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Main extends Application {
    private static final GameBoard GAME_BOARD = GameBoard.getInstance();
    private static final UnitPool UNIT_POOL = UnitPool.getInstance();

    @Override
    public void start(Stage primaryStage) {
        // Основные размеры экрана
        double baseWidth = 1920;
        double baseHeight = 1080;

        // Вычисление размеров для Pane
        double paneWidth = baseWidth / 12;
        double paneHeight = baseHeight / 4.5;

        // Корневая панель
        BorderPane root = new BorderPane();

        // Верхняя панель с кнопками (пока просто линия)
        Line topLine = new Line(0, baseHeight / 18, baseWidth, baseHeight / 18);
        topLine.setStrokeWidth(4); // Увеличим толщину линии
        root.setTop(new Pane(topLine));

        // Центр, где находятся юниты и активный юнит
        Pane centerField = new Pane();

        // Линия земли
        Line groundLine = new Line(0, baseHeight - baseHeight / 3, baseWidth, baseHeight - baseHeight / 3);
        groundLine.setStrokeWidth(4); // Увеличим толщину линии
        centerField.getChildren().add(groundLine);

        // Создание 12 Pane, которые будут располагаться горизонтально
        for (int i = 0; i < 12; i++) {
            Pane pane = new Pane();
            pane.setLayoutX(i * paneWidth);
            pane.setLayoutY(baseHeight - baseHeight / 3 - paneHeight); // Нижняя граница на groundLine
            pane.setPrefSize(paneWidth, paneHeight);
            pane.setStyle("-fx-border-color: black; -fx-border-width: 1;"); // Уменьшение толщины границы у Pane
            centerField.getChildren().add(pane);
        }

        // Добавление новой линии, разделяющей консоль
        Line consoleLine = new Line(baseWidth / 3, baseHeight * 2 / 3, baseWidth / 3, baseHeight);
        consoleLine.setStrokeWidth(4);
        centerField.getChildren().add(consoleLine);

        // Панель для консоли и вывода логов
        Pane consolePane = new Pane();
        consolePane.setLayoutX(0);
        consolePane.setLayoutY(baseHeight * 2 / 3); // Начало от groundLine
        consolePane.setPrefSize(baseWidth / 3, baseHeight / 3); // Размеры консоли по оставшейся площади
        consolePane.setStyle("-fx-border-color: black;");
        centerField.getChildren().add(consolePane);

        root.setCenter(centerField);

        // Создание сцены и отображение
        Scene scene = new Scene(root, baseWidth, baseHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Interface Test");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        startGame();
    }


    private static void startGame() {
        List<UnitType> unitList = new ArrayList<>();
        unitList.add(UnitType.UNIT_MELEE);
        unitList.add(UnitType.UNIT_MELEE);

        unitList.add(UnitType.UNIT_RANGE);
        unitList.add(UnitType.UNIT_RANGE);

        unitList.add(UnitType.UNIT_HEAVY);
        unitList.add(UnitType.UNIT_HEAVY);

        unitList.add(UnitType.UNIT_WIZARD);
        unitList.add(UnitType.UNIT_WIZARD);

        UnitInitializerFacade unitInitializer = new UnitInitializerFacade();

        unitInitializer.initializeUnits(unitList, true);
        unitInitializer.initializeUnits(unitList, false);

        GAME_BOARD.game();
    }
}
