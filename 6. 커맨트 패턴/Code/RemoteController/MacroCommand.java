package RemoteController;

public class MacroCommand implements Command{
    Command[] commands;
    public MacroCommand(Command[] commands) {
        this.commands = commands;
    }
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }
    public void undo() {
        for (Command command : commands) {
            command.undo();
        }
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MacroCommand: ");
        for (Command command : commands) {
            sb.append(command.getClass().getName() + " ");
        }
        return sb.toString();
    }    
}
