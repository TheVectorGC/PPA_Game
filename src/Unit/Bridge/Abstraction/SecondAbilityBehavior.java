package Unit.Bridge.Abstraction;

public class SecondAbilityBehavior implements UnitBehavior {

    @Override
    public void act() {
        System.out.println("Юнит использует вторую способность");
    }
}
