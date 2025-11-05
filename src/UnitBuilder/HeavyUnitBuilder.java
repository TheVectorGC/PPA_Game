package UnitBuilder;

import Game.UnitListener;
import Unit.Heavy;
import Unit.Unit;

import java.util.List;

public class HeavyUnitBuilder implements UnitBuilder {
    private final Heavy heavy;
    public HeavyUnitBuilder() {
        heavy = new Heavy();
    }

    @Override
    public UnitBuilder setIsEnemy(boolean isEnemy) {
        heavy.setEnemy(isEnemy);
        return this;
    }

    @Override
    public UnitBuilder setName(String name) {
        heavy.setName(name);
        return this;
    }

    @Override
    public UnitBuilder setHealthPoints(int healthPoints) {
        heavy.setHealthPoints(healthPoints);
        return this;
    }

    @Override
    public UnitBuilder setDefence(int defence) {
        heavy.setDefence(defence);
        return this;
    }

    @Override
    public UnitBuilder setEvasion(int evasion) {
        heavy.setEvasion(evasion);
        return this;
    }

    @Override
    public UnitBuilder setCriticalChance(int criticalChance) {
        heavy.setCriticalChance(criticalChance);
        return this;
    }

    @Override
    public UnitBuilder setIsStunned(boolean isStunned) {
        heavy.setStunned(isStunned);
        return this;
    }

    @Override
    public UnitBuilder setPosition(int position) {
        heavy.setPosition(position);
        return this;
    }

    @Override
    public UnitBuilder setBleed(List<Integer> bleed) {
        heavy.setBleed(bleed);
        return this;
    }

    @Override
    public Unit build() {
        UnitListener.addListeners(heavy);
        return heavy;
    }
}
