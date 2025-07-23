## Chapter 9

### 마을 식당과 팬케이크 하우스 합병
이제 식당을 합병해서 한곳에서 점심 메뉴를 먹을 수 있게 되었다. 그러나 문제가 생겼다.

아침에는 팬케이크 하우스 메뉴를 쓰고 점심에는 객체마을 식당 메뉴를 쓴다고 한다.<br>
메뉴 항목을 구현하는 방법은 각자 다르다.

팬케이크 하우스는 배열에 메뉴를 저장하고, 마을 식당은 ArrayList에 저장했다. 이미 많은 내용을 각자 코드에 썼고 수정하기에는 너무 양이 방대하다. 이럴 때는 어떻게 해야할까?
```java
//메뉴 아이템 클래스
public class MenuItem {
    String name;
    String description;
    double price;
    boolean vegetarian;
    //생성자 밑 getter
    public MenuItem(String name, String description, double price, boolean vegetarian) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.vegetarian = vegetarian;
    }
    public String getName() {
        return name;
    }
    //... 기타 나머지 getter
}

//팬케이크 가게 : ArrayList
public class PancakeHouseMenu {
    List<MenuItem> menuItems;
    public PancakeHouseMenu() {
        menuItems = new ArrayList<>();
        addItem("팬케이크", "맛있는 팬케이크", 2.99, true);
        addItem("베이컨과 계란", "바삭한 베이컨과 계란", 3.49, false);
        addItem("블루베리 팬케이크", "신선한 블루베리 팬케이크", 3.99, true);
    }
    public void addItem(String name, String description, double price, boolean vegetarian) {
        MenuItem menuItem = new MenuItem(name, description, price, vegetarian);
        menuItems.add(menuItem);
    }
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
    //.. 기타 많은 메소드들
}

//마을 식당 : 배열
public class DinerMenu {
    static final int MAX_ITEMS = 6;
    int numberOfItems = 0;
    MenuItem[] menuItems;
    
    public DinerMenu() {
        menuItems = new MenuItem[MAX_ITEMS];
        addItem("채식 샐러드", "신선한 채소 샐러드", 2.99, true);
        addItem("BLT 샌드위치", "베이컨, 상추, 토마토 샌드위치", 3.49, false);
        addItem("스프와 샌드위치", "오늘의 스프와 샌드위치", 3.99, true);
    }
    public void addItem(String name, String description, double price, boolean vegetarian) {
        if (numberOfItems >= MAX_ITEMS) {
            System.out.println("죄송합니다. 메뉴가 가득 찼습니다.");
        } else {
            MenuItem menuItem = new MenuItem(name, description, price, vegetarian);
            menuItems[numberOfItems] = menuItem;
            numberOfItems++;
        }
    }
    public MenuItem[] getMenuItems() {
        return menuItems;
    }
    //.. 기타 많은 메소드들
}
```

보다시피 한쪽은 리스트, 한쪽은 배열을 쓴다. 이미 특정 자료 구조를 통해 여러 메소드를 만들어서 고치기엔 여력이 부족하다.

한번 printMenu()를 작성해보자.
```java
//각각 가게에서 메뉴 가져오기
PancakeHouseMenu pancakeHouseMenu = new PancakeHouseMenu();
ArrayList<MenuItem> breakfastItems = pancakeHouseMenu.getMenuItems();

DinerMenu dinerMenu = new DinerMenu();
MenuItem[] lunchItems = dinerMenu.getMenuItems();

//메뉴 출력
for (int i=0;i<breakfastItems.size();i++) {
    MenuItem item = breakfastItems.get(i);
    System.out.println(item.getName() + ": " + item.getDescription() + " - $" + item.getPrice());
}
for (int i=0;i<lunchItems.length;i++) {
    MenuItem item = lunchItems[i];
    System.out.println(item.getName() + ": " + item.getDescription() + " - $" + item.getPrice());
}
```

위의 코드처럼 진행하면 항상 두 메뉴를 사용하고, 각 항목에서 반복 작업을 사용하려면 2개의 순환문을 써야한다.

만약 다른 구현법을 사용하는 레스토랑과 또 합병하면 3개의 순환문이 필요하다.

### 반복을 캡슐화하기
바뀌는 부분은 캡슐화하는게 좋다. 반복 처리 작업을 캡슐화해보자.

반복 작업 처리 방법을 캡슐화한 Iterator이라는 객체를 만들어서 적용시켜보면 다음처럼 가능하다.
```java
Iterator iterator = breakfastMenu.createIterator();

while (iterator.hasNext()) {
    MenuItem menuItem = iterator.next(); //next 메소드는
}
```
또 Waitress라는 클래스를 만들어 그 안에 printMenu()를 놓고 Iterator을 이용하면 다음과 같은 구조가 된다.


