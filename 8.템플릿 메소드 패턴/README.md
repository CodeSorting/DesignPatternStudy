## Chapter 8

### 커피와 홍차 만들기
커피와 홍차의 공통점은 카페인이 있다는 점이다.

또, 커피와 홍차는 매우 비슷한 방법으로 만들어진다. 물을 끓이고 커피(찻잎)을 우리고 컵에 따르고 설탕(레몬)을 추가한다.

Coffee,Tea 클래스를 한 번 만들어보자. 아마 다음과 같이 될 것이다.

```java
//Coffee 클래스
public class Coffee {
    void prepareRecipe() {
        boilWater();
        brewCoffeeGrinds();
        pourInCup();
        addSugarAndMilk();
    }
    public void boilWater() {
        System.out.println("물 끓이기");
    }
    public void brewCoffeeGrinds() {
        System.out.println("필터로 커피 우려내기");
    }
    public void pourInCup() {
        System.out.println("컵에 따르기");
    }
    public void addSugarAndMilk() {
        System.out.println("설탕,우유 추가하기");
    }
}

//Tea 클래스
public class Tea {
    void prepareRecipe() {
        boilWater();
        steepTeaBag();
        pourInCup();
        addLemon();
    }
    public void boilWater() {
        System.out.println("물 끓이기");
    }
    public void steepTeaBag() {
        System.out.println("찻잎 우려내기");
    }
    public void pourInCup() {
        System.out.println("컵에 따르기");
    }
    public void addLemon() {
        System.out.println("레몬 추가하기");
    }
}
```

boilWater,pourInCup은 중복된다. 또 제조법의 알고리즘이 똑같다.

Coffee, Tea 클래스를 추상 클래스 CaffeineBeverage 에서 추상 메서드로 만들고 그 둘을 상속하는 걸 쓰면 중복되는 내용을 줄일 수 있다.

이를 prepareRecipe()라는 추상 메서드로 만들면 된다.
```java
public abstract class CaffeineBeverage {
    final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        addCondiments();
    }
    abstract void brew();
    abstract void addCondiments();
    public void boilWater() {
        System.out.println("물 끓이기");
    }
    public void pourInCup() {
        System.out.println("컵에 따르기");
    }
}

public class Tea extends CaffeineBeverage {
    @Override
    void brew() {
        System.out.println("찻잎 우려내기");
    }

    @Override
    void addCondiments() {
        System.out.println("레몬 추가하기");
    }    
}

public class Coffee extends CaffeineBeverage {
    @Override
    void brew() {
        System.out.println("필터로 커피를 우려내는 중");
    }

    @Override
    void addCondiments() {
        System.out.println("설탕, 우유 추가하기");
    }    
}
```

조금 다른 방식으로 구현해야 하는 부분이 있지만, 알고리즘 절차가 비슷하므로 제조법을 일반화해서 베이스 클래스에 넣었다. 일부 단계의 메소드는 서브 클래스에 의존하였다.

CaffeineBeverage 추상 클래스의 prepareRecipe()를 보면 일종의 템플릿(틀 역할하는) 메소드가 있다.

템플릿 내엥서 알고리즘의 각 단계는 메소드로 표현된다. 어떤 메소드는 이 클래스에서, 어떤 메소드는 서브클래스에서 처리한다.

서브 클래스에서 구현해야하는 메소드는 abstract로 선언한다.

### 템플릿 메소드 정의
템플릿 메소드 패턴은 알고리즘의 골격을 정의한다. 템플릿 메소드를 사용하면 알고리즘의 일부 단계를 서브 클래스에서 구현할 수 있으며, 알고리즘의 구조는 그대로 유지하면서 알고리즘의 특정 단계를 서브 클래스에서 재정의할 수도 있다.

한마디로 알고리즘의 템플릿(틀)을 만드는 패턴이다.

