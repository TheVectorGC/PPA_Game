package Unit.Adapter;

import Unit.Unit;

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
}
