package Success;

public class SoldState implements State {
    GumballMachine gumballMachine;
    public SoldState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }
    public void insertQuarter() {
        System.out.println("알맹이를 내보내는 중입니다. 잠시 기다려주세요.");
    }
    public void ejectQuarter() {
        System.out.println("알맹이를 내보내는 중입니다. 동전을 반환할 수 없습니다.");
    }
    public void turnCrank() {
        System.out.println("손잡이를 이미 돌렸습니다. 알맹이를 내보내는 중입니다.");
    }
    public void dispense() {
        gumballMachine.releaseBall();
        if (gumballMachine.getCount() > 0) {
            gumballMachine.setState(gumballMachine.getNoQuarterState());
        } else {
            System.out.println("알맹이가 모두 소진되었습니다.");
            gumballMachine.setState(gumballMachine.getSoldOutState());
        }
    }
    public void refill() {
        System.out.println("알맹이를 보충할 수 없습니다. 알맹이가 이미 소진되었습니다.");
    }
}
