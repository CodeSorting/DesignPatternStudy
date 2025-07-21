package RemoteController;

public class Light {
    String location;
    int level;
    public Light(String location) {
        this.location = location;
        this.level = 0;
    }
    public void on() {
        level = 100;
        System.out.println(location + " 조명이 켜졌습니다.");
    }
    public void off() {
        level = 0;
        System.out.println(location + " 조명이 꺼졌습니다.");
    }
    public void dim(int level) {
        this.level = level;
        if (level == 0) {
            off();
        } else {
            System.out.println(location + " 조명이 " + level + "%로 조정되었습니다.");
        }
    }
    public int getLevel() {
        return level;
    }
}
