package Game.Mediator.Colleague;

import Game.Mediator.Mediator;

public abstract class UnitColleague {
    protected Mediator mediator;
    protected String name;
    public UnitColleague(Mediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void receiveEvent(String event);

    public void sendEvent(String event) {
        mediator.notify(name, event);
    }
}
