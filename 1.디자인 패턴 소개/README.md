## Chapter 1

## Duck 시뮬레이터 게임
조는 오리 시뮬레이션 게임을 만들고 있다.     
이 시스템을 처음 디자인한 사람은 Duck이라는 슈퍼 클래스를 만들고 그를 확장해 MallardDuck,RedheadDuck 등 상속받은 클래스를 만들었다.
Duck에는 quack(),swim(),display()가 있었고 이제 fly()를 추가할려고 한다.    
그리고 상속받은 Mallard,Redhead는 Override 하여 fly()를 구현하였다.      

-> 근데 여기서 문제점이 생겼다. 날면 안되는 오리마저도 fly()를 Override하여 날아다니기 시작하였다!    
-> 물론 fly()를 override 했을 때 아무것도 안하게 하면 된다.
-> 가짜오리를 추가하기 위해서는 quack()에도 아무 소리가 안나게 해야한다.     
-> 결국 이렇게 하면 코드가 더러워진다.

### 상속의 단점
1. 서브 클래스에서 코드가 중복
2. 실행 시 특징을 바꾸기 힘들다.
3. 모든 오리의 행동을 알기 어렵다.
4. 코드를 변경했을 때 다른 오리들에게 원치 않은 영향을 끼칠 수 있다.

### 인터페이스 설계하기
조는 이제 결국 상속이 옳은 방법이 아니라는 것을 깨달았고 특정 형식의 오리만 날거나 꽥꽥거릴 수 있도록 하는 방법을 찾기로 한다.<br>
flyable,Quackable이라는 인터페이스를 만들어서 이를 구현한 객체만 override한 함수를 구현하기로 한다.<br>
-> 그러나 날아가는 동작을 조금 바꾸기 위해 Duck의 서브 클래스에서 날아다닐 수 있는 48개의 코드를 전부 고쳐야하는 건 말이 안된다.<br>
-> 또, 날 수 있는 오리 중에서도 날아다니는 방식이 서로 다를 수 있다.

#### 소프트웨어 개발 불변의 진리
모든 애플리케이션은 시간이 지남에 따라 항상 변화하고 성장해야한다.<br>
그렇지 않으면 그 애플리케이션은 죽는다.<br>

### 문제 파악하기
Flyable,Quackable 인터페이스를 사용하는 방법(날 수 있는 오리들만 Flyable을 구현하기)은 괜찮아 보였다.<br>
하지만 자바 인터페이스에는 구현된 코드가 없으므로 코드를 재사용할 수 없다.<br>
즉, 한 가지 행동을 바꿀 때마다 그 행동이 정의되어 있는 서로 다른 서브 클래스를 전부 찾아서 코드를 일일이 고쳐야 하고, 그 과정에서 버그가 생길 수 있다.
```
이 인터페이스를 구현하는 쪽(각 Duck 서브클래스)에서 직접 fly() 로직을 적어야 한다.
만약 ‘날개로 난다’라는 구현이 48개 오리 중 44종에 들어 있다면,
44개의 파일이 모두 똑같은 fly() 코드(혹은 거의 비슷한 코드)를 갖게 된다.
```
-> 바뀌는 부분은 따로 뽑아서 캡슐화하면 나중에 바뀌지 않는 부분에는 영향을 미치지 않고 그 부분만 고치거나 확장할 수 있다.

**즉, 달라지는 부분을 찾아내고, 달라지지 않는 부분과 분리한다.**

### 바뀌는 부분과 아닌 부분 분리하기
바뀌는 부분은 fly,quack이니 변화하는 부분을 2개의 클래스 집합으로 만든다. 1개는 나는 것과 관련된 부분, 하나는 꽥꽥거리는 부분이다.<br>
fly(),quack()을 Duck 클래스에서 분리하려면 2개의 메소드 모두 Duck 클래스에서 끄집어내서 각 행동을 나타낼 클래스 집합을 새로 만들어야 한다.<br>

### 오리 행동 디자인하기
최대한 유연하고 오리의 인스터늣에 행동을 할당할 수 있어야한다.

**구현보다는 인터페이스에 맞춰서 프로그래밍한다.**

FlyBehavior,QuackBehavior을 행동 인터페이스로 만들고 특정 행동(FlyWithWings,FlyNoWay)을 구현 객체로 사용한다.<br>
따라서 실제 행동 구현은 Duck 서브 클래스에 국한되지 않는다.<br>

#### 꼭 인터페이스일 필요는 없다.
인터페이스를 반드시 사용하는게 아닌 상위 형식으로 선언하고 상위 형식에 맞춰서 프로그래밍하자는 의미다.<br>
그래서 추상 클래스를 사용해도 될거 같다.<br>

### 오리의 행동을 구현하는 방법
FlyBehavior,QuackBehavior이라는 2개의 인터페이스를 사용하고 그에 대한 구현 객체들이 있다.
```
Code/
├── FlyBehavior.java (인터페이스)
│   ├── FlyWithWings.java (구현체)
│   └── FlyNoWay.java (구현체)
└── QuackBehavior.java (인터페이스)
    ├── Quack.java (구현체)
    ├── Squeak.java (구현체)
    └── MuteQuack.java (구현체)
```

