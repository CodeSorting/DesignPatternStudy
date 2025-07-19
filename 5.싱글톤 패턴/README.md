## Chapter 5

### 개요
전체 프로그램에서 하나만 있어도 충분히 잘 돌아가는 객체가 많다.     
스레드 풀, 캐시, 대화상자, 사용자 설정, 로그 기록용 객체, 디바이스 드라이버가 그 예시이다.     
싱글턴 패턴은 특정 클래스에 객체 인스턴스가 1개만 생기도록 보장하여서 객체 인스턴스를 어디서든지 접근 가능하며, 전역 변수의 단점(전역 변수는 프로그램이 끝날 때까지 존재한다. 그러나 싱글톤 패턴은 필요할 때 없앨 수 있다.)을 감수할 필요가 없다.

### private 생성자
다음과 같은 형식으로도 클래스를 작성할 수 있다. 이는 문법적으로 문제 없다.
```java
public A {
    private A() {} //생성자
}
```
private로 선언된 생성자를 사용할 수 있는 방법은 다음과 같다.
```java
public A {
    private A() {} //생성자
    public static A getInstance() {
        return new A();
    }
}
```
이제 A.getInstance()로 객체를 만들 수 있다.

### 고전적인 싱글턴 패턴 구현법
```java
public class Singleton {
    private static Singleton uniqueInstance;
    
    //기타 인스턴스 변수
    
    private Singleton() {}

    public static Singleton getInstance() {
        if (uniqueInstance==null) {
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }
    
    //기타 메소드
}
```
이를 이용해 한 애플리케이션에 들어있는 어떤 객체에서도 같은 자원을 활용할 수 있다.<br>
생성자가 public이 아닌 private인 것 + static 참조 변수를 이용해서 구현했다.

### 초콜릿 보일러 코드 살펴보기
대다수의 초콜릿 공장에는 초콜릿을 끓이는 장치(초콜릿 보일러)와 이를 제어하는 컴퓨터가 있다.     
이 초콜릿 보일러는 우유를 받아서 끓이고 초코바를 만드는 단계에 넘겨준다.

ChocolateBoiler.java를 보면 보일러가 비어있을 때 fill() 하고 boil() 하고 drain() 해서 다음 단계로 넘긴다.

세심하게 잘 짰지만 ChocolateBoiler 인스턴스 2개가 따로 돌아가면 안 좋은 상황이 발생한다.

싱글턴으로 업그레이드 해보자. ChocolateBoiler_Singleton.java를 봐보자.
```java
private static ChocolateBoiler_Singleton uniqueInstance;
private ChocolateBoiler_Singleton() {
    empty = true;
    boiled = false;
}
public static ChocolateBoiler_Singleton getInstance() {
    if (uniqueInstance == null) {
        uniqueInstance = new ChocolateBoiler_Singleton();
    }
    return uniqueInstance;
}
```

### 싱글턴 패턴의 정의
싱글턴 패턴은 클래스 인스턴스를 하나만 만들고, 그 인스턴스로의 전역 접근을 제공한다.<br>
말 그대로 어디서든 그 인스턴스에 접근할 수 있도록 전역 접근 지점을 제공한다. <br>
자원을 많이 잡아먹는 인스턴스가 있다면 Lazy 방식으로 생성하도록 하면 유용하다.<br>

### 문제 발생!
초콜릿 보일러의 fill()에서 끓고(boil 실행 중인데) 있는데 새로운 재료를 넣고 말았다.<br>
멀티 스레딩 문제가 발생하였다.

1번 스레드                                         2번 스레드<br>
public static ChocolateBoiler getInstance() {<br>

                                                   public static ChocolateBoiler getInstance() {<br>
    
if (uniqueInstance==null) {<br>

                                                   if (uniqueInstance==null) {<br>
uniqueInstance = new ChocolateBoiler();<br>
return uniqueInstance;<br>

                                                   uniqueInstance = new ChocolateBoiler();<br>
                                                   return uniqueInstance;<br>
                                                
