## Chapter 7

### 충전기
한국에서도 쓰는 충전기를 영국에서도 쓸려면 플러그 모양을 바꿔주는 어댑터가 필요하다.<br>
어댑터는 국산 전원 플러그를 영국식 전원 소켓에 꽂을 수 있게 하는 역할을 한다. 이처럼 한 객체와 다른 객체를 잇는 역할을 어댑터 패턴이 하게 된다.

### 어댑터 패턴 알아보기
어댑터 패턴은 클라이언트, 어댑터, 어댑티로 이루어져있다.<br>
어댑터는 타깃 인터페이스를 구현하며, 여기에는 어댑티(adaptee) 인스턴스가 들어가 있다.<br>
Adapter 디렉터리 안의 파일 구조를 봐보자.
```
Client
└── DuckTestDrive (클라이언트) : 메인 함수

Target (클라이언트가 사용하려는 인터페이스)
└── Duck (인터페이스)
    └── MallardDuck (구현체)
        ├── quack()
        └── fly()

Adaptee (어댑터가 필요한 기존 인터페이스)
└── Turkey (인터페이스)
    └── WildTurkey (구현체)
        ├── gobble()
        └── fly()

Adapter
└── TurkeyAdapter (Turkey를 Duck처럼 보이게 하는 어댑터)
    ├── quack() // Turkey의 gobble() 호출
    └── fly()   // Turkey의 fly()를 여러 번 호출
```

Duck 타입의 인터페이스로 서로 떨어진 Turkey를 구현하였다. 이는 구성, interface 구현을 이용하면 된다.
```java
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
```

### 어댑터 패턴의 정의
어댑터 패턴은 특정 클래스 인터페이스를 클라이언트에서 요구하는 다른 인터페이스로 변환한다. 인터페이스가 호환되지 않아 같이 쓸 수 없었던 클래스를 사용할 수 있게 도와준다.<br>
Client <-> Target <-> Adapter <-> Adaptee 이런 식으로 구성되어 있다.

### 클래스 어댑터
앞에서의 객체 어댑터와 다르게, Adapter을 다중 상속시켜서 서로 연결 시키는 방식도 있다.<br>
Target, Adaptee를 Adapter을 상속시켜서 쓰면 된다.

### 실전 예제
자바에서는 Enumeration은 옛날 버전의 iterator이다. 그 둘을 호환시키기 위해서 Enumeration을 iterator에 어댑터 패턴으로 적응 시켜보자.

Enumeration = 어댑티, Iterator = 타겟, EnumerationIterator = 어댑터라고 하자.
```java
public class EnumerationIterator implements Iterator<Object> {
    Enumeration<?> enumeration;

    public EnumerationIterator(Enumeration<?> enumeration) {
        this.enumeration = enumeration;
    }
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }
    public Object next() {
        return enumeration.nextElement();
    }
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
```

위와 같이 하면 Enumeration, Iterator를 연결시킬 수 있다.


### 퍼사드 패턴
이 패턴은 인터페이스를 단순하게 바꾸려고 인터페이스를 변경한다. 하나 이상의 클래스 인터페이스를 깔끔하면서도 효과적인 퍼사드로 덮어준다.

즉, 서브 시스템에 있는 일련의 인터페이스를 통합 인터페이스로 묶어주고 고수준 인터페이스도 정의하므로 서브시스템을 더 편하게 쓸 수 있게 하는 패턴이다.

퍼사드 패턴을 사용하면 클라이언트는 서브 시스템과 서로 긴밀하게 연결되지 않아도 된다.

즉, **클라이언트와 구성요소로 이루어진 서브 시스템을 분리하는 역할을 한다.**

퍼사드 패턴을 이해하는데 가장 중요한 것은 용도이다.

어댑터 패턴은 인터페이스를 다른 인터페이스로 바꾸기 위해 사용했다면, 퍼사드 패턴은 **인터페이스를 단순화하여 서브 시스템을 더 편리하게 사용하기 위해 사용된다.**

### 홈 시어터
영화를 보기위해 홈 시어터를 구축한다고 가정해보자.
홈 시어터를 위해 여러 클래스가 필요하고 심지어 서로 복잡하게 얽혀있어서 제대로 사용하려면 많은 인터페이스를 배우고 쓸 수 있어야 한다.