### hook(후크) 알아보기
hook는 추상 클래스에서 선언되지만 기본적인 내용만 구현되어 있거나 아무 코드도 들어가지 않은 메소드이다. 이러면 서브클래스는 다양한 위치에서 알고리즘에 끼어들 수 있다.

```java
//CaffeineBeverageWithHook.java
public abstract class CaffeineBeverageWithHook {
    final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        if (customerWantsCondiments()) {
            addCondiments();
        }
    }
    abstract void brew();
    abstract void addCondiments();
    public void boilWater() {
        System.out.println("물 끓이기");
    }
    public void pourInCup() {
        System.out.println("컵에 따르기");
    }
    boolean customerWantsCondiments() {
        return true; // 기본 구현은 condiments를 추가하도록 설정
    }
}

//CoffeeWithHook.java
public class CoffeeWithHook extends CaffeineBeverageWithHook {
    @Override
    void brew() {
        System.out.println("필터로 커피를 우려내는 중");
    }

    @Override
    void addCondiments() {
        System.out.println("설탕, 우유 추가하기");
    }

    //hook은 서브클래스에서 오버라이드 해서 구체적으로 구현한다.
    @Override
    boolean customerWantsCondiments() {
        String answer = getUserInput();
        return answer.toLowerCase().startsWith("y") || answer.toLowerCase().startsWith("Y");
    }

    private String getUserInput() {
        System.out.print("커피에 추가할까요? (y/n): ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }    
}
```

### 할리우드 원칙 (Hollywood Principle)
고수준 구성 요소가 저수준 구성 요소에 의존하고, 저수준 구성 요소는 고수준 구성 요소에 의존하고 그 고수준 구성 요소는 다시 또 다른 구성 요소에 의존하는 식으로 의존성이 복잡하게 꼬여있는 것을 의존성 부패라고 한다.

할리우드 원칙은 이러한 **의존성 부패(dependency rot)**를 방지하기 위한 원칙이다.

할리우드 원칙을 적용하면, 저수준 구성 요소에서 고수준 구성 요소를 직접 호출할 수 없게 하고, 고수준 구성 요소가 저수준 구성 요소를 직접 호출 하는 것은 허용한다.

(할리우드에서 면접관이 “먼저 연락하지 마세요. 연락 드릴게요”라고 하는 것과 같아서 이름이 붙여졌다고 한다.)

### 자바 API 속 템플릿 메서드 패턴 알아보기
**정렬 알고리즘**

```java
public static void sort(Object[] a) {
	Object aux[] = (Object[])a.clone();
	mergeSort(aux, a, 0, a.length, 0);
}

// 템플릿 메소드
private static void mergeSort(Object src[], Object dest[],int low, int high, int off) {
    
    //많은 코드들...

	for (int i=low; i<high; i++){
		// Comparable 인터페이스를 통해 compareTo() 메소드 호출
		for (int j=i; j>low && ((Comparable)dest[j-1]).compareTo((Comparable)dest[j])>0; j--){
			swap(dest, j, j-1);
		}
	}

    //많은 코드들...

}
```

Arrays에 있는 정렬용 템플릿 메소드에서 알고리즘은 제공하지만, 비교 방법은 compareTo() 메소드로 구현해야 한다.

위에 템플릿 메소드 패턴에서처럼 서브 클래스를 사용하지 않았는데, **여기서는 모든 배열에서 sort()를 쓸 수 있도록 정적 메소드로 만들었다.**

알고리즘의 단계는 정적 메소드인 mergeSort()에 구현되어있고, 정렬 알고리즘 단계 중 하나인 비교는 배열의 원소에서 구현(compareTo) 하므로 템플릿 메소드 패턴임을 알 수 있다.

**각 원소들이 compareTo() 메소드를 구현했는지 알기 위해** compareTo()만을 가지고 있는 Comparable 인터페이스를 도입하여 이 인터페이스를 구현하면 정렬을 사용할 수 있다.