```
Client (클라이언트)
 └── Waitress
     └── printMenu(Iterator iterator) // 공통된 Iterator 인터페이스에만 의존하여 메뉴를 출력
 
 Aggregate (집합체: 각 메뉴 클래스)
 ├── PancakeHouseMenu (내부적으로 ArrayList<MenuItem> 사용)
 │   └── createIterator()
 │       └── PancakeHouseMenuIterator를 생성하여 반환
 │
 └── DinerMenu (내부적으로 MenuItem[] 사용)
     └── createIterator()
         └── DinerMenuIterator를 생성하여 반환

// 원래 다음과 같이 했었는데 후에 import java.util.Iterator을 사용해서 없앴다.
 Iterator (이터레이터)
 └── Iterator (인터페이스)
     ├── hasNext()
     └── next()
         │
         ├─ DinerMenuIterator (구상 이터레이터)
         │   ├── hasNext() // 배열(MenuItem[]) 순회 로직 구현
         │   └── next()
         │
         └─ PancakeHouseMenuIterator (구상 이터레이터)
             ├── hasNext() // ArrayList 순회 로직 구현
             └── next()
```

Menu 객체를 만들고 Waitress에 전달하면 알아서 각 Iterator 객체를 만들고 print 해주기 때문에 클라이언트는 내부 구조를 신경 쓰지 않아도 된다.

### 반복자 패턴의 정의
반복자 패턴(Iterator Pattern)은 컬렉션의 구현 방법을 노출하지 않으면서 집합체 내의 모든 항목에 접근하는 방법을 제공한다.

즉, 반복자 패턴을 통해 접근기능과 컬렉션 자료구조를 분리시켜서 객체화한다.

즉, 항목에 일일이 접근할 수 있게 해주는 기능을 집합체(DinerMenu)가 아닌 반복자 객체(Iterator)가 책임지게 한다.

반복자 패턴을 통해 서로 다른 구조를 가지고 있는 저장 객체에 대해서 접근하기 위해 접근 기능을 반복자(Iterator) interface로 통일 시킬 수 있다.

### 단일 책임 원칙
어떤 클래스가 바뀌는 이유는 하나뿐이어야 한다.

**즉, 하나의 클래스는 하나의 역할만 맡아야한다.**

어떤 클래스에서 맡고 있는 모든 역할은 나중에 코드 변화를 불러올 수 있다.

역할이 2개 이상 있으면 바뀔 수 있는 부분이 2개 이상이 되는 것이고, 어떤 클래스를 변경하는 이유가 두개 이상이 될 수 있다.

변경 이유가 2개 이상이되면 한 책임의 변경으로부터 다른 책임의 변경으로의 연쇄작용이 생길 수 있다.

### 응집도
클래스 또는 모듈이 특정 목적이나 역할을 얼마나 일관되게 지원하는지를 나타내는 척도이다.

어떤 모듈이나 클래스의 응집도가 높다는 것은 서로 연관된 기능이 묶여있다는 것을 뜻한다.

즉, 클래스가 2개 이상의 역할을 맡고 있는 클래스에 비해 하나만 맡고 있는 클래스가 응집도가 높다고 할 수 있다.

- 의존성 얘기가 아니다. 응집도는 얼마나 한 주제로 연관되어 있는지를 보는 것이다.

### iterable 인터페이스
자바의 모든 컬렉션 유형에서는 Iterable 인터페이스를 구현한다.

**Iterable 인터페이스 안의 iterator(),forEach(),spliterator()이 있고 Collection 인터페이스에서는 Iterable을 상속한다.**

이러한 Iterable 인터페이스를 구현하는 클래스 객체는 향상된 for문을 사용할 수 있다. (forEach() 메서드가 기본으로 포함됨.)

그러나 위의 Menu 예시를 생각해봤을 때, **자바 배열은 Iterable 인터페이스를 상속받고 있지 않기 때문에 printMenu(Iterable ~)를 통해 메소드를 고치는 것은 불가능하다.**

즉, 자바의 향상된 for문을 사용할 수 없어 Collection을 상속 받도록 바꾸는 등의 리팩터링이 필요하다.

그러나 이 부분은 범위를 벗어나니 나중에 얘기한다.

참고 : Iterable인터페이스, Collection 인터페이스와 그 구현체들외에 다양한 인터페이스 등을 모아 놓은 것을 자바 컬렉션 프레임워크라고 한다. 컬렉션 객체에서 iterator() 메소드를 통해 구상 Iterator클래스가 반환되는 것을 사용할 수 있다.

### 리팩터링 : 전체 메뉴로 놓기
pancake,diner,cafe menu를 전체 메뉴 배열로 놓고 또 DinerMenu 안에 서브 메뉴 DessertMenu가 들어가 있는 것도 필요하다고 한다.
```
Client (클라이언트)
 └── Waitress
     ├── PancakeHouseMenu
     │   ├── MenuItem: "팬케이크"
     │   ├── MenuItem: "베이컨과 계란"
     │   └── MenuItem: "블루베리 팬케이크"
     │
     ├── DinerMenu
     │   ├── Dessert Menu // 여기에 서브 메뉴로 포함시켜야함.
     │   |       ├── MenuItem: "사탕"
     │   |       ├── MenuItem: "커피"
     │   |       └── MenuItem: "마카롱"
     │   ├── MenuItem: "BLT 샌드위치"
     │   └── MenuItem: "스프와 샌드위치"
     │
     └── CafeMenu
         ├── MenuItem: "에스프레소"
         ├── MenuItem: "카푸치노"
         └── MenuItem: "라떼"
```

