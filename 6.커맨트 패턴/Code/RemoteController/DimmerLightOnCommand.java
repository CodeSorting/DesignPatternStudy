package RemoteController;

public class DimmerLightOnCommand implements Command {
    Light light;
    int prevLevel;

    public DimmerLightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        prevLevel = light.getLevel();
        light.dim(75); // 조명을 50%로 조정
    }

    @Override
    public void undo() {
        light.dim(prevLevel); // 이전 레벨로 되돌리기
    }
    
}
