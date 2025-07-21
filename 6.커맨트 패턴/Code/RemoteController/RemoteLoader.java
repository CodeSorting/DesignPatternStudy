package RemoteController;


public class RemoteLoader {
	public static void main(String[] args) {
        RemoteControlWithUndo remoteControl = new RemoteControlWithUndo();
        CeilingFan ceilingFan = new CeilingFan("Living Room");
        CeilingFanHighCommand ceilingFanHigh = new CeilingFanHighCommand(ceilingFan);
        CeilingFanMediumCommand ceilingFanMedium = new CeilingFanMediumCommand(ceilingFan);
        CeilingFanOffCommand ceilingFanOff = new CeilingFanOffCommand(ceilingFan);

        remoteControl.setCommand(0, ceilingFanMedium, ceilingFanOff);
        remoteControl.setCommand(1, ceilingFanHigh, ceilingFanOff);
        System.out.println(remoteControl);

        remoteControl.onButtonWasPushed(0); //medium 설정
        remoteControl.offButtonWasPushed(0); //선풍기 끄기
        System.out.println(remoteControl);
        remoteControl.undoButtonWasPushed(); //작업 취소
        
        remoteControl.onButtonWasPushed(1); //high 설정
        System.out.println(remoteControl);
        remoteControl.undoButtonWasPushed(); //작업 취소
    
        Command[] partyOn = { 
            new LightOnCommand(new Light("Living Room")), 
            new CeilingFanHighCommand(ceilingFan), 
        };
        Command[] partyOff = { 
            new LightOffCommand(new Light("Living Room")), 
            new CeilingFanOffCommand(ceilingFan), 
        };
        MacroCommand partyOnMacro = new MacroCommand(partyOn);
        MacroCommand partyOffMacro = new MacroCommand(partyOff);
        remoteControl.setCommand(2, partyOnMacro, partyOffMacro); //리모컨에 설정
        System.out.println(remoteControl);
        remoteControl.onButtonWasPushed(2); //파티 모드 켜기
        remoteControl.offButtonWasPushed(2); //파티 모드 끄기
    }

}