각 파일의 역할:
- `FlyBehavior`: 나는 행동을 정의하는 인터페이스
  - `FlyWithWings`: 날개로 나는 행동 구현
  - `FlyNoWay`: 날 수 없는 행동 구현
- `QuackBehavior`: 울음 소리를 정의하는 인터페이스
  - `Quack`: 일반적인 오리의 꽥꽥 소리 구현
  - `Squeak`: 고무 오리의 삑삑 소리 구현
  - `MuteQuack`: 소리를 내지 않는 행동 구현

### 오리 행동 통합하기
가장 중요한 점은 Duck 클래스에서 정의한 메소드를 써서 구현하지 않고 다른 클래스에 위임한다는 것이다.
우선 Duck 클래스에 flyBehavior, quackBehavior 인터페이스 형식의 인스턴스 변수를 추가한다.<br>
각 오리 객체에서는 실행 시에 이 변수에 특정 행동 형식의 레퍼런스를 다형적으로 설정이 된다.(인터페이스가 변수니..)<br>
Duck에서 fly,quack 메소드를 제거하고 performQuack(),performFly()를 추가한다.
이제 총괄 코드를 Code 폴더에서 봐보자.

MallardDuck은 Duck이라는 추상 클래스를 상속했고 Duck은 인터페이스 QuackBehavior,FlyBehavior 2개가 있다. <br>
MallardDuck이 QuackBehavior,FlyBehavior 변수를 가지고 있고 인터페이스 구현 객체인 Quack,FlyWithWings를 대입하였으니 결국 꽥, 날 수 있는 객체가 된 것이다.

### 동적으로 행동 지정하기
Duck 클래스에 메소드 2개를 추가해보자.
```
public void setFlyBehavior(FlyBehavior fb) {
    flyBehavior = fb;
}
public void setQuackBehavior(QuackBehavior qb) {
    quackBehavior = qb;
}
```

이 두 메소드를 호출하면 언제든지 오리의 행동을 바꿀 수 있다.     
ModelDuck 클래스를 만든다.<br>

```
package Code;

public class ModelDuck extends Duck {
    public ModelDuck() {
        quackBehavior = new Quack();
        flyBehavior = new FlyNoWay();
    }

    @Override
    public void display() {
        System.out.println("저는 모형 오리입니다.");
    }
}
```

그리고 FlyRocketPowered를 새로 만든다.

```
public class FlyRocketPowered implements FlyBehavior {
    public void fly() {
        System.out.println("로켓 추진으로 날아갑니다.");
    }
}
```

MiniDuckSimulator에서 수정하고 테스트 해보자.<br>
FlyRocketPowered로 인해서 ModelDuck도 날 수 있게 되었다.<br>
실행 결과<br>
```
꽥꽥
날개로 날고 있습니다.
날 수 없습니다.
로켓 추진으로 날아갑니다.
```

### is-a, has-a, implements
A는 B이다 = is-a, MallardDuck is-a Duck -> A는 B의 하위 개념이다.<br>
A에는 B가 있다 = has-a, Duck has-a FlyBehavior -> A 클래스는 B 클래스를 멤버 변수로 갖는다.<br>
A가 B를 구현한다 = implements, Quack,Squeak,MuteQuack는 QuackBehavior을 구현한다. -> A 클래스가 B 인터페이스의 규칙(메서드)을 따라 구현한 것이다.<br>

has-a는 composition(구성)이다.     
각 오리에는 FlyBehavior,QuackBehavior이 있고 각각 나는 행동, 꽥꽥거리는 행동을 위임받는다.
이런 식으로 두 클래스를 합치는 것을 구성(composition)을 이용한다고 한다.     

**상속 보다는 구성(composition)을 활용한다.**

### 전략 패턴(Strategy Pattern)
전략 패턴은 알고리즘군을 정의하고 캡슐화해서 각각의 알고리즘군을 수정해서 쓸 수 있게 해줍니다.<br>
전략 패턴을 사용하면 클라이언트로부터 알고리즘을 분해해서 독립적으로 변경할 수 있습니다.<br>
즉,행동(알고리즘) 들을 별도 클래스로 분리해서 필요할 때마다 바꿔서 쓸 수 있게 만든다는 뜻입니다.<br>
덕분에 알고리즘이 클라이언트(여기서는 오리) 코드에 직접 박혀 있지 않아서 독립적으로 수정할 수 있습니다.<br>

### 디자인 패턴
1. 디자인 패턴은 개발자 사이에서 서로 모두가 이해할 수 있는 용어를 제공한다. 일단 용어를 안다면 훨씬 쉽게 다른 개발자와 대화하고 코드를 이해할 수 있다.
2. 패턴으로 소통하면 일상어보다 훨씬 효율적인 의사소통이 가능하다.
3. 패턴 수준에서 이야기하면 디자인에 더 오랫동안 집중할 수 있다.

