package RemoteController;

public interface Command {
    void execute();
    void undo();
}
