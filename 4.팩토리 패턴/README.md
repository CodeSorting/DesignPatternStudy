## Chapter 4

### new의 문제
new를 사용하면 구상(concrete : 구체적인) 클래스의 인스턴스가 만들어진다. (특정 구현 사용)     
```java
Duck duck;
if (picnic) {
    duck = new MallardDuck();
} else if (hunting) {
    duck = new DecoyDuck();
} else {
    duck = new RubberDuck();
}
```
이렇게 코드를 짜면 나중에 새로운 클래스가 생길 때 코드를 더 써서 수정하거나 제거해야한다.     
new를 안 쓸 수는 없지만 **변화**하는 무언가 때문에 new를 조심해서 써야한다.     
인터페이스에 맞춰서 코딩하면 여러 변화에 대응할 수 있다. (다형성)     
반대로 구상 클래스를 많이 쓰면 클래스가 추가 될 때마다 기존 코드를 고쳐야하므로 문제가 생긴다.<br>
즉, 변경에 닫혀있는 코드가 된다. 새로운 구상 형식을 써서 확장해야 할 때는 어떻게 해서든 다시 열 수 있게 만들어야 한다.     
-> 바뀌지 않는 부분과 바뀌는 부분을 찾아내서 분리해보자.

### 최첨단 피자 코드 만들기
피자 가게를 운영해보자. 최첨단 피자 가게인만큼 이미 코드를 어느정도 만들어놓았다.
```java
Pizza orderPizza(String type) {
    Pizza pizza;

    if (type.equals("cheese")) {
        pizza = new CheesePizza();
    } else if (type.equals("greek")) {
        pizza = new GreekPizza();
    } else if (type.equals("pepperoni")) {
        pizza = new PepperoniPizza();
    }

    pizza.prepare();
    pizza.back();
    pizza.cut();
    pizza.box();
    return pizza;
}
```
이제 신메뉴를 추가하고 기존 메뉴를 없애볼려고 해보자.     
ex) greek을 없애고 clam,veggie를 추가해본다고 하자. 이럴 경우에 orderPizza 부분에서 일부분을 지우고 다시 수정해야한다.

피자를 prepare,back,cut,box 부분은 안 변하고 타입에 따라 객체를 만드는 부분은 변한다.     
이제 객체 생성 부분의 캡슐화를 진행해보자.

### 객체 생성 팩토리 만들기
객체 생성을 처리하는 클래스를 Factory(팩토리)라고 부르자.     
이제 한 번 팩토리로 피자 객체를 생성하도록 만들어보자.
```java
public class PizzaStore {
    SimplePizzaFactory factory;
    public PizzaStore(SimplePizzaFactory factory) {
        this.factory = factory;
    }
    public Pizza orderPizza(String type) {
        Pizza pizza;
        pizza = factory.createPizza(type); //피자 팩토리에게 타입에 따라 만들라고 전하기만 하면 된다.
        pizza.prepare();
        pizza.back();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
//이제 SimplePizzaFactory에서 createPizza() 구현하면 된다.
```

### 간단한 팩토리
프로그래밍에서 쓰이는 관용구에 가깝다.
객체의 생성 역할을 별도의 팩토리 클래스에 맡기는 방법이다.<br>
즉, 객체를 사용하는 쪽(클라이언트)에서 구체적으로 어떤 객체를 만들지 직접 결정하지 않고, 팩토리 클래스에 생성 요청만 하면 팩토리가 대신 적절한 객체를 만들어 반환해주는 방식이다.<br>
정확히 말하면 패턴은 아닌데 중요하다.

```
Pizza (추상 클래스) : prepare(),bake(),cut(),box()
├── CheesePizza (구현체)
├── VeggiePizza (구현체)
├── ClamPizza (구현체)
└── PepperoniPizza (구현체)

PizzaStore (피자 가게) : orderPizza()로 createPizza 호출
└── SimplePizzaFactory (팩토리 클래스) : createPizza() 정의
```

### 다양한 팩토리 만들기
피자가게가 인기를 끌면서, 이제 지점을 내기로 한다.<br>
각 지점마다 그 지역의 특징을 반영한 다양한 스타일의 피자(뉴욕 스타일, 시카고 스타일, 캘리포니아 스타일 등)을 만들어야한다.     
PizzaStore -> NYPizzaFactory,ChicagoPizzaFactory 처럼 말이다.

```java
public abstract class PizzaStore {

    public Pizza orderPizza(String type) {
        Pizza pizza;
        pizza = createPizza(type); //피자 팩토리에게 타입에 따라 만들라고 전하기만 하면 된다.
        pizza.prepare();
        pizza.back();
        pizza.cut();
        pizza.box();
        return pizza;
    }
    // 원래 SimplePizzaFactory가 구현했던 것 대신에 추상 메서드를 넣어서
    // 상속받은 Store이 피자를 만들도록 하여 다각화한다.  
    abstract Pizza createPizza(String type); 
}
```

PizzaStore 클래스는 이제 createPizza()라는 추상 메서드, orderPizza()라는 메서드를 만들었다.<br>
이제 상속한 NYStylePizzaStore,ChicagoPizzaStore를 사용한다. 그리고 각 store 마다 createPizza를 구현하면 된다.<br>

```java
public class NYPizzaStore extends PizzaStore {
    Pizza createPizza(String item) {
        if (item.equals("cheese")) {
            return new NYStyleCheesePizza();
        } //... 나머지
    }
}
```

### 제작 과정
1. PizzaStore nyPizzaStore = new NYPizzaStore();
2. nyPizzaStore.orderPizza("cheese");
3. Pizza pizza = createPizza("cheese");
4. NY의 치즈 피자 객체 받았으니 prepare(),bake(),cut(),box() 호출

