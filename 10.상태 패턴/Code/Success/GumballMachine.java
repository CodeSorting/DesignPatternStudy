package Success;

public class GumballMachine {
    State noQuarterState;
    State hasQuarterState;
    State soldOutState;
    State soldState;
    State winnerState;

    State state = soldOutState;
    int count = 0;

    public GumballMachine(int numberGumballs) {
        noQuarterState = new NoQuarterState(this);
        hasQuarterState = new HasQuarterState(this);
        soldOutState = new SoldOutState(this);
        soldState = new SoldState(this);
        winnerState = new WinnerState(this);

        this.count = numberGumballs;
        if (numberGumballs > 0) {
            state = noQuarterState;
        }
    }
    public void insertQuarter() {
        state.insertQuarter();
    }
    public void ejectQuarter() {
        state.ejectQuarter();
    }
    public void turnCrank() {
        state.turnCrank();
        state.dispense();
    }
    public void setState(State state) {
        this.state = state;
    }
    void releaseBall() {
        System.out.println("알맹이를 내보냅니다...");
        if (count != 0) {
            count--;
        }
    }
    public State getNoQuarterState() {
        return noQuarterState;
    }
    public State getHasQuarterState() {
        return hasQuarterState;
    }
    public State getSoldOutState() {
        return soldOutState;
    }
    public State getSoldState() {
        return soldState;
    }
    public State getWinnerState() {
        return winnerState;
    }
    public int getCount() {
        return count;
    }
    public void refill(int count) {
        this.count += count;
        System.out.println("GumballMachine에 " + count + "개의 알맹이를 추가했습니다.");
        state.refill();
        if (this.count > 0) {
            state = noQuarterState;
        }
    }
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("\n주식회사 Gumball의 자바로 만든 1.0 버전\n");
        result.append("남은 알맹이의 개수: " + count + "\n");
        result.append("현재 상태: " + state + "\n");
        return result.toString();
    }
    public State getState() {
        return state;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public void setNoQuarterState(State noQuarterState) {
        this.noQuarterState = noQuarterState;
    }
    public void setHasQuarterState(State hasQuarterState) {
        this.hasQuarterState = hasQuarterState;
    }
    public void setSoldOutState(State soldOutState) {
        this.soldOutState = soldOutState;
    }
    public void setSoldState(State soldState) {
        this.soldState = soldState;
    }
    public void setWinnerState(State winnerState) {
        this.winnerState = winnerState;
    }
    public void refill() {
        System.out.println("알맹이를 보충합니다.");
        count = 10; // 예시로 10개의 알맹이를 보충
        state.refill();
        if (count > 0) {
            state = noQuarterState;
        } else {
            state = soldOutState;
        }
    }
    public void releaseGumball() {
        System.out.println("알맹이를 내보내는 중입니다...");
        if (count > 0) {
            count--;
        } else {
            System.out.println("알맹이가 모두 소진되었습니다.");
        }
    }
    public int getGumballCount() {
        return count;
    }
}
