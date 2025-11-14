package Unit;

import Exception.UnitPositionException;
import Game.GameLogger;
import Unit.Decorator.MeleeEnhancedThrowDecorator;

public class DecoratedMelee extends Melee {
    private MeleeEnhancedThrowDecorator decorator;

    public DecoratedMelee(boolean isEnemy) {
        super(isEnemy);
        this.decorator = new MeleeEnhancedThrowDecorator(this);

        if (!isEnemy) {
            setName("УСИЛЕННЫЙ БЛИЖНИК");
        }
        else {
            setName("УСИЛЕННЫЙ БЛИЖНИК-ВРАГ");
        }
    }

    @Override
    public void performAction() {
        switch (getPosition()) {
            case 1 -> {
                GameLogger.addLogEntry(String.format("%s (%d): ПОДЛЫЙ УДАР (DMG 4-5 +bleed 2(3))\n", this.getName(), this.getPosition()));
                sneakyBlow();
            }
            case 2 -> {
                // Декорированный метод
                GameLogger.addLogEntry(String.format("%s (%d): УСИЛЕННЫЙ БРОСОК (DMG 6-7)\n", this.getName(), this.getPosition()));
                decorator.enhancedSneakyThrow();
            }
            case 3 -> {
                GameLogger.addLogEntry(String.format("%s (%d): ЗАТОЧКА НОЖЕЙ (+CRT (10%%))\n", this.getName(), this.getPosition()));
                knifeSharpening();
            }
            case 4 -> {
                GameLogger.addLogEntry(String.format("%s (%d): КОВЫРЯНИЕ В НОСУ (nothing)\n", this.getName(), this.getPosition()));
                nosePicking();
            }
            default -> throw new UnitPositionException("Unit is not in one of the four positions");
        }
    }

    @Override
    public Unit clone() {
        DecoratedMelee cloned = (DecoratedMelee) super.clone();
        cloned.decorator = new MeleeEnhancedThrowDecorator(cloned);
        return cloned;
    }
}