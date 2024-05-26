package Interfaces;
import Units.Unit;

public interface AbstractUnitFactory {
    Unit createUnit(boolean isEnemy);
}
