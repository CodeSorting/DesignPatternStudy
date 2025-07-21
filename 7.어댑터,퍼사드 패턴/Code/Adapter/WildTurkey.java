package Adapter;

public class WildTurkey implements Turkey {
    public void gobble() {
        System.out.println("골골");
    }

    public void fly() {
        System.out.println("짧게 날고 있음.");
    }
}
