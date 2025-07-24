package fail;

public class GumballMachine {
    final static int SOLD_OUT = 0;
    final static int NO_QUARTER = 1;
    final static int HAS_QUARTER = 2;
    final static int SOLD = 3;
    int state = SOLD_OUT;
    int count = 0;

    public GumballMachine(int count) {
        this.count = count;
        if (count > 0) {
            state = NO_QUARTER;
        }
    }
    public void insertQuarter() {
        if (state == HAS_QUARTER) {
            System.out.println("동전이 이미 투입되었습니다.");
        } else if (state == NO_QUARTER) {
            state = HAS_QUARTER;
            System.out.println("동전이 투입되었습니다.");
        } else if (state == SOLD_OUT) {
            System.out.println("매진되었습니다. 동전을 반환합니다.");
        } else if (state == SOLD) {
            System.out.println("알맹이가 나가는 중입니다. 잠시 기다려주세요.");
        }
    }
    public void ejectQuarter() {
        if (state == HAS_QUARTER) {
            System.out.println("동전이 반환되었습니다.");
            state = NO_QUARTER;
        } else if (state == NO_QUARTER) {
            System.out.println("동전을 투입해주세요.");
        } else if (state == SOLD_OUT) {
            System.out.println("매진되었습니다. 동전을 반환합니다.");
        } else if (state == SOLD) {
            System.out.println("알맹이가 나가는 중입니다. 동전은 반환되지 않습니다.");
        }
    }
    public void turnCrank() {
        if (state == SOLD) {
            System.out.println("알맹이가 나가는 중입니다. 잠시 기다려주세요.");
        } else if (state == NO_QUARTER) {
            System.out.println("동전을 투입해주세요.");
        } else if (state == SOLD_OUT) {
            System.out.println("매진되었습니다.");
        } else if (state == HAS_QUARTER) {
            System.out.println("손잡이를 돌렸습니다. 알맹이가 나옵니다.");
            state = SOLD;
            dispense();
        }
    }
    public void dispense() {
        if (state == SOLD) {
            System.out.println("알맹이가 나왔습니다.");
            count--;
            if (count == 0) {
                System.out.println("매진되었습니다.");
                state = SOLD_OUT;
            } else {
                state = NO_QUARTER;
            }
        } else if (state == NO_QUARTER) {
            System.out.println("동전을 투입해주세요.");
        } else if (state == SOLD_OUT) {
            System.out.println("매진되었습니다.");
        } else if (state == HAS_QUARTER) {
            System.out.println("손잡이를 돌려주세요.");
        }
    }
    //toString(), refill() 메서드
}