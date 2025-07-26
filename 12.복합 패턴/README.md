## Chapter 12

### 패턴 섞어 쓰기
여러 패턴으로 다양한 디자인 문제를 해결하는 방법을 복합 패턴이라고 한다.

패턴을 무작정 결합하는게 아니라 여러가지 문제의 일반적인 해결법을 제시할 수 있어야 한다.

MVC(Model,View,Controller)을 알아보자. (MVC,MVP,MVVM 이 3개를 앱 개발 할 때 자주 썼었다.)

### 오리 예시
1단원부터 꾸준히 써왔던 오리 시뮬레이션 게임에 다양한 패턴을 적용해보자.

일단 Quackable 인터페이스를 만든다.
```java
public interface Quackable {
    public void quack();
}
```
그리고 이를 구현한 여러 오리 객체들을 만든다.
```java
public class MallardDuck implements Quackable {
    public void quack() {
        System.out.println("꽥꽥");
    }
}
public class RedheadDuck implements Quackable {
    public void quack() {
        System.out.println("꽥!");
    }
}
public class DuckCall implements Quackable {
    public void quack() {
        System.out.println("꽉!");
    }
}
public class RubberDuck implements Quackable {
    public void quack() {
        System.out.println("삑삑!");
    }
}
```

그리고 main에서 시뮬레이터를 만들면 된다.
```java
DuckSimulator simulator = new DuckSimulator();
simulator.simulate();


void simulate() {
    Quackable mallardDuck = new MallardDuck();
    Quackable redheadDuck = new RedheadDuck();
    Quackable duckCall = new DuckCall();
    Quackable rubberDuck = new RubberDuck();
    simulate(mallardDuck);
    simulate(redheadDuck);
    simulate(duckCall);
    simulate(rubberDuck);
}
void simulate(Quackable duck) {
    duck.quack();
}
```

### 어댑터 패턴 추가
여기서 이제 다르게 우는 오리가 아닌 거위 객체가 생겼다고 해보자. 이를 이어주기 위해서 어댑터 패턴을 써야 한다.

```java
//Goose
public class Goose {
    public void honk() {
        System.out.println("끽끽");
    }
}
//GooseAdapter
public class GooseAdapter implements Quackable {
    Goose goose;
    public GooseAdapter(Goose goose) {
        this.goose = goose;
    }
    public void quack() {
        goose.honk();
    }
}
//main
Quackable gooseDuck =  new GooseAdapter(new Goose());
simulate(gooseDuck);
```


### 데코레이터 패턴 추가
꽥꽥 소리를 낸 횟수를 세 주는 기능을 추가한다고 해보자.
```java
public class QuackCounter implements Quackable {
    Quackable duck;
    static int numberOfQuacks;
    public QuackCounter(Quackable duck) {
        this.duck = duck;
    }
    public void quack() {
        duck.quack();
        numberOfQuacks++;
    }
    public static int getQuacks() {
        return numberOfQuacks;
    }
}

//DuckSimulator(main)
void simulate() {
    Quackable mallardDuck = new QuackCounter(new MallardDuck());
    //.. 기타 나머지 초기화
    simulate(mallardDuck);
    //.. 기타 나머지 시뮬레이트
    System.out.println(QuackCounter.getQuacks());
}
```

-> 데코레이터를 쓸 때는 객체를 제대로 포장하지 않으면 못 쓴다. 따라서 생성하는 작업을 한 군데 몰아서 하는게 좋다.

### 팩토리 패턴 추가
객체 생성을 팩토리 패턴으로 해보자.

