package Game.Mediator.Colleague;

import Game.Mediator.Mediator;

public class HeavyColleague extends UnitColleague {
    public HeavyColleague(Mediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void receiveEvent(String event) {
        System.out.println("Heavy юнит " + name + " получил сообщение: " + event);
    }
}
