package fail;

public class GumballMachineTestDrive {
    public static void main(String[] args) {
        GumballMachine gumballMachine = new GumballMachine(5);
        System.out.println("현재 상태: " + gumballMachine.count);
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        System.out.println("현재 상태: " + gumballMachine.count);
        gumballMachine.insertQuarter();
        gumballMachine.ejectQuarter();
        gumballMachine.turnCrank();
        System.out.println("현재 상태: " + gumballMachine.count);
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        gumballMachine.ejectQuarter();
        System.out.println("현재 상태: " + gumballMachine.count);
    }   
}
