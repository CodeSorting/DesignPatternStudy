package Success;

public class WinnerState implements State {
    GumballMachine gumballMachine;

    public WinnerState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    public void insertQuarter() {
        System.out.println("동전을 넣었습니다.");
    }

    public void ejectQuarter() {
        System.out.println("동전을 넣지 않았습니다.");
    }

    public void turnCrank() {
        System.out.println("손잡이를 돌렸습니다. 알맹이를 받으세요!");
        gumballMachine.releaseGumball();
        if (gumballMachine.getCount() > 0) {
            gumballMachine.setState(gumballMachine.getNoQuarterState());
        } else {
            gumballMachine.setState(gumballMachine.getSoldOutState());
        }
    }

    public void dispense() {
        System.out.println("축하합니다! 추가 알맹이를 받았습니다!");
        gumballMachine.releaseGumball();
    }

    public void refill() {
        // No action needed
    }
    
}
