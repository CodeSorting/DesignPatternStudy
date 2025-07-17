## Chapter 2

### 인터넷 기반 기상 스테이션
WeatherData 객체 (온도,습도,기압 등 현재 기상 조건)을 바탕으로 3개의 항목을 화면에 표시하는 어플리케이션을 만드는게 목적이다.<br>
이 항목들은 모두 최신 측정치를 수집하고 실시간으로 갱신된다.<br>
이제 한 번 다른 개발자가 직접 새로운 날씨 디스플레이를 만들어서 바로 넣을 수 있도록 확장 가능하도록 만들어보자.<br>

이 시스템은 기상 스테이션(습도,온도,기압 감지하는 센서),WeatherData 객체(기상 스테이션으로 부터 오는 정보를 추적하는 객체),디스플레이 장비(사용자에게 보여줌.) 총 3가지로 이루어진다.     
즉, WeatherData 객체는 물리 기상 스테이션과 통신해서 갱신된 기상 데이터를 가져온다. 그리고 WeatherData를 업데이트하고 디스플레이로 나타낸다.     

### WeatherData 클래스 살펴보기
WeatherData를 먼저 살펴보자.<br>
```
WeatherData 클래스

getTemperature()
getHumidity()
getPressure()
measurementsChanged() //갱신된 값을 가져올 때마다 이 함수가 호출됨.

//기타 메소드
```

### 구현 목표
디스플레이를 구현하고 measurementsChanged()가 호출될 때마다 WeatherData를 업데이트해야한다.

그리고 나중에 디스플레이가 더 늘어날 수도 있으니 변화에 맞춰서 확장 기능을 추가하면 좋을 것이다.

**확장성** : 다른 개발자가 새로운 디스플레이를 만들고 싶을 수도 있고 사용자가 마음대로 디스플레이 요소를 더하거나 빼고 싶을 수도 있기 때문에 확장성을 고려하는 것도 나쁘지 않을 것이다.


```java
public class WeatherData {
    //인스턴스 변수들 선언
    
    public void measurementsChanged() {
        float temp = getTemperature();
        float humidity = getHumidity();
        float pressure = getPressure();

        //각 디스플레이 갱신
        currentConditionsDisplay.update(temp, humidity, pressure);
        statisticsDisplay.update(temp, humidity, pressure);
        forecastDisplay.update(temp, humidity, pressure);
    }    
}
```

그러나 이렇게 만들면 다음과 같은 단점들이 있다.
```java
//각 디스플레이 갱신
currentConditionsDisplay.update(temp, humidity, pressure);
statisticsDisplay.update(temp, humidity, pressure);
forecastDisplay.update(temp, humidity, pressure);
```
1. 인터페이스가 아닌 구체적인 구현 바탕이다. (인터페이스 X)
2. 새로운 디스플레이가 추가될 때마다 코드를 변경해야 한다. (갱신 함수 매개변수들을 다 고쳐야함.)
3. 실행 중에 디스플레이 항목을 추가하거나 제거할 수 없다. (동적으로 추가하거나 제거가 안됨.)
4. 바뀌는 부분을 캡슐화하지 않았다. (함수가 그대로 노출)

### 옵저버 패턴
신문이나 잡지를 구독하는 것을 생각하자.<br>
신문 구독신청을 하면 새 신문이 나올 때마다 배달을 받는다. 받고 싶지 않다면 해지를 한다.     
신문사가 망하지 않는 이상 개인,호텔,항공사 등 꾸준하게 신문을 받거나 해지한다.    

**신문사 + 구독자 = 옵저버 패턴**

신문사 = 주제(subject), 구독자 = 옵저버(observer)     
신문사는 주요 데이터 관리, 바뀌면 옵저버들에게 전달한다.     

**옵저버 패턴(Observer Pattern)** : 한 객체의 상태가 바뀌면 그 객체에 의존하는 다른 객체에게 연락이 가고 자동으로 내용이 갱신되는 방식으로 일대다(one-to-many) 의존성을 정의한다.

### 옵저버 패턴의 구조
```
Subject 인터페이스
registerObserver()
removeObserver()
notifyObserver()

Observser 인터페이스
update()

ConcreteObserver 클래스
update()
//기타 옵저버용 메소드

ConcreteSubject 클래스
registerObserver() {}
removeObserver() {}
notifyObservers() {}
getState()
setState()
```

### 느슨한 결합
바구니처럼 소프트웨어도 뻣뻣하고 단단하게 결합되어있으면 의존성이 크다.     
그러기에 객체들이 상호작용할 수는 있지만, 서로를 잘 모르게 하면 느슨한 결합이 돼고 소프트웨어 유연성이 좋아진다.     

1. 주제(subject)는 옵저버가 특정 인터페이슬르 구현한다는 사실만 안다.
2. 옵저버는 언제든지 새로 추가할 수 있다.
3. 주제와 옵저버는 느슨하게 결합되어 있어 서로 독립적으로 재사용이 가능하다.
4. 주제나 옵저버가 달라져도 서로에게 영향을 미치지 않는다.

**상호작용하는 객체 사이에는 가능하면 느슨한 결합을 사용해야 한다.**

