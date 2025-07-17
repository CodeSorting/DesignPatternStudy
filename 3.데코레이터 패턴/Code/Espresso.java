package Code;

public class Espresso extends Beverage {
    public Espresso() {
        setDescription("에스프레소");
    }

    @Override
    public double cost() {
        return 1.99;
    }
    
}
