package Code;

public class Mocha extends CondimentDecorator {
    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }
    public String getDescription() {
        return beverage.getDescription() + ", 모카";
    }
    public double cost() {
        double cost = beverage.cost();
        if (beverage.getSize() == Size.TALL) {
            cost += 0.20;
        } else if (beverage.getSize() == Size.GRANDE) {
            cost += 0.25;
        } else if (beverage.getSize() == Size.VENTI) {
            cost += 0.30;
        }
        return cost;
    }
}