```java
//팩토리
public abstract class AbstractDuckFactory {
    public abstract Quackable createMallardDuck();
    public abstract Quackable createRedheadDuck();
    public abstract Quackable createDuckCall();
    public abstract Quackable createRubberDuck();
}
//오리 생성 공장
public class CountingDuckFactory extends AbstractDuckFactory{
    @Override
    public Quackable createMallardDuck() {
        return new QuackCounter(new MallardDuck());
    }
    @Override
    public Quackable createRedheadDuck() {
        return new QuackCounter(new RedheadDuck());
    }
    @Override
    public Quackable createDuckCall() {
        return new QuackCounter(new DuckCall());
    }
    @Override
    public Quackable createRubberDuck() {
        return new QuackCounter(new RubberDuck());
    }
}

//main
void simulate(AbstractDuckFactory duckFactory) {
    Quackable mallardDuck = duckFactory.createMallardDuck();
    Quackable redheadDuck = duckFactory.createRedheadDuck();
    Quackable duckCall = duckFactory.createDuckCall();
    Quackable rubberDuck = duckFactory.createRubberDuck();
    Quackable gooseDuck = new GooseAdapter(new Goose());
}
```

- 위의 여러 오리 무리들을 시뮬레이터에서 하나하나 관리하는 것은 좋은 코드가 아니다.
- 객체들로 구성된 컬렉션을 개별 객체와 같은 방식으로 다룰 수 있게 해주는 컴포지트 패턴을 사용해 오리들을 모두 관리하는 클래스를 만들어보자.
- 또, 이 컬렉션에 반복자 패턴을 적용해보자.

### 컴포지트, 반복자 패턴 추가
```java
//Composite
public class Flock implements Quackable{
    List<Quackable> quackers = new ArrayList<>();

    public void add(Quackable quacker) {
        quackers.add(quacker);
    }

    @Override
    public void quack() {
        // 반복자 패턴
        Iterator<Quackable> iterator = quackers.iterator();
        while (iterator.hasNext()) {
            Quackable quacker = iterator.next();
            quacker.quack();
        }
    }
}
//Simulator
public class DuckSimulator {
	public static void main(String[] args) {
		DuckSimulator simulator = new DuckSimulator();
		AbstractDuckFactory duckFactory = new CountingDuckFactory();
		simulator.simulate(duckFactory);
	}

	void simulate(AbstractDuckFactory duckFactory) {
		Quackable mallardDuck = duckFactory.createMallardDuck();
		Quackable redheadDuck = duckFactory.createRedheadDuck();
		Quackable duckCall = duckFactory.createDuckCall();
        Quackable rubberDuck = duckFactory.createRubberDuck();
        Quackable goose = new GooseAdapter(new Goose());
		System.out.println("\n오리 시뮬레이션 게임");

		Flock flockOfDucks = new Flock();

		flockOfDucks.add(mallardDuck);
		flockOfDucks.add(redheadDuck);
		flockOfDucks.add(duckCall);
		flockOfDucks.add(rubberDuck);
        flockOfDucks.add(goose);

		simulate(flockOfDucks);

		System.out.println(QuackCounter.getNumberOfQuacks());
	}

	// 다형성이 활용됨.
	void simulate(Quackable duck) {
		duck.quack();
	}
}
```

-> 우는 횟수 말고도 개별 오리의 행동을 관찰하고 싶다고 한다. 옵저버 패턴을 추가해보자.

### 옵저버 패턴 추가
오리가 울 때 알림을 받는 오리학자 클래스를 만들기 위해 옵저버 패턴을 사용해보자.

옵저버 패턴을 만들 때, **Observable 보조 클래스를 만들어서 실제 QuackObservable(실제 오리 객체들)에 구성으로 포함하여 등록 옵저버에 등록 및 연락하는 기능을 캡슐화**해보자.

즉, 실제 등록 및 연락 코드는 Observable에 포함되고, QuackObservable이 필요한 작업을 Observable 보조 클래스에 전부 위임하게 만들 수 있다.

