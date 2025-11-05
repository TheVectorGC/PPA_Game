package UI;

import Exception.UnitPositionException;
import javafx.scene.image.Image;

public record UnitViewModel(
        int position,
        String name,
        int hp,
        Image image,
        boolean isEnemy,
        boolean isActive
) {
    public UnitViewModel {
        UnitPositionException.validatePosition(position);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UnitViewModel unitViewModel
                && unitViewModel.isEnemy == isEnemy
                && unitViewModel.position == position;
    }
}
