## Chapter 10

### 최첨단 뽑기 기계
뽑기 기계 회사 제품에 CPU를 넣어서 매출액도 늘리고 고객 만족도도 정확하게 집계하고 싶어한다고 해보자.

현재 상태는 동전 있음, 동전 없음, 알맹이 판매, 알맹이 매진이 있다.<br>
동전을 넣거나 알맹이가 없을 때마다 상태가 전환된다.

사용자가 어떤 행동을 하든 지금 어떤 상태에 있는지 확인하고, 이에 따라 적절한 행동을 취해야한다.

일단 상태들을 모아보자.
```java
final static int SOLD_OUT = 0; //알맹이 없음
final static int NO_QUARTER = 1; //동전 없음.
final static int HAS_QUARTER = 2; //동전 있음
final static int SOLD = 3; //알맹이 있음.

int state = SOLD_OUT;
```

이에 따라 함수를 만들 수 있다.

```java
public class GumballMachine {
    final static int SOLD_OUT = 0;
    final static int NO_QUARTER = 1;
    final static int HAS_QUARTER = 2;
    final static int SOLD = 3;
    int state = SOLD_OUT;
    int count = 0;

    public GumballMachine(int count) {
        this.count = count;
        if (count > 0) {
            state = NO_QUARTER;
        }
    }
    public void insertQuarter() {
        if (state == HAS_QUARTER) {
            System.out.println("동전이 이미 투입되었습니다.");
        } else if (state == NO_QUARTER) {
            state = HAS_QUARTER;
            System.out.println("동전이 투입되었습니다.");
        } else if (state == SOLD_OUT) {
            System.out.println("매진되었습니다. 동전을 반환합니다.");
        } else if (state == SOLD) {
            System.out.println("알맹이가 나가는 중입니다. 잠시 기다려주세요.");
        }
    } 
    //기타 메소드들...
}
```

단점 : **너무 많은 조건문을 만들어야 하고 새로 나올 때마다 코드를 많이 고쳐야한다.**

### 바뀌는 부분 캡슐화하기
상태는 계속 바뀌니 State 인터페이스를 만들자.

```java
public interface State {
    void insertQuarter();
    void ejectQuarter();
    void turnCrank();
    void dispense();
    void refill();
}
```

그 다음에 State 인터페이스를 구현한 객체 5가지(당첨된 상태도 추가) 상태를 만들고 나서 GumballMachine에서 이를 구성(Composite)한다.

```java

public class NoQuarterState implements State {
    GumballMachine gumballMachine;

    public NoQuarterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    public void insertQuarter() {
        System.out.println("동전을 넣었습니다.");
        gumballMachine.setState(gumballMachine.getHasQuarterState());
    }

    public void ejectQuarter() {
        System.out.println("동전을 넣지 않았습니다.");
    }

    public void turnCrank() {
        System.out.println("동전을 넣어주세요.");
    }

    public void dispense() {
        System.out.println("동전을 넣어주세요.");
    }

    public void refill() {
        // No action needed
    }
    
}
```

```java
//GumballMachine.java
State noQuarterState;
State hasQuarterState;
State soldOutState;
State soldState;
State winnerState; //당첨된 상태도 추가!

State state = soldOutState;
```

전체 구조는 다음과 같다.
```
Success (디렉터리)
 ├── GumballMachine.java (Context)
 │   ├── 상태를 관리하며, 현재 상태에 따라 동작을 위임
 │   ├── State 인터페이스를 구현한 상태 객체를 포함
 │   └── 상태 전환 메서드 (setState, getState 등) 포함
 │
 ├── State.java (State 인터페이스)
 │   ├── insertQuarter()
 │   ├── ejectQuarter()
 │   ├── turnCrank()
 │   ├── dispense()
 │   └── refill()
 │
 ├── NoQuarterState.java (ConcreteState)
 │   ├── 동전 없음 상태를 구현
 │   └── GumballMachine의 상태를 변경
 │
 ├── HasQuarterState.java (ConcreteState)
 │   ├── 동전 있음 상태를 구현
 │   └── GumballMachine의 상태를 변경
 │
 ├── SoldState.java (ConcreteState)
 │   ├── 알맹이 판매 상태를 구현
 │   └── GumballMachine의 상태를 변경
 │
 ├── SoldOutState.java (ConcreteState)
 │   ├── 매진 상태를 구현
 │   └── GumballMachine의 상태를 변경
 │
 └── WinnerState.java (ConcreteState, 선택 사항)
     ├── 특별 상태 (예: 보너스 알맹이 제공)
     └── GumballMachine의 상태를 변경
```

### 상태 패턴의 정의
상태 패턴을 사용하면 객체의 내부 상태가 바뀜에 따라서 객체의 행동을 바꿀 수 있다. 마치 객체의 클래스가 바뀌는 것과 같은 결과를 얻을 수 있다.

상태를 별도의 클래스로 캡슐화한 다음 현재 상태를 나타내는 객체에게 행동을 위임하므로 내부 상태가 바뀔 때 행동이 달라지게 된다는 사실을 쉽게 알 수 있다.

클라이언트의 관점에서 지금 상태에 따라 사용하는 객체의 행동이 완전히 달라져 마치 그 객체가 다른 클래스로부터 만들어진 객체처럼 느껴진다.

상태패턴을 사용하지 않으면, 상태마다의 모든 분기를 if문을 사용하여 분기 처리하여야 한다. 즉, 확장에 닫혀있게 되고, 상태패턴을 이용하면 확장에 비교적 열려있게 된다.

### 상태 패턴 vs 전략 패턴
상태 패턴은 상태 객체를 캡슐화해서 클라이언트가 상태 객체를 몰라도 된다.

하지만 전략 패턴은 일반적으로 클라이언트가 Context 객체에게 어떤 전략 객체를 사용할지를 지정해준다.