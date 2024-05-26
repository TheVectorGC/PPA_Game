import Interfaces.AbstractUnitFactory;
import Interfaces.UnitBuilder;
import Units.Unit;
import UnitsBuilders.MeleeUnitBuilder;
import UnitsFactories.MeleeUnitFactory;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main extends Application {

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
        launch(args);
    }
}
