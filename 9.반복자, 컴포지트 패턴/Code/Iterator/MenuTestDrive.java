package Code.Iterator;

import java.util.ArrayList;
import java.util.List;

public class MenuTestDrive {
    public static void main(String[] args) {
        List<Menu> menus = new ArrayList<>();
        menus.add(new DinerMenu());
        menus.add(new PancakeHouseMenu());
        menus.add(new CafeMenu());

        Waitress waitress = new Waitress(menus);
        waitress.printMenu();
    }
}
