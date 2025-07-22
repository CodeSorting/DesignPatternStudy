package Hook;

import java.util.Scanner;

public class TeaWithHook extends CaffeineBeverageWithHook {
    @Override
    void brew() {
        System.out.println("찻잎 우려내기");
    }

    @Override
    void addCondiments() {
        System.out.println("레몬 추가하기");
    }

    @Override
    boolean customerWantsCondiments() {
        String answer = getUserInput();
        return answer.toLowerCase().startsWith("y") || answer.toLowerCase().startsWith("Y");
    }
    
    public String getUserInput() {
        System.out.print("차에 레몬을 추가할까요? (y/n): ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
