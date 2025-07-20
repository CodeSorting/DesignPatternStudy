package RemoteController;

public class Nocommand implements Command {
    public void execute() {
        System.out.println("할당할 명령이 없습니다.");
    }
    public void undo() {
        System.out.println("취소할 명령이 없습니다.");
    }
}
