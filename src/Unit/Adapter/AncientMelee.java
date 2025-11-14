package Unit.Adapter;

import Game.GameLogger;

public class AncientMelee {
    private static final int damage = 4;

    public void simpleAttack() {
        GameLogger.addLogEntry("Древний мечник атакует! Наносит " + damage + " урона.");
    }

    public static int getDamage() {
        return damage;
    }

    public static int getHealth() {
        return 5;
    }
}