클라이언트에서 영화를 보는 것을 구현한다고 가정해보면, 필히 클래스들을 전부 구성하고 하나하나 전부 켜주어야만 할 것이다.

퍼사드 객체를 사용하여 클라이언트와 클래스들의 긴밀한 연결을 끊고, 클라이언트를 대신하여 복잡한 로직들을 구현한다.

이번에는 Facade 디렉터리 파일 구조 대신 아래의 인터페이스만 보면 될 것 같다.

```java
public class HomeTheaterFacade {
    Amplifier amp;
    Tuner tuner;
    StreamingPlayer player;
    CdPlayer cd;
    Projector projector;
    TheaterLights lights;
    Screen screen;
    PopcornPopper popper;

    public HomeTheaterFacade(Amplifier amp,
                             Tuner tuner,
                             StreamingPlayer player,
                             Projector projector,
                             Screen screen,
                             TheaterLights lights,
                             PopcornPopper popper) {

        this.amp = amp;
        this.tuner = tuner;
        this.player = player;
        this.projector = projector;
        this.screen = screen;
        this.lights = lights;
        this.popper = popper;
    }

    public void watchMovie(String movie) {
        System.out.println("Get ready to watch a movie...");
        popper.on();
        popper.pop();
        lights.dim(10);
        screen.down();
        projector.on();
        projector.wideScreenMode();
        amp.on();
        amp.setStreamingPlayer(player);
        amp.setSurroundSound();
        amp.setVolume(5);
        player.on();
        player.play(movie);
    }


    public void endMovie() {
        System.out.println("Shutting movie theater down...");
        popper.off();
        lights.on();
        screen.up();
        projector.off();
        amp.off();
        player.stop();
        player.off();
    }

    public void listenToRadio(double frequency) {
        System.out.println("Tuning in the airwaves...");
        tuner.on();
        tuner.setFrequency(frequency);
        amp.on();
        amp.setVolume(5);
        amp.setTuner(tuner);
    }

    public void endRadio() {
        System.out.println("Shutting down the tuner...");
        tuner.off();
        amp.off();
    }
}
```

복잡한 인터페이스, 클래스들을 묶어서 하나의 인터페이스, 클래스로 놓고 안쪽의 복잡한 문제들을 밖에서 못 보게 한다. (추상화)

### 디자인 원칙 : 최소 지식 원칙
**진짜 절친에게만 이야기해야 한다.**

위 말은 시스템을 디자인할 때 어떤 객체든 그 객체와 상호작용을 하는 클래스의 개수와 상호작용 방식에 주의를 기울여야 한다는 뜻이다.

최소 지식 원칙은 소프트웨어 **모듈 사이의 결합도를 줄여서 코드의 품질을 높이는 것이 목표이다.**

이 원칙을 지키면, 여러 클래스가 복잡하게 얽혀있어 시스템의 한 부분을 변경했을 때 다른 부분까지 줄줄이 고쳐야하는 상황을 미리 방지할 수 있다.


이 원칙을 지키기 위해 객체의 모든 메소드는 다음에 해당하는 메소드만을 호출해야 한다.

1. 객체 자체
2. 메소드에 매개변수로 전달된 객체
3. 메소드를 생성하거나 인스턴스를 만든 객체
4. 객체에 속하는 구성 요소

```java
public float getTemp(){
        Thermometer thermometer = station.getThermometer();
        return thermometer.getTemperature();
}
```

위 코드를 보면 station 인스턴스로부터 return 받은 객체의 메소드를 호출하여 최소 지식 원칙을 지키지 못하였다.

이를 지키기 위해선 Station 객체에 getTemperature() 메소드를 생성하는 것이 옳다.

퍼사드 패턴에서는 클라이언트의 친구를 퍼사드 객체 하나로 단순화하여, 최소 지식 원칙을 지켰다.

### 퍼사드 패턴 단점 (추가 조사)
최소 지식 원칙을 너무 엄격히 적용하면 코드가 너무 단순해지려는 방향으로 가게 되고, 그로 인해 코드 추적이 어려워질 수 있다.

객체가 너무 많은 중간 단계를 거쳐 코드 이해가 오래 걸리고 클래스가 너무 분리되어 있어 협업이 어려워진다. 

제일 중요한 것은 균형을 맞춰서 적당히 쓰는 것이다.