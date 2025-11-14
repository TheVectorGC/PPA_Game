package UI;

import Game.Facade.UnitInitializerFacade;
import Game.GameBoard;
import Game.GameLogger;
import Game.Strategy.AutoTurnStrategy;
import Game.Strategy.InstantResultStrategy;
import Unit.Unit;
import Unit.UnitType;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class GameUIController {

    private final GameBoard gameBoard = GameBoard.getInstance();
    private final UIDataProvider dataProvider = new UIDataProvider();

    private final ExecutorService turnExecutor = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "Game-Turn-Thread");
        t.setDaemon(true);
        return t;
    });

    private boolean autoPlaying = false;

    private AutoTurnStrategy autoStrategy;
    private Future<?> autoFuture;
    private Consumer<UnitViewModel> onActiveUnitChanged;
    private Runnable onArmyUpdated;
    private Consumer<Boolean> onGameOver;

    private volatile UnitViewModel lastActiveVm;

    public GameUIController() {
        registerGameListeners();
    }

    public void initializeDefaultArmy() {
        List<UnitType> unitList = new ArrayList<>();
        unitList.add(UnitType.UNIT_MELEE);
        unitList.add(UnitType.UNIT_WIZARD);
        unitList.add(UnitType.UNIT_HEAVY);
        unitList.add(UnitType.UNIT_RANGE);
        unitList.add(UnitType.UNIT_RANGE);

        UnitInitializerFacade unitInitializerFacade = new UnitInitializerFacade();
        unitInitializerFacade.initializeUnits(unitList, true);
        unitInitializerFacade.initializeUnits(unitList, false);

        gameBoard.setSquadPositions(true);
        gameBoard.setSquadPositions(false);
        gameBoard.saveCurrentState();

        Platform.runLater(this::notifyArmyUpdate);
    }

    private void refreshActiveUnit() {
        var activeOpt = gameBoard.getActiveUnit();
        UnitViewModel newActive = activeOpt.map(dataProvider::getViewModelForPosition).orElse(null);
        lastActiveVm = newActive;

        if (onActiveUnitChanged != null) {
            Platform.runLater(() -> onActiveUnitChanged.accept(newActive));
        }
    }

    public void startGameOver() {
        stopAutoPlay();
        gameBoard.resetGameState();
    }

    private void registerGameListeners() {
        gameBoard.addTurnListener(new GameBoard.TurnListener() {
            @Override
            public void onBeforeAct(Unit actingUnit, boolean isYourTurn) {
                UnitViewModel vm = dataProvider.getViewModelForPosition(actingUnit);
                lastActiveVm = vm;

                Platform.runLater(() -> {
                    if (onActiveUnitChanged != null) {
                        onActiveUnitChanged.accept(vm);
                    }
                });
            }

            @Override
            public void onAfterAct(Unit actedUnit, boolean isYourTurn) {
                Platform.runLater(GameUIController.this::notifyArmyUpdate);
            }

            @Override
            public void onGameOver(boolean yourWon) {
                Platform.runLater(() -> {
                    GameLogger.addLogEntry("\n=== Игра окончена. Победил: " + (yourWon ? "Вы" : "Враг") + " ===\n");
                    if (onGameOver != null) onGameOver.accept(yourWon);
                    stopAutoPlay();
                });
            }
        });
    }

    public void performSingleTurn() {
        turnExecutor.submit(() -> {
            if (gameBoard.isGameOver()) return;

            // получаем текущего активного юнита ДО хода
            var active = gameBoard.getActiveUnit();
            if (active.isPresent()) {
                UnitViewModel vm = dataProvider.getViewModelForPosition(active.get());
                Platform.runLater(() -> {
                    if (onActiveUnitChanged != null) onActiveUnitChanged.accept(vm);
                    if (onArmyUpdated != null) onArmyUpdated.run();
                });
            }

            gameBoard.executeTurnWithSave();

            // после хода обновляем армию и активного юнита
            Platform.runLater(() -> {
                if (onArmyUpdated != null) onArmyUpdated.run();
                UnitViewModel newActive = dataProvider.getActiveUnit();
                if (onActiveUnitChanged != null) onActiveUnitChanged.accept(newActive);
            });
        });
    }

    public void undo() {
        turnExecutor.submit(() -> {
            gameBoard.undo();
            Platform.runLater(() -> {
                notifyArmyUpdate();
                refreshActiveUnit();
            });
        });
    }

    public void redo() {
        turnExecutor.submit(() -> {
            gameBoard.redo();
            Platform.runLater(() -> {
                notifyArmyUpdate();
                refreshActiveUnit();
            });
        });
    }

    public void saveGame(String name) {
        turnExecutor.submit(() -> gameBoard.saveGame(name));
    }

    public void loadGame(String saveName) {
        turnExecutor.submit(() -> {
            try {
                gameBoard.loadGame(saveName);
                gameBoard.saveCurrentState();

                Platform.runLater(() -> {
                    notifyArmyUpdate();
                    refreshActiveUnit();
                });
            } catch (Exception e) {
                GameLogger.addLogEntry("Ошибка при загрузке: " + e.getMessage());
            }
        });
    }

    public void performInstantResult() {
        turnExecutor.submit(() -> {
            new InstantResultStrategy().execute();
            Platform.runLater(this::notifyArmyUpdate);
        });
    }

    public void toggleAutoPlay(Runnable onAutoStarted, Runnable onAutoStopped) {
        if (autoPlaying) {
            stopAutoPlay();
            if (onAutoStopped != null) Platform.runLater(onAutoStopped);
            return;
        }

        autoStrategy = new AutoTurnStrategy();
        autoPlaying = true;
        if (onAutoStarted != null) Platform.runLater(onAutoStarted);

        autoFuture = turnExecutor.submit(() -> {
            try {
                autoStrategy.execute();
            } finally {
                autoPlaying = false;
                if (onAutoStopped != null) Platform.runLater(onAutoStopped);
            }
        });
    }

    private void stopAutoPlay() {
        autoPlaying = false;
        if (autoStrategy != null) {
            autoStrategy.stop();
        }
        if (autoFuture != null && !autoFuture.isDone()) {
            autoFuture.cancel(true);
        }
    }


    public void shutdown() {
        turnExecutor.shutdownNow();
    }

    public void setOnActiveUnitChanged(Consumer<UnitViewModel> listener) {
        this.onActiveUnitChanged = listener;
    }

    public void setOnArmyUpdated(Runnable listener) {
        this.onArmyUpdated = listener;
    }

    public void setOnGameOver(Consumer<Boolean> listener) {
        this.onGameOver = listener;
    }

    private void notifyArmyUpdate() {
        if (onArmyUpdated != null) {
            onArmyUpdated.run();
        }
    }

    public boolean isAutoPlaying() {
        return autoPlaying;
    }

    public UnitViewModel getLastActiveVm() {
        return lastActiveVm;
    }

    public UIDataProvider getDataProvider() {
        return dataProvider;
    }
}
