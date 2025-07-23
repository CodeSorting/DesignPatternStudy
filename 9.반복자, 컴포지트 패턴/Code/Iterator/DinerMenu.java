package Code.Iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DinerMenu implements Menu {
    List<MenuItem> menuItems;

    public DinerMenu() {
        menuItems = new ArrayList<>();
        addItem("채식 샐러드", "신선한 채소 샐러드", 2.99, true);
        addItem("BLT 샌드위치", "베이컨, 상추, 토마토 샌드위치", 3.49, false);
        addItem("스프와 샌드위치", "오늘의 스프와 샌드위치", 3.99, true);
    }

    public void addItem(String name, String description, double price, boolean vegetarian) {
        MenuItem menuItem = new MenuItem(name, description, price, vegetarian);
        menuItems.add(menuItem);
    }

    public Iterator<MenuItem> createIterator() {
        return menuItems.iterator();
    }
}
