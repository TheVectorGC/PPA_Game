package UI;

import Unit.UnitType;
import javafx.scene.image.Image;

public interface UnitImageProvider {

    Image getBaseImage(UnitType unitType);

    Image getPositionImage(UnitType unitType, int position);

}
