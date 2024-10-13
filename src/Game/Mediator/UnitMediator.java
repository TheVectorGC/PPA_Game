package Game.Mediator;

import Game.Mediator.Colleague.UnitColleague;
import java.util.ArrayList;
import java.util.List;

public class UnitMediator implements Mediator {

    private final List<UnitColleague> units = new ArrayList<>();

    public void addUnit(UnitColleague unit) {
        units.add(unit);
    }

    @Override
    public void notify(String sender, String event) {
        System.out.println(sender + " отправил сообщение: " + event);
        for (UnitColleague unit : units) {
            if (!unit.getName().equals(sender)) {
                unit.receiveEvent(event);
            }
        }
    }
}