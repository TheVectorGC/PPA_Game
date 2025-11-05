package Unit.Proxy;

import Unit.Melee;
import Unit.Unit;

public class MeleeProxy extends Unit {
    private Melee melee;
    private final boolean isEnemy;
    private final String name;
    private boolean isInitialized = false;

    public MeleeProxy(boolean isEnemy, String name) {
        this.isEnemy = isEnemy;
        this.name = name;
    }

    private void initialize() {
        if (!isInitialized) {
            melee = new Melee(isEnemy, name);
            isInitialized = true;
        }
    }

    @Override
    public void performAction(StringBuilder logBuilder) {
        initialize();
        melee.performAction(logBuilder);
    }
}
