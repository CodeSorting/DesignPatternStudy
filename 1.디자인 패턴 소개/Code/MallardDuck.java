package Code;

public class MallardDuck extends Duck {
    //Duck 클래스에서 quackBehavior와 flyBehavior를 상속받는다.
    public MallardDuck() {
        quackBehavior = new Quack();
        flyBehavior = new FlyWithWings();
    }
    @Override
    public void display() {
        System.out.println("저는 물오리입니다.");
    }
}