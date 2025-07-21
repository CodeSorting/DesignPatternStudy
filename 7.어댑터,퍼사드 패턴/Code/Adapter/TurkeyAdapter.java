package Adapter;

public class TurkeyAdapter implements Duck {
    Turkey turkey; //적응시킬 형식의 인터페이스를 구현하고 adaptee 클래스를 구성한다.

    public TurkeyAdapter(Turkey turkey) {
        this.turkey = turkey;
    }

    public void quack() {
        turkey.gobble();
    }

    public void fly() {
        for (int i = 0; i < 5; i++) {
            turkey.fly();
        }
    }
}