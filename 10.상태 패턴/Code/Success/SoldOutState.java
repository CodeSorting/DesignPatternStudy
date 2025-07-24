package Success;

public class SoldOutState implements State {
    GumballMachine gumballMachine;

    public SoldOutState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    public void insertQuarter() {
        System.out.println("알맹이가 소진되었습니다. 동전을 넣을 수 없습니다.");
    }

    public void ejectQuarter() {
        System.out.println("동전을 넣지 않았습니다.");
    }

    public void turnCrank() {
        System.out.println("알맹이가 소진되었습니다. 손잡이를 돌릴 수 없습니다.");
    }

    public void dispense() {
        System.out.println("알맹이가 소진되었습니다. 알맹이를 내보낼 수 없습니다.");
    }

    public void refill() {
        System.out.println("알맹이를 보충합니다.");
        gumballMachine.refill();
        gumballMachine.setState(gumballMachine.getNoQuarterState());
    }
    
}
