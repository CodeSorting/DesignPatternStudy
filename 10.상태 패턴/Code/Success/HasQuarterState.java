package Success;

public class HasQuarterState implements State {
    GumballMachine gumballMachine;

    public HasQuarterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    public void insertQuarter() {
        System.out.println("동전은 한 번만 넣을 수 있습니다.");
    }

    public void ejectQuarter() {
        System.out.println("동전을 반환합니다.");
        gumballMachine.setState(gumballMachine.getNoQuarterState());
    }

    public void turnCrank() {
        System.out.println("손잡이를 돌렸습니다.");
        int winner = (int) (Math.random() * 10);
        if ((winner == 0) && (gumballMachine.count > 1)) {
            gumballMachine.setState(gumballMachine.getWinnerState());
        } else {
            gumballMachine.setState(gumballMachine.getSoldState());
        }
    }

    public void dispense() {
        System.out.println("알맹이를 내보낼 수 없습니다. 손잡이를 돌려주세요.");
    }

    public void refill() {
        // No action needed
    }
    
}
