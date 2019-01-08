//Created by Gwen Miller & Laila Yost :3 2018

package frc.robot;

import edu.wpi.first.wpilibj.Timer; 
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;

public class AutoCross extends Command {
	private double TimerVal;
	private boolean TimerEnd;
	
	private double UltraVal;
	private boolean UltraEnd;
	
	public static Timer AutoTimer = new Timer();
	public static AnalogInput UltraSonic = new AnalogInput(0);
	
	public static void timerStart() {
		AutoTimer.start();
	}
		
	public void execute() {
		TimerVal = AutoTimer.get();
		TimerEnd = (TimerVal == 2);
		
		UltraVal = (UltraSonic.getVoltage()/12);
		UltraEnd = (UltraVal == 0.025);
		
		DriveTrain.drive(1, 0);
		System.out.println("Move Forward");
	}
	
	public void end() {
		DriveTrain.drive(0, 0);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return (TimerEnd == false) || (UltraEnd == false);
	}
}
