package Code;

public abstract class Duck {
    QuackBehavior quackBehavior;
    FlyBehavior flyBehavior;

    public Duck() {}
    public abstract void display();

    public void performQuack() {
        quackBehavior.quack();
    }
    
    public void performFly() {
        flyBehavior.fly();
    }
    public void swim() {
        System.out.println("모든 오리는 물에 뜹니다. 가짜도 뜹니다.");
    }
    
    public void setFlyBehavior(FlyBehavior fb) {
        flyBehavior = fb;
    }
    public void setQuackBehavior(QuackBehavior qb) {
        quackBehavior = qb;
    }
}