package Hook;

import java.util.Scanner;

public class CoffeeWithHook extends CaffeineBeverageWithHook {
    @Override
    void brew() {
        System.out.println("필터로 커피를 우려내는 중");
    }

    @Override
    void addCondiments() {
        System.out.println("설탕, 우유 추가하기");
    }

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
