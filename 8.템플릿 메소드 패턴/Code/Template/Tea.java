package Template;
//package Code;

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
