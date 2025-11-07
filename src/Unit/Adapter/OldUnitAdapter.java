package Unit.Adapter;

import Unit.Unit;
import Unit.UnitType;

public class OldUnitAdapter extends Unit {
    private final OldUnit oldUnit;

    public OldUnitAdapter(OldUnit oldUnit) {
        this.oldUnit = oldUnit;
    }

    @Override
    public void performAction() {
        oldUnit.attackOld();
        System.out.println(("Старый юнит атакует через адаптер.\n"));;
    }

    @Override
    public UnitType getUnitType() {
        return null;
    }
}
