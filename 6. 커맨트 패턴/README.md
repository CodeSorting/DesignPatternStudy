## Chapter 6

### 만능 IOT 리모컨
기상 스테이션 프로그램이 잘 되어서 이번에 새로 개발한 home automation 리모컨의 API 디자인을 의뢰받았다.    
리모컨에는 7개의 슬롯이 있다. 각 슬롯에 원하는 제품을 연결한 다음 옆에 있는 버튼으로 On,Off를 조작할 수 있다.    
제품의 이름도 떠야 하고, 버튼의 명령을 취소하는 Undo 버튼도 있다.    
ex : 연결한 제품 이름1 , On, Off

### 협력 업체 클래스 살펴보기
TV,CeilingLight,OutdoorLight,GardenLight,Sprinkler,SecurityControl,Hottub,...<br>
클래스가 정말 많다. 그런데 공통적인 인터페이스가 있지 않다. 더 큰 문제는 앞으로 클래스가 더 추가된다는 것이다. 리모컨은 On/Off 버튼만 있는데 협력 업체의 클래스는 너무 복잡하다.<br>
메소드도 on(),off()만 있는게 아니라 dim(),setTemperature(),setVolume(),setDirection() 같은 메서드들도 많다. <br>
리모컨이 간단한 요청밖에 못한다면 작업을 요청하는 리모컨과 실제로 작업을 처리하는 쪽을 분리해야한다. <br>
이를 위해서 커맨드 객체를 추가히야 특정 작업 요처을 캡슐화하면 된다. 리모컨은  실제 작업 처리를 하는 방법은 모르고 커맨드 객체가 알아서 하게 된다.

### 요리 주문 예시
1. 고객은 원하는 주문을 주문하고 주문서를 만든다. createOrder()
2. 종업원은 takeOrder()로 주문을 받고 셰프에게 orderUp()으로 주문서를 주어 주문 처리를 요청한다.
3. 셰프는 주문서를 받고 makeBurger(),makeShake()등을 호출하여 요리를 만든다.

주문서는 주문 내용을 캡슐화한다.
종업원은 주문서를 받고 orderUp()을 호출한다.
주방장(셰프)은 식사를 준비하는데 필요한 정보를 가지고 있다.

### 식당과 커맨드 패턴
식당은 어떤 것을 요구하는 객체와 그 요구를 받아들이고 처리하는 객체를 분리하는 객체지향 디자인 패턴의 한 모델이다.

클라이언트 -> createCommandObject() -> 커맨드 -> setCommand() -> 인보커 -> execute() -> 리시버
- 고객 = 클라이언트 객체
- 주문서 = 커맨드 객체
- 종업원 = 인보커 객체
- 주방장 = 리시버 객체
- takeOrder() = setCommand()
- orderUp() = execute()

간단한 커맨드 패턴인 SimpleCommand 디렉터리 구조는 다음과 같다.
```
Command (인터페이스 : 구현체들은 execute()안에 on,off,up,down등의 함수 호출)
├── LightOnCommand (구현체)
├── LightOffCommand (구현체)
├── GarageDoorOpenCommand (구현체)
└── GarageDoorCloseCommand (구현체)

Receiver (실제 동작을 수행하는 클래스들)
├── Light
│   ├── on()
│   └── off()
└── GarageDoor
    ├── up()
    └── down()

SimpleRemoteControl (인보커)
└── setCommand()
    └── buttonWasPressed() // Command의 execute() 호출

Client
└── main() // RemoteControl과 Command 객체 생성 및 설정
```

### 커맨드 패턴의 정의
커맨드 패턴을 사용하면 요청 내역을 객체로 캡슐화해서 객체를 서로 다른 요청 내역에 따라 매개변수화할 수 있다. 이러한 요청을 큐에 저장하거나 로그로 기록하거나 작업 취소 기능을 사용할 수 있다.

### undo 기능 추가
undo 기능을 추가한 Code/RemoteControl 안의 파일 구조들은 다음과 같다.<br>
```java
Command (인터페이스)
├── NoCommand
├── LightCommand/
│   ├── LightOnCommand
│   ├── LightOffCommand
│   ├── DimmerLightOnCommand
│   └── DimmerLightOffCommand
├── CeilingFanCommand/
|   ├── CeilingFanHighCommand
|   ├── CeilingFanMediumCommand
|   ├── CeilingFanLowCommand
|   └── CeilingFanOffCommand
└── MacroCommand

Receiver (리시버)
├── Light
|   ├── dim()
|   ├── on()
|   ├── off()
|   └── getLevel()
└── CeilingFan
    ├── high()
    ├── medium()
    ├── low()
    ├── off()
    └── getSpeed()

RemoteControlWithUndo (인보커)
├── setCommand()
├── onButtonWasPushed()
├── offButtonWasPushed()
└── undoButtonWasPushed()

RemoteLoader (클라이언트)
```

1. Command: 모든 커맨드는 execute()와 undo() 메서드를 구현
- NoCommand: Null 객체 패턴으로 사용되는 빈 커맨드
- 각 기기별로 On/Off 커맨드 쌍을 가짐
- MacroCommand: 여러 커맨드를 그룹으로 실행

2. Receiver: 실제 기능을 수행하는 클래스들
- CeilingFan은 여러 속도 상태를 가짐
- 각 기기는 자신만의 고유한 기능을 가짐

3. RemoteControl: 7개의 슬롯을 가진 리모컨, 인보커
- 각 슬롯은 on/off 커맨드 쌍을 가짐
- RemoteControlWithUndo는 마지막 실행된 커맨드를 기억

4. RemoteLoader: 리모컨 초기화 및 테스트
- Receiver, Command, RemoteControl 객체들을 생성하고 연결

### 커맨드 패턴 활용하기
커맨드로 computation의 한 부분을 패키지로 묶어서(복잡한 계산 또는 행동(ex. 이메일 보내기, 파일 저장 등)을 하나의 커맨드 객체로 묶는 것) 일급 객체 형태로 전달할 수도 있다. 그러면 클라이언트 애플리케이션에서도 커맨드 객체를 생성한 뒤 오랜 시간이 지나도 그 computation을 호출할 수 있다. 심지어 다른 스레드에서도 호출할 수 있다. 이 점을 활용해 커맨드 패턴을 스케줄러나 스레드 풀, 작업 큐와 같은 다양한 작업에 적용할 수 있다.