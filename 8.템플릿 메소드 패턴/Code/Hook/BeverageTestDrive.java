package Hook;

public class BeverageTestDrive {
    public static void main(String[] args) {
        CaffeineBeverageWithHook tea = new TeaWithHook();
        tea.prepareRecipe();

        CaffeineBeverageWithHook coffee = new CoffeeWithHook();
        coffee.prepareRecipe();
    }
}
