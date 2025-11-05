package Game.SaveLoad.DTO;

import Unit.UnitType;

import java.io.Serializable;
import java.util.List;

public record UnitDTO(
        UnitType unitType,
        boolean isEnemy,
        String name,
        int healthPoints,
        int defence,
        int evasion,
        int criticalChance,
        boolean isStunned,
        int position,
        List<Integer> bleed
) implements Serializable {}