우리는 이를 구현하기 위해 트리 구조를 이용한다.

### 컴포지트 패턴
컴포지트 패턴(Composite Pattern)으로 객체를 트리구조로 구성해서 부분-전체 계층을 구현한다. 컴포지트 패턴을 사용하면 클라이언트에서 개별 객체와 복합 객체를 똑같은 방법으로 다룰 수 있다.

즉, 메뉴와 항목을 같은 구조에 넣어서 부분-전체 계층 구조를 생성한다.

**부분-전체 계층 구조(part-whole hierarchy)**란, 부분들이 계층을 이루고 있지만 모든 부분을 묶어서 전체로 다룰 수 있는 구조를 뜻한다. (트리에서 부모 노드와 자식 노드들의 가장 작은 부분들이 합쳐져서 트리의 전체 구조가 된다는 것을 생각하면 이해하기에 쉽다.)

컴포지트 패턴을 사용하면 객체의 구성과 개별 객체를 노드로 가지는 트리 형태의 객체 구조를 만들 수 있다.

트리와 구분되는 점은 트리에선 모든 Leaf 노드가 부모 노드가 될 수 있지만, 컴포지트 패턴에서는 Composite 객체만 Leaf 객체들을 관리하는 부모 노드가 될 수 있다는 것이다.

```
Composite (디렉터리)
 ├── MenuComponent (추상 클래스 또는 인터페이스)
 │   ├── Menu (Composite)
 │   │   ├── MenuItem (Leaf)
 │   │   └── MenuItem (Leaf)
 │   │
 │   └── MenuItem (Leaf)
 │
 ├── CompositeIterator (Iterator 구현체)
 │   └── Stack<Iterator<MenuComponent>> (구성 요소 순회)
 │
 ├── NullIterator (Iterator 구현체)
 │   └── hasNext() -> false
 │
 ├── Waitress (클라이언트)
 │   └── printMenu() (전체 트리 순회 및 출력)
 │
 └── MenuTestDrive (테스트 클라이언트)
     └── Waitress를 통해 메뉴 출력
```

MenuTestDrive.java를 보면 마치 트리처럼 복합 객체 안에 복합 객체를, 그 안에 메뉴 아이템,복합 객체를 넣고 계속 넣을 수 있다.

```java
        MenuComponent pancakeHouseMenu = 
			new Menu("PANCAKE HOUSE MENU", "Breakfast");
		MenuComponent dinerMenu = 
			new Menu("DINER MENU", "Lunch");
		MenuComponent cafeMenu = 
			new Menu("CAFE MENU", "Dinner");
		MenuComponent dessertMenu = 
			new Menu("DESSERT MENU", "Dessert of course!");
  
		MenuComponent allMenus = new Menu("ALL MENUS", "All menus combined");
  
		allMenus.add(pancakeHouseMenu);
		allMenus.add(dinerMenu);
		allMenus.add(cafeMenu);
		//all menus 안에 pancake,diner,cafe 메뉴가 있고 pancake,diner,cafe 메뉴 안에 각각의 메뉴 아이템이 있다.
        ..... 

		dinerMenu.add(new MenuItem(
			"Pasta",
			"Spaghetti with marinara sauce, and a slice of sourdough bread",
			true, 
			3.89));
   
		dinerMenu.add(dessertMenu); //diner 안에 dessert 메뉴 추가
  
		dessertMenu.add(new MenuItem(
			"Apple Pie",
			"Apple pie with a flakey crust, topped with vanilla icecream",
			true,
			1.59));
		dessertMenu.add(new MenuItem(
			"Cheesecake",
			"Creamy New York cheesecake, with a chocolate graham crust",
			true,
			1.99));
		dessertMenu.add(new MenuItem(
			"Sorbet",
			"A scoop of raspberry and a scoop of lime",
			true,
			1.89));

		cafeMenu.add(new MenuItem(
			"Veggie Burger and Air Fries",
			"Veggie burger on a whole wheat bun, lettuce, tomato, and fries",
			true, 
			3.99));
```

또, 반복문을 이용해 dfs 방식(재귀나 스택 이용해야하는데 여기서는 재귀)으로 출력하고 있다.

```java    
    public void print() {
		System.out.print("\n" + getName());
		System.out.println(", " + getDescription());
		System.out.println("---------------------");
		//트리 구조를 이용해 다음과 같이 출력하기 (DFS 방식)
		Iterator<MenuComponent> iterator = menuComponents.iterator();
		while (iterator.hasNext()) {
			MenuComponent menuComponent = iterator.next();
			menuComponent.print();
		}
	}
```
