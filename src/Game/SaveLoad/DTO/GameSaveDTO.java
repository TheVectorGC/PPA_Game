package Game.SaveLoad.DTO;

import java.io.Serializable;
import java.util.List;

public record GameSaveDTO(
        List<UnitDTO> yourUnits,
        List<UnitDTO> enemyUnits,
        int yourUnitIndex,
        int enemyUnitIndex,
        boolean isYourUnitTurn,
        int turnCounter,
        int expectedStateHash
) implements Serializable {}
