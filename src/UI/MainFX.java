package UI;

import Game.GameLogger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class MainFX extends Application {

    private final GameUIController controller = new GameUIController();
    private final StackPane[] ourSlots = new StackPane[4];
    private final StackPane[] enemySlots = new StackPane[4];
    private final ImageView activeUnitImage = new ImageView();
    private final Label yourHiddenUnitsLabel = new Label();
    private final Label enemyHiddenUnitsLabel = new Label();

    @Override
    public void start(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth() * 0.85;
        double height = screenBounds.getHeight() * 0.95;

        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        ToolBar toolbar = initializeToolbar(stage);
        root.setTop(toolbar);

        GridPane grid = createGrid();
        root.setCenter(grid);

        // Верхняя строка (активный юнит) 
        StackPane activePane = new StackPane(activeUnitImage);
        GridPane.setHalignment(activePane, HPos.CENTER);
        activeUnitImage.fitWidthProperty().bind(activePane.widthProperty());
        activeUnitImage.fitHeightProperty().bind(activePane.heightProperty());
        grid.add(activePane, 4, 0, 4, 1);

        // Средняя строка (армии) 
        addArmySlots(grid);

        // Нижняя строка (логи + кнопки) 
        addBottomPanel(grid);

        // Scene 
        Scene scene = new Scene(root, width, height, Color.WHITE);
        scene.widthProperty().addListener((obs, oldVal, newVal) -> updateArmyUI());
        scene.heightProperty().addListener((obs, oldVal, newVal) -> updateArmyUI());
        stage.setTitle("Battle Game");
        stage.setScene(scene);
        stage.show();

        controller.setOnActiveUnitChanged(active -> {
            if (active != null) {
                activeUnitImage.setImage(active.image());
                activeUnitImage.setScaleX(active.isEnemy() ? -1 : 1);
            }
            else {
                activeUnitImage.setImage(null);
            }
        });
        controller.setOnArmyUpdated(this::updateArmyUI);
    }

    private ToolBar initializeToolbar(Stage stage) {
        final Button saveBtn = new Button("💾 Сохранить");
        final Button loadBtn = new Button("📂 Загрузить");

        File savesDir = new File(System.getProperty("user.dir"), "saves");
        if (!savesDir.exists()) {
            savesDir.mkdirs();
        }

        saveBtn.setOnAction(l -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Сохранить игру как...");
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Файлы сохранений (*.save)", "*.save")
            );
            chooser.setInitialDirectory(savesDir);

            File file = chooser.showSaveDialog(stage);
            if (file != null) {
                String name = file.getName().replaceFirst("\\.save$", "");
                controller.saveGame(name);
            } else {
                controller.saveGame(null);
            }
        });

        loadBtn.setOnAction(l -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Выберите файл сохранения");
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Файлы сохранений (*.save)", "*.save")
            );
            chooser.setInitialDirectory(savesDir);

            File file = chooser.showOpenDialog(stage);
            if (file != null) {
                String name = file.getName().replaceFirst("\\.save$", "");
                controller.loadGame(name);
            } else {
                GameLogger.addLogEntry("Загрузка отменена");
            }
        });

        return new ToolBar(
                saveBtn,
                loadBtn
        );
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        for (int i = 0; i < 12; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0 / 12);
            grid.getColumnConstraints().add(col);
        }
        for (int i = 0; i < 3; i++) {
            RowConstraints row = new RowConstraints();
            if (i == 0) row.setPercentHeight(35);
            else if (i == 1) row.setPercentHeight(30);
            else row.setPercentHeight(35);
            grid.getRowConstraints().add(row);
        }
        return grid;
    }

    private void addArmySlots(GridPane grid) {
        yourHiddenUnitsLabel.setFont(Font.font("Consolas", 20));
        yourHiddenUnitsLabel.setTextFill(Color.web("#333"));
        yourHiddenUnitsLabel.setAlignment(Pos.CENTER_LEFT);
        GridPane.setHalignment(yourHiddenUnitsLabel, HPos.LEFT);
        grid.add(yourHiddenUnitsLabel, 0, 1);

        enemyHiddenUnitsLabel.setFont(Font.font("Consolas", 20));
        enemyHiddenUnitsLabel.setTextFill(Color.web("#333"));
        enemyHiddenUnitsLabel.setAlignment(Pos.CENTER_RIGHT);
        GridPane.setHalignment(enemyHiddenUnitsLabel, HPos.RIGHT);
        grid.add(enemyHiddenUnitsLabel, 11, 1);

        for (int i = 0; i < 4; i++) {
            StackPane pane = createArmySlot(false);
            grid.add(pane, 1 + i, 1);
            ourSlots[i] = pane;
        }

        for (int i = 0; i < 4; i++) {
            StackPane pane = createArmySlot(true);
            grid.add(pane, 7 + i, 1);
            enemySlots[i] = pane;
        }
    }

    private StackPane createArmySlot(boolean isEnemy) {
        StackPane pane = new StackPane();
        pane.setAlignment(Pos.CENTER);
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        VBox box = new VBox(3);
        box.setAlignment(Pos.TOP_CENTER);

        Label hpLabel = new Label();
        hpLabel.setFont(Font.font("Consolas", 18));
        hpLabel.setTextFill(Color.web("#333"));

        ImageView iv = new ImageView();
        iv.fitWidthProperty().bind(pane.widthProperty().multiply(0.85));
        iv.fitHeightProperty().bind(pane.heightProperty().multiply(0.85));

        if (isEnemy) {
            iv.setScaleX(-1);
        }

        box.getChildren().addAll(hpLabel, iv);
        pane.getChildren().add(box);
        return pane;
    }

    private void addBottomPanel(GridPane grid) {
        // логи
        ListView<String> logView = new ListView<>(GameLogger.getLogEntries());
        logView.setStyle("""
            -fx-font-family: Consolas;
            -fx-font-size: 13px;
            -fx-background-color: white;
            -fx-border-color: #ccc;
            -fx-border-radius: 8;
            -fx-padding: 6;
            """);
        GameLogger.getLogEntries().addListener(
                (ListChangeListener<? super String>) c ->
                        Platform.runLater(() -> logView.scrollTo(GameLogger.getLogEntries().size() - 1))
        );
        GridPane.setHalignment(logView, HPos.LEFT);
        GridPane.setValignment(logView, VPos.CENTER);
        grid.add(logView, 0, 2, 8, 1);

        // кнопки
        FlowPane buttonsPane = new FlowPane();
        buttonsPane.setHgap(10);
        buttonsPane.setVgap(8);
        buttonsPane.setAlignment(Pos.CENTER_RIGHT);
        buttonsPane.setPrefWrapLength(250);
        buttonsPane.setPadding(new Insets(5, 5, 5, 5));

        Button btnUndo = styledButton("↩ Undo");
        Button btnRedo = styledButton("↪ Redo");
        Button btnNext = styledButton("▶ Ход");
        Button btnAuto = styledButton("⏩ Авто");
        Button btnResult = styledButton("🏁 Результат");
        Button initializeDefaultArmyBtn = styledButton("Собрать дефолтную армию");
        Button startOverBtn = styledButton("Начать заново");

        // поведение
        btnUndo.setOnAction(e -> controller.undo());
        btnRedo.setOnAction(e -> controller.redo());
        btnNext.setOnAction(e -> controller.performSingleTurn());
        btnAuto.setOnAction(e -> controller.toggleAutoPlay(
                () -> btnAuto.setText("⏹ Стоп"),
                () -> btnAuto.setText("⏩ Авто")
        ));
        btnResult.setOnAction(e -> {
            controller.performInstantResult();
            btnAuto.setVisible(false);
        });
        initializeDefaultArmyBtn.setOnAction(e -> {
            controller.initializeDefaultArmy();
            updateArmyUI();
            initializeDefaultArmyBtn.setVisible(false);
        });
        startOverBtn.setOnAction(e -> {
            controller.startGameOver();
            initializeDefaultArmyBtn.setVisible(true);
            btnAuto.setVisible(true);
            updateArmyUI();
            activeUnitImage.setImage(null);
        });

        // чтобы невидимые кнопки не занимали место
        btnAuto.managedProperty().bind(btnAuto.visibleProperty());
        initializeDefaultArmyBtn.managedProperty().bind(initializeDefaultArmyBtn.visibleProperty());

        buttonsPane.getChildren().addAll(
                btnUndo, btnRedo, btnNext, btnAuto,
                btnResult, initializeDefaultArmyBtn, startOverBtn
        );

        // компоновка правого блока
        VBox rightControls = new VBox(buttonsPane);
        rightControls.setAlignment(Pos.CENTER_RIGHT);
        rightControls.setPadding(new Insets(0, 15, 0, 0));
        grid.add(rightControls, 8, 2, 4, 1);
    }

    private Button styledButton(String text) {
        Button btn = new Button(text);
        btn.setMinWidth(140);
        btn.setPadding(new Insets(8, 16, 8, 16));
        btn.setStyle("""
            -fx-background-color: #000;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-background-radius: 8;
            -fx-cursor: hand;
            """);
        btn.setOnMouseEntered(e -> btn.setStyle("""
            -fx-background-color: #222;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-background-radius: 8;
            -fx-cursor: hand;
            """));
        btn.setOnMouseExited(e -> btn.setStyle("""
            -fx-background-color: #000;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-background-radius: 8;
            -fx-cursor: hand;
            """));
        return btn;
    }

    private void updateArmyUI() {
        var provider = controller.getDataProvider();
        UnitViewModel activeSnap = controller.getLastActiveVm();
        boolean autoMode = controller.isAutoPlaying();

        provider.getHiddenUnitsCount(false).ifPresentOrElse(
                count -> yourHiddenUnitsLabel.setText("+" + count + " 🛡"),
                () -> yourHiddenUnitsLabel.setText("")
        );

        provider.getHiddenUnitsCount(true).ifPresentOrElse(
                count -> enemyHiddenUnitsLabel.setText("+" + count + " ⚔"),
                () -> enemyHiddenUnitsLabel.setText("")
        );

        List<UnitViewModel> yourArmy = provider.getYourArmy();
        for (int i = 0; i < ourSlots.length; i++) {
            StackPane slot = ourSlots[i];
            VBox box = (VBox) slot.getChildren().getFirst();
            Label hpLabel = (Label) box.getChildren().get(0);
            ImageView iv = (ImageView) box.getChildren().get(1);

            int finalI = i;
            UnitViewModel vm = yourArmy.stream()
                    .filter(u -> 4 - u.position() == finalI)
                    .findFirst()
                    .orElse(null);

            if (vm == null) {
                hpLabel.setText("");
                iv.setImage(null);
                slot.setBorder(null);
                slot.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                continue;
            }

            hpLabel.setText(vm.hp() + " ❤");
            iv.setImage(vm.image());
            iv.setScaleX(1);

            boolean isActiveHere = autoMode
                    ? (activeSnap != null && activeSnap.equals(vm))
                    : vm.isActive();

            slot.setBorder(isActiveHere
                    ? new Border(new BorderStroke(Color.web("#4285f4"),
                    BorderStrokeStyle.SOLID, new CornerRadii(6), new BorderWidths(5)))
                    : null);
            slot.setBackground(new Background(new BackgroundFill(
                    isActiveHere ? Color.web("#e6f0ff") : Color.WHITE,
                    new CornerRadii(6), Insets.EMPTY
            )));
        }

        List<UnitViewModel> enemyArmy = provider.getEnemyArmy();
        for (int i = 0; i < enemySlots.length; i++) {
            StackPane slot = enemySlots[i];
            VBox box = (VBox) slot.getChildren().getFirst();
            Label hpLabel = (Label) box.getChildren().get(0);
            ImageView iv = (ImageView) box.getChildren().get(1);

            int finalI = i;
            UnitViewModel vm = enemyArmy.stream()
                    .filter(u -> u.position() - 1 == finalI)
                    .findFirst()
                    .orElse(null);

            if (vm == null) {
                hpLabel.setText("");
                iv.setImage(null);
                slot.setBorder(null);
                slot.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                continue;
            }

            hpLabel.setText(vm.hp() + " ❤");
            iv.setImage(vm.image());
            iv.setScaleX(-1);

            boolean isActiveHere = autoMode
                    ? (activeSnap != null && activeSnap.equals(vm))
                    : vm.isActive();

            slot.setBorder(isActiveHere
                    ? new Border(new BorderStroke(Color.web("#ff5555"),
                    BorderStrokeStyle.SOLID, new CornerRadii(6), new BorderWidths(5)))
                    : null);
            slot.setBackground(new Background(new BackgroundFill(
                    isActiveHere ? Color.web("#ffecec") : Color.WHITE,
                    new CornerRadii(6), Insets.EMPTY
            )));
        }
    }


    @Override
    public void stop() {
        controller.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
