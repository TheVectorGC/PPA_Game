package UnitFactory;
import Unit.Unit;

public interface AbstractUnitFactory {
    Unit createUnit(boolean isEnemy);
}
