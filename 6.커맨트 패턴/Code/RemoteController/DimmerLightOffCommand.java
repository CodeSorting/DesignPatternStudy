package RemoteController;

public class DimmerLightOffCommand implements Command {
    Light light;
    int prevLevel;
    public DimmerLightOffCommand(Light light) {
        this.light = light;
        prevLevel = 100;
    } 
    public void execute() {
        prevLevel = light.getLevel();
        light.off(); // 조명을 끔
    }
    public void undo() {
        light.dim(prevLevel); // 이전 레벨로 되돌리기
    }
}
