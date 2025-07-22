package Template;
//package Code;

public abstract class CaffeineBeverage {
    final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        addCondiments();
    }
    abstract void brew();
    abstract void addCondiments();
    public void boilWater() {
        System.out.println("물 끓이기");
    }
    public void pourInCup() {
        System.out.println("컵에 따르기");
    }
}
