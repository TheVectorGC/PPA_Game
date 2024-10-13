package Unit.Decorator;

import Unit.Melee;

public class MeleeDecorator {
    private final Melee melee;

    public MeleeDecorator(Melee melee) {
        this.melee = melee;
    }
    public void act(StringBuilder logBuilder) {
        melee.act(logBuilder);
        // Новая функциональность
    }
}
