package Game.Composite;

import java.util.ArrayList;
import java.util.List;

public class UnitComposite implements UnitComponent {
    private final List<UnitComponent> units = new ArrayList<>();

    public void addUnit(UnitComponent unit) {
        units.add(unit);
    }

    public void removeUnit(UnitComponent unit) {
        units.remove(unit);
    }

    @Override
    public void action() {
        for (UnitComponent unit : units) {
            unit.action();
        }
    }
}