```java
//QuackObservable
public interface QuackObservable {
    void registerObserver(Observer observer);
    void notifyObservers();
}

//등록,통지 함수 상속
public interface Quackable extends QuackObservable{
    public void quack();
}

//구현체
public class MallardDuck implements Quackable{

    Observable observable;

    public MallardDuck() {
        this.observable = new Observable(this);
    }

    @Override
    public void quack() {
        System.out.println("꽥꽥");
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observable.registerObserver(observer);
    }

    @Override
    public void notifyObservers() {
        observable.notifyObservers();
    }
}

//Observable
public class Observable implements QuackObservable{
    List<Observer> observers = new ArrayList<>();
    QuackObservable duck;

    public Observable(QuackObservable duck) {
        this.duck = duck;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(duck);
        }
    }
}
```
Observable은 여러 옵저버를 가질 수 있으므로 Observer 컬렉션으로 구성하고 있고, 구성하고 있는 옵저버들에 연락을 돌리는 코드를 실질적으로 맡고 있다.

```java
public interface Observer {
    void update(QuackObservable duck);
}

public class Quackologist implements Observer{
    @Override
    public void update(QuackObservable duck) {
        System.out.println("꽥꽥 학자: "+ duck + "이 방금 소리를 쳤다.");
    }
}

//Composite 패턴
public class Flock implements Quackable{
    List<Quackable> quackers = new ArrayList<>();

    public void add(Quackable quacker) {
        quackers.add(quacker);
    }

    @Override
    public void quack() {
        // 반복자 패턴
        Iterator<Quackable> iterator = quackers.iterator();
        while (iterator.hasNext()) {
            Quackable quacker = iterator.next();
            quacker.quack();
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        for (Quackable duck : quackers) {
            duck.registerObserver(observer);
        }
    }

    @Override
    public void notifyObservers() {
        
    }
}

//main
public class DuckSimulator {
	public static void main(String[] args) {
		DuckSimulator simulator = new DuckSimulator();
		AbstractDuckFactory duckFactory = new CountingDuckFactory();
		simulator.simulate(duckFactory);
	}

	void simulate(AbstractDuckFactory duckFactory) {
		//Quackable mallardDuck = duckFactory.createMallardDuck();
		//Quackable redheadDuck = duckFactory.createRedheadDuck();
		//Quackable goose = new GooseAdapter(new Goose());
		//System.out.println("\n오리 시뮬레이션 게임");

		//Flock flockOfDucks = new Flock();

		//flockOfDucks.add(mallardDuck);
		//flockOfDucks.add(redheadDuck);
		//flockOfDucks.add(goose);

		Quackologist quackologist = new Quackologist();
		flockOfDucks.registerObserver(quackologist);

		//simulate(flockOfDucks);

		//System.out.println(QuackCounter.getNumberOfQuacks());
	}

	// 다형성이 활용됨.
	void simulate(Quackable duck) {
		duck.quack();
	}
}
```

### MVC 패턴
이미 동아리에서 MVC,MVP,MVVM을 공부해서 이 부분은 패스한다.

차이점은 복습하는게 좋겠다.

MVC는 Controller에서 입력을 받고 나머지는 View에 입력이 간다.
또, 모델과 뷰의 상호작용이 가장 많아서 단점이 있는 것이 MVC이다.

kotlin에서는 MVVM을 기본적으로 쉽게 구현이 가능하다.

MVC에는 전략 패턴, 컴포지트 패턴, 옵저버 패턴이 쓰인다.

1. 옵저버 패턴
모델은 옵저버 패턴을 써서 상태가 변경되었을 때 그 모델과 연관된 객체들에게 연락한다.

이를 통해 모델을 뷰와 컨트롤러로부터 완전히 독립시킬 수 있다.

2. 전략 패턴
뷰와 컨트롤러는 고전적인 전략 패턴으로 구현되어있다.

뷰는 애플리케이션의 겉모습에만 신경을 쓰고, 인터페이스의 행동을 결정하는 일은 모두 컨트롤러에 캡슐화한다.

또, 사용자가 요청한 내역을 처리하려고 모델과 얘기하는 일을 컨트롤러가 맡게되어 모델과 뷰를 분리할 수 있다.

3. 컴포지트 패턴
디스플레이는 여러 단계로 겹쳐있는 윈도우, 패널, 버튼 등으로 구성되어있다.

컨트롤러가 뷰에게 화면을 갱신해 달라고 요청하면 최상위 뷰 구성 요소에게만 화면을 갱신하라고 얘기하면 된다.
