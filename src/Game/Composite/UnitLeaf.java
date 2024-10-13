package Game.Composite;

public class UnitLeaf implements UnitComponent{
    private final String name;

    public UnitLeaf(String name) {
        this.name = name;
    }

    @Override
    public void action() {
        System.out.println(name + " выполняет действие.");
    }
}
