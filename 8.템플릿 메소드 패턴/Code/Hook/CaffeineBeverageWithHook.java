package Hook;

public abstract class CaffeineBeverageWithHook {
    final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        if (customerWantsCondiments()) {
            addCondiments();
        }
    }
    abstract void brew();
    abstract void addCondiments();
    public void boilWater() {
        System.out.println("물 끓이기");
    }
    public void pourInCup() {
        System.out.println("컵에 따르기");
    }
    boolean customerWantsCondiments() {
        return true; // 기본 구현은 condiments를 추가하도록 설정
    }
}