### 기상 스테이션 설계하기

```
Code/
├── Subject.java (인터페이스)
│   └── WeatherData.java (구현체)
├── Observer.java (인터페이스)
├── DisplayElement.java (인터페이스)
└── Display 구현체들/
    ├── CurrentConditionDisplay.java (Observer, DisplayElement 구현)
    ├── StatisticsDisplay.java (Observer, DisplayElement 구현)
    └── ForecastDisplay.java (Observer, DisplayElement 구현)
```

실행결과
```
현재 상태: 온도 80.0°F, 습도 65.0%
Avg/Max/Min temperature: 80.0/80.0/80.0
예보: 날씨가 좋아질 것 같습니다.
현재 상태: 온도 82.0°F, 습도 70.0%
Avg/Max/Min temperature: 81.0/82.0/80.0
예보: 날씨가 나빠질 것 같습니다.
현재 상태: 온도 78.0°F, 습도 90.0%
Avg/Max/Min temperature: 80.0/82.0/78.0
예보: 날씨가 변하지 않을 것 같습니다.
```

Subject, Observer, DisplayElement를 인터페이스로 두고 각각 구현한 객체들이 있다.     
여기서 만약 디스플레이 할 것을 추가하고 싶으면 DisplayElement,Observer을 구현한 객체 클래스를 추가하고 함수를 Override 하기만 하면 된다.      
객체를 생성하면 자동으로 생성자에서 register 함수를 호출한다.     
분명히 test.java에서는 setmeasurementsChange()만 호출했는데 바뀌고 변화를 바로 출력했다.     

### HeatIndexDisplay.java 추가
```

public class HeatIndexDisplay implements Observer, DisplayElement {
	float heatIndex = 0.0f;
	private WeatherData weatherData;

	public HeatIndexDisplay(WeatherData weatherData) {
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}

	public void update(float t, float rh, float pressure) {
		heatIndex = computeHeatIndex(t, rh);
		display();
	}
	
	private float computeHeatIndex(float t, float rh) {
		float index = (float)((16.923 + (0.185212 * t) + (5.37941 * rh) - (0.100254 * t * rh) 
			+ (0.00941695 * (t * t)) + (0.00728898 * (rh * rh)) 
			+ (0.000345372 * (t * t * rh)) - (0.000814971 * (t * rh * rh)) +
			(0.0000102102 * (t * t * rh * rh)) - (0.000038646 * (t * t * t)) + (0.0000291583 * 
			(rh * rh * rh)) + (0.00000142721 * (t * t * t * rh)) + 
			(0.000000197483 * (t * rh * rh * rh)) - (0.0000000218429 * (t * t * t * rh * rh)) +
			0.000000000843296 * (t * t * rh * rh * rh)) -
			(0.0000000000481975 * (t * t * t * rh * rh * rh)));
		return index;
	}

	public void display() {
		System.out.println("체감 온도: " + heatIndex);
	}
}
```
다음과 같이 추가하기만 하고 컴파일 하면 실행이 된다.
```
현재 상태: 온도 80.0°F, 습도 65.0%
Avg/Max/Min temperature: 80.0/80.0/80.0
예보: 날씨가 좋아질 것 같습니다.
체감 온도: 82.95535

현재 상태: 온도 82.0°F, 습도 70.0%
Avg/Max/Min temperature: 81.0/82.0/80.0
예보: 날씨가 나빠질 것 같습니다.
체감 온도: 86.90124

현재 상태: 온도 78.0°F, 습도 90.0%
Avg/Max/Min temperature: 80.0/82.0/78.0
예보: 날씨가 변하지 않을 것 같습니다.
체감 온도: 83.64967

```

### 풀 방식으로 코드 바꾸기
생각해보면 옵저버가 필요할 때마다 주제(subject)로부터 데이터를 당겨오는 방식도 있다.     
```java
public void notifyObservers() {
    for (Observer observer : observers) {
        observer.update(); //인자 없이 호출하도록 변경
    }
}
public interface Observer {
    public void update(); //매개변수 없도록 바꿔줌.
}
//CurrentConditionDisplay 클래스 코드 변경한다.
//이렇게 하면 Observer가 필요할 때마다 당겨오는 방식이다.
public void update() {
    this.temperature = weatherData.getTemperature();
    this.humidity = weatherData.getHumidity();
    display();
}
```

주요 차이점

1. 데이터 제어: 옵저버가 자신에게 필요한 데이터만 선택적으로 가져올 수 있습니다
2. 유연성: 각 옵저버는 자신이 원하는 시점에 필요한 데이터만 가져올 수 있습니다
3. 의존성: WeatherData 객체의 레퍼런스를 가지고 있어야 하지만, 대신 필요한 데이터만 선택적으로 가져올 수 있는 장점이 있습니다

- CurrentConditionDisplay는 온도와 습도만 필요할 수 있습니다
- StatisticsDisplay는 온도만 필요할 수 있습니다
- ForecastDisplay는 기압만 필요할 수 있습니다

**이렇게 각 옵저버가 자신에게 필요한 데이터만 선택적으로 가져올 수 있어서 "당겨오는 방식", 즉 풀(pull) 방식이라고 합니다.**