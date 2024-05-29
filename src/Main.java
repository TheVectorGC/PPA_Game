import Game.GameBoard;
import Interfaces.AbstractUnitFactory;
import Interfaces.UnitBuilder;
import Units.Unit;
import UnitsBuilders.MeleeUnitBuilder;
import UnitsFactories.HeavyUnitFactory;
import UnitsFactories.MeleeUnitFactory;
import UnitsFactories.RangeUnitFactory;
import UnitsFactories.WizardUnitFactory;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main extends Application {
    private static final GameBoard instance = GameBoard.getInstance();

    @Override
    public void start(Stage primaryStage) {
        // Загрузка изображения
        Image image = new Image("file:D:/Учёба/3 Курс/ППА/ИГРУШКА/ЧУВАК.png");
        ImageView imageView = new ImageView(image);
        imageView.setX(100);
        imageView.setY(100);

        Group root = new Group(imageView);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Simple Image Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        startGame();
        launch(args);
    }

    private static void startGame() {
        AbstractUnitFactory meleeFactory = new MeleeUnitFactory();
        AbstractUnitFactory rangeFactory = new RangeUnitFactory();
        AbstractUnitFactory heavyFactory = new HeavyUnitFactory();
        AbstractUnitFactory wizardFactory = new WizardUnitFactory();
        Unit melee = meleeFactory.createUnit(false);
        Unit range = rangeFactory.createUnit(false);
        Unit heavy = heavyFactory.createUnit(false);
        Unit wizard = wizardFactory.createUnit(false);
        Unit meleeEnemy = meleeFactory.createUnit(true);
        Unit rangeEnemy = rangeFactory.createUnit(true);
        Unit heavyEnemy = heavyFactory.createUnit(true);
        Unit wizardEnemy = wizardFactory.createUnit(true);

        instance.game();
    }
}
