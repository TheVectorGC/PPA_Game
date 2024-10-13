package Game.Mediator.Colleague;

import Game.Mediator.Mediator;

public class WizardColleague extends UnitColleague {
    public WizardColleague(Mediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void receiveEvent(String event) {
        System.out.println("Wizard юнит " + name + " получил сообщение: " + event);
    }
}
