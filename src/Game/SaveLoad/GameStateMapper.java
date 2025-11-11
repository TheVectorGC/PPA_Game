package Game.SaveLoad;

import Game.GameBoard;
import Game.SaveLoad.DTO.GameSaveDTO;
import Game.SaveLoad.DTO.UnitDTO;
import Unit.Unit;
import UnitBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

public class GameStateMapper {
    public static GameSaveDTO toGameStateDTO() {
        GameBoard gameBoard = GameBoard.getInstance();
        List<UnitDTO> yourUnitsDTO = gameBoard.getYourUnits().stream()
                .map(GameStateMapper::unitToDTO)
                .collect(Collectors.toList());

        List<UnitDTO> enemyUnitsDTO = gameBoard.getEnemyUnits().stream()
                .map(GameStateMapper::unitToDTO)
                .collect(Collectors.toList());

        return new GameSaveDTO(
                yourUnitsDTO,
                enemyUnitsDTO,
                gameBoard.getYourUnitIndex(),
                gameBoard.getEnemyUnitIndex(),
                gameBoard.isYourUnitTurn(),
                gameBoard.getTurnCounter()
        );
    }

    public static void fromGameStateDTO(GameSaveDTO dto) {
        GameBoard gameBoard = GameBoard.getInstance();
        gameBoard.setYourUnits(
                dto.yourUnits().stream()
                .map(GameStateMapper::createUnitFromDTO)
                .collect(Collectors.toList())
        );
        gameBoard.setEnemyUnits(dto.enemyUnits().stream()
                .map(GameStateMapper::createUnitFromDTO)
                .collect(Collectors.toList())
        );

        gameBoard.setYourUnitIndex(dto.yourUnitIndex());
        gameBoard.setEnemyUnitIndex(dto.enemyUnitIndex());
        gameBoard.setYourUnitTurn(dto.isYourUnitTurn());
        gameBoard.setTurnCounter(dto.turnCounter());

        gameBoard.setSquadPositions(true);
        gameBoard.setSquadPositions(false);
    }

    private static UnitDTO unitToDTO(Unit unit) {
        return new UnitDTO(
                unit.getUnitType(),
                unit.isEnemy(),
                unit.getName(),
                unit.getHealthPoints(),
                unit.getDefence(),
                unit.getEvasion(),
                unit.getCriticalChance(),
                unit.isStunned(),
                unit.getPosition(),
                unit.getBleed()
        );
    }

    private static Unit createUnitFromDTO(UnitDTO dto) {
        UnitBuilder unitBuilder;

        switch (dto.unitType()) {
            case UNIT_MELEE -> unitBuilder = new MeleeUnitBuilder();
            case UNIT_RANGE -> unitBuilder = new RangeUnitBuilder();
            case UNIT_HEAVY -> unitBuilder = new HeavyUnitBuilder();
            case UNIT_WIZARD -> unitBuilder = new WizardUnitBuilder();
            default -> throw new IllegalArgumentException("Неизвестный тип юнита: " + dto.unitType());
        }

        unitBuilder
                .setIsEnemy(dto.isEnemy())
                .setName(dto.name())
                .setHealthPoints(dto.healthPoints())
                .setDefence(dto.defence())
                .setEvasion(dto.evasion())
                .setCriticalChance(dto.criticalChance())
                .setIsStunned(dto.isStunned())
                .setBleed(dto.bleed());

        if (dto.position() < 1) {
            return unitBuilder.build();
        } else {
            return unitBuilder.setPosition(dto.position()).build();
        }
    }
}
