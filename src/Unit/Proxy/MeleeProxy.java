package Unit.Proxy;

import Unit.Melee;
import Unit.Unit;

public class MeleeProxy extends Unit {
    private Melee melee;
    private final boolean isEnemy;
    private boolean isInitialized = false;

    public MeleeProxy(boolean isEnemy) {
        this.isEnemy = isEnemy;
    }

    private void initialize() {
        if (!isInitialized) {
            melee = new Melee(isEnemy);
            isInitialized = true;
        }
    }

    @Override
    public void performAction() {
        initialize();
        melee.performAction();
    }
}
