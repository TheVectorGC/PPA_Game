package UI;

import Game.GameBoard;
import Unit.Unit;
import Unit.UnitType;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UIDataProvider {

    private static final GameBoard gameBoard = GameBoard.getInstance();
    private final UnitImageProvider unitImageProvider;

    public UIDataProvider() {
        this.unitImageProvider = new UnitImageProviderProxy("resources/images/");
    }

    public Optional<Integer> getHiddenUnitsCount(boolean isEnemy) {
        final var diff = gameBoard.getArmySize(isEnemy) - 4;
        return diff > 0 ? Optional.of(diff) : Optional.empty();
    }

    public List<UnitViewModel> getYourArmy() {
        return buildArmyView(false);
    }

    public List<UnitViewModel> getEnemyArmy() {
        return buildArmyView(true);
    }

    public UnitViewModel getActiveUnit() {
        final var activeUnitOpt = gameBoard.getActiveUnit();
        if (activeUnitOpt.isEmpty()) {
            return null;
        }

        final Unit activeUnit = activeUnitOpt.get();
        final UnitType type = activeUnit.getUnitType();
        final int unitPosition = activeUnit.getPosition();
        final Image unitImage = unitImageProvider.getPositionImage(type, unitPosition);

        return new UnitViewModel(unitPosition, type.name(), activeUnit.getHealthPoints(), unitImage, activeUnit.isEnemy(), true);
    }

    public UnitViewModel getViewModelForPosition(Unit unit) {
        return new UnitViewModel(
                unit.getPosition(),
                unit.getName(),
                unit.getHealthPoints(),
                unitImageProvider.getPositionImage(unit.getUnitType(), unit.getPosition()),
                unit.isEnemy(),
                unit.equals(GameBoard.getInstance().getActiveUnit().orElse(null))
        );
    }

    private List<UnitViewModel> buildArmyView(boolean isEnemy) {
        List<UnitViewModel> armyView = new ArrayList<>();
        var activeUnitOpt = gameBoard.getActiveUnit();
        for (int i = 1; i <= 4; i++) {
            Unit unit = gameBoard.getUnit(i, isEnemy);
            if (unit == null) break;
            boolean isActive = unit.equals(activeUnitOpt.orElse(null));
                    Image img = unitImageProvider.getBaseImage(unit.getUnitType());
            armyView.add(new UnitViewModel(unit.getPosition(), unit.getUnitType().name(), unit.getHealthPoints(), img, isEnemy, isActive));
        }
        return armyView;
    }
}
