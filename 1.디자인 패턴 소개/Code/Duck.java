package Code;

public class Duck {
    private String name;

    public Duck(String name) {
        this.name = name;
    }

    public void quack() {
        System.out.println(name + " says Quack!");
    }

    public void swim() {
        System.out.println(name + " is swimming!");
    }

    public static void main(String[] args) {
        Duck duck = new Duck("Daffy");
        duck.quack();
        duck.swim();
    }
}