## Chapter 3

### 초대형 커피 전문점, 스타버즈
스타버즈 커피는 초대형 커피 전문점이다.     
빠르게 성장하다 보니 다양한 음료를 모두 포괄하는 주문 시스템을 못 갖췄다.     
주문 시스템을 한번 개선해보자.     

#### 현재 상황
주문 시스템 클래스는 다음 처럼 구성되어 있다.     
```
Beverage 추상 클래스 : cost(), getDescription(), description 변수(서브클래스에서 받아감.)
├── HouseBlendWithSteamedMilkandMocha.java (구현 객체)
├── DarkRoastWithSteamedMilk.java (구현 객체)
├── DecafWithSoy.java (구현 객체)
... 이외의 수십가지들
└── EspressoWithWhipandSoy(구현 객체)
```

이렇게 클래스를 많이 구성하면 복잡해짐.

### 1차 개선 : 인스턴스 변수 + 슈퍼클래스 상속
```
Beverage 클래스
description 문자열 변수, milk,soy,mocha,whip 불리언 변수

getDescription(),cost()
hasMilk(),setMilk()
hasSoy(),setSoy()
hasMocha(),setMocha()
hasWhip(),setWhip()

HouseBlend,DarkRoast,Decaf,Espresso 클래스 extends Beverage
cost() 메서드
```

#### 단점
1. 첨가물 가격이 변하면 기존 코드를 수정해야함.
2. 첨가물 종류가 많아지면 새 메소드를 추가하고, 슈퍼 클래스의 cost()도 고쳐야함.
3. 새로운 음료가 나오는데 특정 첨가물이 들어가면 안되는 음료도 있을 것이다. 그럴 경우에 hasWhip(),hasMocha() 같은 메서드는 상속 받으면 안된다.

**상속이 강력하긴 하지만, 여기서는 상속 보다는 구성,위임을 이용해 실행 중에 동적으로 행동을 설정하자.**

### OCP(Open-Closed Principle)
클래스는 확장에는 열려 있어야 하지만 변경에는 닫혀 있어야 한다.<br>
-> 기존 코드는 수정하지 말고 확장으로 새로운 행동을 추가하는 것이 좋다.<br>
왜냐하면 기존 코드를 수정하면 도미노처럼 다른 코드 또한 수정하는 일이 많기 때문이다.<br>

### 데코레이터 패턴 살펴보기
특정 음료에서 시작해서 첨가물로 그 음료를 **장식(decorate)** 해보자.<br>
ex) DarkRoast + Mocha 장식 + Whip 장식 -> cost() 메서드 호출 (이때 첨가물 가격을 계산하는 일은 해당 객체에게 위임한다.)

마치 **스택**처럼 DarkRoast 객체에서 시작하여 DarkRoast->Mocha->Whip 순서로 쌓고 가격을 계산할 때는<br>
1. Whip cost() 호출
2. Whip이 Mocha cost() 호출
3. Mocha가 DarkRoast cost() 호출
4. DarkRoast가 99센트 리턴
5. Mocha가 99 + 20센트 리턴
6. Whip이 99 + 20 + 10센트 리턴
7. 최종적으로 1.29 달러를 리턴하는 과정이다.

- 데코레이터의 슈퍼클래스는 자신이 장식하고 있는 객체의 슈퍼클래스와 같다. (ConcreteComponent <-> Decorator)
- 한 객체를 여러 개의 데코레이터로 감쌀 수 있다.
- **데코레이터는 자신이 장식하고 있는 객체에게 어떤 행동을 위임하는 일 말고도 추가 작업을 수행할 수 있다.**

### 데코레이터 패턴 정의
데코레이터 패턴(Decorator Pattern)으로 객체에 추가 요소를 동적으로 더할 수 있다.<br>
데코레이터를 사용하면 서브 클래스를 만들 때보다 훨씬 유연하게 기능을 확장할 수 있다.
```
Component (추상 클래스/인터페이스)
├── ConcreteComponent (구현체)
└── Decorator (추상 클래스, Component 구현)
    ├── ConcreteDecoratorA (구현체)
    └── ConcreteDecoratorB (구현체)

Beverage (Component)
├── DarkRoast (ConcreteComponent)
├── HouseBlend (ConcreteComponent)
├── Decaf (ConcreteComponent)
├── Espresso (ConcreteComponent)
└── CondimentDecorator (Decorator)
    ├── Mocha (ConcreteDecoratorA)
    ├── Soy (ConcreteDecoratorB)
    ├── Milk (ConcreteDecoratorC)
    └── Whip (ConcreteDecoratorD)    
```

데코레이터 패턴에서는 상속을 사용하지만 상속으로 인해 행동이 아닌 형식을 맞추는 거라 상관 없다.

```java
Beverage beverage3 = new HouseBlend();
beverage3 = new Mocha(beverage3);
beverage3 = new Whip(beverage3);
beverage3 = new Soy(beverage3);
System.out.println(beverage3.getDescription() + " $" + beverage3.cost());
```
1. beverage3 = HouseBlend 객체를 만들고 참조한다.
2. beverage3 = Mocha 객체를 만들고 참조한다. 그러나 Mocha 객체 안에는 beverage라는 변수가 있고 그 변수는 HouseBlend를 참조한다.
3. 마찬가지로 Whip 객체를 만들고 참조한다. 그러나 Whip -> Mocha -> HouseBlend로 참조한다.
4. Soy -> Whip -> Mocha -> HouseBlend 순으로 참조되며 cost()가 호출되면 ($0.15 + ($0.10 + ($0.20 + $0.89))) = $1.34가 된다.

## 데코레이터 패턴 적용 예시 : java.io
java.io 패키지에는 정말 많은 클래스들이 있다. 이는 데코레이터 패턴을 바탕으로 만들어져있다.<br>
ZipInputStream -> BufferedInputStream -> FileInputStream 같은 형식으로 구성된다.<br>
Zip 폴더 항목 받아오기 + 버퍼링 기능 추가(속도 UP) + 파일 읽기

```
InputStream (추상 Component)
├── FileInputStream (ConcreteComponent)
├── StringBufferInputStream (ConcreteComponent)
├── ByteArrayInputStream (ConcreteComponent)
└── FilterInputStream (추상 Decorator)
    ├── BufferedInputStream (ConcreteDecorator)
    ├── DataInputStream (ConcreteDecorator)
    ├── PushbackInputStream (ConcreteDecorator)
    └── InflatorInputStream (ConcreteDecorator)  
        └── ZipInputStream (inherited ConcreteDecorator)
```

### 데코레이터 패턴의 단점
잡다한 클래스가 너무 많아진다. 그렇다 보니 데코레이터 기반 API를 사용해야하는 개발자는 괴로워한다.

### 추가 활동
각각 사이즈를 추가한다고 해보자. TALL,GRANDE,VENTI가 있다고 할 때 데코레이터 클래스를 고쳐보자.
```java
//Decorator 클래스
public abstract class CondimentDecorator extends Beverage {
    Beverage beverage;
    public abstract String getDescription();
    public Size getSize() {
        return beverage.getSize();
    }
}
//Soy 클래스(Mocha,Whip도 적용해야함.)
public class Soy extends CondimentDecorator {
    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 두유";
    }

    @Override
    public double cost() {
        double cost = beverage.cost();
        if (beverage.getSize() == Size.TALL) { //사이즈 얻고 사이즈마다 금액 추가
            cost += 0.15;
        } else if (beverage.getSize() == Size.GRANDE) {
            cost += 0.20;
        } else if (beverage.getSize() == Size.VENTI) {
            cost += 0.25;
        }
        return cost;
    }    
}
```
