package UI;

import Unit.UnitType;
import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

public class UnitImageProviderProxy implements UnitImageProvider {
    private final UnitImageProvider realProvider;
    private final Map<String, Image> imageCache;

    public UnitImageProviderProxy(String imageBasePath) {
        this.realProvider = new UnitImageProviderBaseImpl(imageBasePath);
        this.imageCache = new HashMap<>();
    }

    @Override
    public Image getBaseImage(UnitType unitType) {
        String cacheKey = unitType.name() + "_base";
        return getImageFromCache(cacheKey, () -> realProvider.getBaseImage(unitType));
    }

    @Override
    public Image getPositionImage(UnitType unitType, int position) {
        String cacheKey = unitType.name() + "_pos_" + position;
        return getImageFromCache(cacheKey, () -> realProvider.getPositionImage(unitType, position));
    }

    private Image getImageFromCache(String cacheKey, ImageLoader loader) {
        if (imageCache.containsKey(cacheKey)) {
            return imageCache.get(cacheKey);
        }

        System.out.println("Proxy: ЗАГРУЗКА С ДИСКА - " + cacheKey);
        Image image = loader.load();

        if (image != null) {
            imageCache.put(cacheKey, image);
            System.out.println("Proxy: ИЗОБРАЖЕНИЕ ДОБАВЛЕНО В КЭШ - " + cacheKey);
        }

        return image;
    }

    @FunctionalInterface
    private interface ImageLoader {
        Image load();
    }
}