package UI;

import Unit.UnitType;
import Exception.UnitPositionException;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class UnitImageProviderBaseImpl implements UnitImageProvider {

    private final Map<UnitType, Map<String, Image>> imageCache = new HashMap<>();
    private final String imageBasePath;

    public UnitImageProviderBaseImpl(String imageBasePath) {
        this.imageBasePath = imageBasePath;
    }

    @Override
    public Image getBaseImage(UnitType unitType) {
        final String key = "base";
        return getImage(unitType, key);
    }

    @Override
    public Image getPositionImage(UnitType unitType, int position) {
        UnitPositionException.validatePosition(position);

        final String key = "pos_" + position;
        return getImage(unitType, key);
    }

    private Image getImage(UnitType unitType, String key) {
        Map<String, Image> subMap = imageCache.computeIfAbsent(unitType, t -> new HashMap<>());

        if (subMap.containsKey(key)) {
            return subMap.get(key);
        }

        Image img = loadImage(unitType, key);
        subMap.put(key, img);
        return img;
    }

    private Image loadImage(UnitType unitType, String key) {
        File file = new File(MessageFormat.format(imageBasePath + "/{0}/{1}.png", unitType.name(), key));

        try (FileInputStream fis = new FileInputStream(file)) {
            return new Image(fis);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Required image not found: " + file.getAbsolutePath(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error while reading image: " + file.getAbsolutePath(), e);
        }
    }
}
