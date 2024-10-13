package CustomIterator;

import Unit.UnitType;
import java.util.List;

public class UnitTypeIterator implements CustomIterator<UnitType> {
    private final List<UnitType> unitTypes;
    private int position = 0;

    public UnitTypeIterator(List<UnitType> unitTypes) {
        this.unitTypes = unitTypes;
    }

    @Override
    public boolean hasNext() {
        return position < unitTypes.size();
    }

    @Override
    public UnitType next() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("No more elements.");
        }
        return unitTypes.get(position++);
    }
}
