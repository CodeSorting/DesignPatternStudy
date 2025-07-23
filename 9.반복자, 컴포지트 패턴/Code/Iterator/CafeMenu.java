package Code.Iterator;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class CafeMenu implements Menu {
    Map<String,MenuItem> menuItems = new HashMap<String,MenuItem>();

    public CafeMenu() {
        addItem("베지 버거와 에어 프라이", "통밀빵에 신선한 야채와 에어 프라이 감자", true, 3.99);
        addItem("오늘의 스프", "신선한 야채로 만든 오늘의 스프", false, 3.69);
        addItem("베지 샐러드", "신선한 야채와 드레싱", true, 2.99);
    }
    public void addItem(String name, String description, boolean vegetarian, double price) {
        MenuItem menuItem = new MenuItem(name, description, price, vegetarian);
        menuItems.put(name, menuItem);
    }
    public Iterator<MenuItem> createIterator() {
        return menuItems.values().iterator();
    }
}