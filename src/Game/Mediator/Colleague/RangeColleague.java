package Game.Mediator.Colleague;

import Game.Mediator.Mediator;

public class RangeColleague extends UnitColleague {
    public RangeColleague(Mediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void receiveEvent(String event) {
        System.out.println("Range юнит " + name + " получил сообщение: " + event);
    }
}