### pizzafm의 계층 구조
```
PizzaStore (생산자 클래스)
├── NYPizzaStore (구현체)
│   └── createPizza() // NY 스타일 피자들 생성
└── ChicagoPizzaStore (구현체)
    └── createPizza() // Chicago 스타일 피자들 생성

Pizza (제품 클래스)
├── NYStylePizza/ (뉴욕 스타일 피자들)
│   ├── NYStyleCheesePizza
│   ├── NYStyleVeggiePizza
│   ├── NYStyleClamPizza
│   └── NYStylePepperoniPizza
└── ChicagoStylePizza/ (시카고 스타일 피자들)
    ├── ChicagoStyleCheesePizza
    ├── ChicagoStyleVeggiePizza
    ├── ChicagoStyleClamPizza
    └── ChicagoStylePepperoniPizza
```

### 팩토리 메소드 패턴
팩토리 메소드 패턴(Factory Method Pattern)에서는 객체를 생성할 때 필요한 인터페이스를 만든다. 어떤 클래스의 인스턴스를 만들지는 서브클래스에서 결정한다. 팩토리 메소드 패턴을 사용하면 클래스 인스턴스 만드는 일을 서브클래스에게 맡기게 된다.(위임)

### 의존성 역전 원칙
맨 앞처럼 PizzaStore 을 상속한 다양한 피자들을 직접 구현하면 PizzaStore은 모든 피자 객체에게 직접 의존한다. 피자 클래스들의 구현이 변경되면 PizzaStore도 변경해야한다.

따라서 구상 클래스의 의존성을 줄이면 좋다.<br>
즉, **추상화된 것(고수준의 PizzaStore)에 의존하게 만들고 구상 클래스(저수준의 Pizza 클래스)에 의존하지 않게 만드는 것이 중요하다.**<br>
이러한 것을 의존성 뒤집기 원칙(의존성 역전 원칙)이라고 한다.<br>
고수준의 구성 요소가 저수준의 구성 요소에 의존하면 안된다.<br>

### 의존성 역전 원칙 지키는 방법
1. 변수에 구상 클래스의 레퍼런스 저장 X (new X)
2. 구상 클래스에서 유도된 클래스를 만들지 말자. -> 추상화된 인터페이스, 추상 클래스 먼저 만들기
3. 베이스 클래스에 이미 구현되어 있는 메소드를 오버라이드 하지 말자.


### 원재로 종류 알아보기
PizzaStore이 성공하였다! 그러나 이런 성공에는 피자 재료의 중요성도 한몫한다.<br>
피자 요리 절차는 잘 지켜지는데 몇몇 지점에서 자잘한 재료를 더 싼 재료로 바꾸거나 재료를 제대로 사용하고 있지 않다고 한다. 이를 고쳐보자.

제품에 들어가는 재료군(반죽, 소스, 치즈, 야채, 고기)는 같지만, 지역마다 재료의 구체적인 종류는 조금씩 다르다.

시카고, 뉴욕, 캘리포니아마다 있는 미묘한 차이를 지닌 재료들을 어떻게 처리해야할까?<br>
이 역시 원재료 팩토리를 만들고 이를 구현한 NYPizzaIngredientFactory,ChicagoPizzaIngredientFactory을 만들면 된다.

재료 인터페이스 Dough,Sauce,Cheese,Veggies,Clams,Pepperoni를 만들고 이를 구현한 객체들을 만들면서 각각 도우,소스,치즈, 야채 등 뭘 쓸지 정할 수 있다.

다음과 같은 구조를 따른다.

pizzaaf
```
PizzaIngredientFactory (추상 팩토리 인터페이스)
├── NYPizzaIngredientFactory (구현체)
└── ChicagoPizzaIngredientFactory (구현체)

재료 인터페이스들/
├── Dough (인터페이스)
│   ├── ThinCrustDough
│   └── ThickCrustDough
├── Sauce (인터페이스)
│   ├── MarinaraSauce
│   └── PlumTomatoSauce
├── Cheese (인터페이스)
│   ├── ReggianoCheese
│   ├── MozzarellaCheese
│   └── ParmesanCheese
├── Veggies (인터페이스)
│   ├── Garlic
│   ├── Onion
│   ├── Mushroom
│   ├── RedPepper
│   ├── Spinach
│   ├── BlackOlives
│   └── Eggplant
├── Pepperoni (인터페이스)
│   └── SlicedPepperoni
└── Clams (인터페이스)
    ├── FreshClams
    └── FrozenClams

Pizza (추상 클래스)
├── CheesePizza
├── VeggiePizza
├── ClamPizza
└── PepperoniPizza

PizzaStore (추상 클래스)
├── NYPizzaStore
└── ChicagoPizzaStore
```

### 피자가 만들어지기까지 과정
1. PizzaStore = nyPizzaStore = new NYPizzaStore();
2. nyPizzaStore.orderPizza("cheese"); // 피자 주문 메서드 안에서 createPizza 호출함.
3. Pizza pizza = createPizza("cheese");
4. Pizza pizza = new CheesePizza(nyIngredientFactory); // createPizza()가 호출되면 원재료 팩토리가 돌아가기 시작한다.
5. prepare() 안에서 dough = factory.createDough(),createSauce(),createCheese()로 선택된다.

### 추상 팩토리 패턴
구상(concrete) 클래스에 의존하지 않고도 서로 연관되거나 의존적인 객체로 이루어진 제품군을 생산하는 인터페이스를 제공한다. 구상 클래스는 서브 클래스에서 만든다.

