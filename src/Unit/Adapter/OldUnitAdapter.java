package Unit.Adapter;

import Unit.Unit;
public class OldUnitAdapter extends Unit {
    private final OldUnit oldUnit;

    public OldUnitAdapter(OldUnit oldUnit) {
        this.oldUnit = oldUnit;
    }

    @Override
    public void performAction(StringBuilder logBuilder) {
        oldUnit.attackOld();
        logBuilder.append("Старый юнит атакует через адаптер.\n");
    }
}
