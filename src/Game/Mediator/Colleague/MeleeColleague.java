package Game.Mediator.Colleague;

import Game.Mediator.Mediator;

public class MeleeColleague extends UnitColleague {
    public MeleeColleague(Mediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void receiveEvent(String event) {
        System.out.println("Melee юнит " + name + " получил сообщение: " + event);
    }
}
