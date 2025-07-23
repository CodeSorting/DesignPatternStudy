package Code.Iterator;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class PancakeHouseMenu implements Menu {
    List<MenuItem> menuItems;
    public PancakeHouseMenu() {
        menuItems = new ArrayList<>();
        addItem("K&B's Pancake Breakfast", "Pancakes with scrambled eggs, and toast", 2.99, true);
        addItem("Regular Pancake Breakfast", "Pancakes with fried eggs, sausage", 2.99, false);
        addItem("Blueberry Pancakes", "Pancakes made with fresh blueberries", 3.49, true);
        addItem("Waffles", "Waffles with your choice of blueberries or strawberries", 3.59, true);
    }
    public void addItem(String name, String description, double price, boolean vegetarian) {
        MenuItem menuItem = new MenuItem(name, description, price, vegetarian);
        menuItems.add(menuItem);
    }
    public Iterator<MenuItem> createIterator() {
        return menuItems.iterator();
    }
}
