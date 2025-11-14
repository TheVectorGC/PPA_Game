package Unit.Adapter;

import Game.GameBoard;
import Unit.Unit;
import Unit.UnitType;

public class AncientMeleeAdapter extends Unit {
    private final AncientMelee ancientMelee;

    public AncientMeleeAdapter(boolean isEnemy) {
        super(isEnemy,
                isEnemy ? "ДРЕВНИЙ БЛИЖНИК-ВРАГ" : "ДРЕВНИЙ МЕЧНИК",
                AncientMelee.getHealth(),
                0,   // defence (фиксированно)
                0,   // evasion (фиксированно)
                0);  // criticalChance (фиксированно)
        this.ancientMelee = new AncientMelee();
    }

    @Override
    public void performAction() {
        ancientMelee.simpleAttack();

        Unit enemy = GameBoard.getInstance().getUnit(1, !isEnemy());
        if (enemy != null) {
            enemy.setHealthPoints(enemy.getHealthPoints() - AncientMelee.getDamage());
        }
    }

    @Override
    public UnitType getUnitType() {
        return UnitType.UNIT_MELEE;
    }

    @Override
    public int getDefence() { return 0; }
    @Override
    public int getEvasion() { return 0; }
    @Override
    public int getCriticalChance() { return 0; }
}
