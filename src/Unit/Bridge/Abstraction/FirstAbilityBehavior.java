package Unit.Bridge.Abstraction;

public class FirstAbilityBehavior implements UnitBehavior {
    @Override
    public void act() {
        System.out.println("Юнит использует первую способность");
    }
}
