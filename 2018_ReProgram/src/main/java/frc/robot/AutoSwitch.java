//Created by Gwen Miller & Laila Yost 2018

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Elevator;
import frc.robot.Gripper;

public class AutoSwitch extends Command {
	char autoDirection;
	char fromSide;
	
	private Timer m_timer = new Timer();
	private Timer elevTimer = new Timer();
	private Timer gripTimer = new Timer();
	private Timer backTimer = new Timer();
	
	static char fromLeft = 2;
	static char fromRight = 3;
	static char fromMiddle = 4;
	
	char SwitchSide = 0;
	
	boolean rightSwitch = (SwitchSide == 'R' && fromSide == fromRight);
	boolean leftSwitch = (SwitchSide == 'L' && fromSide == fromLeft);
	
	boolean brakeStat = true;
	boolean eleUp = false;
	boolean inPos = false;
	boolean deposit = false;
	boolean back = false;
	
	AutoSwitch(char fromSide) {
		this.fromSide = fromSide;
	}
	
	public void initialize() {
		m_timer.reset();
		m_timer.start();
		elevTimer.reset();
		elevTimer.start();
		gripTimer.reset();
		gripTimer.start();
		backTimer.reset();
		backTimer.start();
		
		eleUp = false;
		inPos = false;
		deposit = false;
		back = false;
		
		String gameData;
		
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if(gameData.length() > 0){
			SwitchSide = gameData.charAt(0);
		}

		if((SwitchSide == 'R' && fromSide == fromRight) || (SwitchSide == 'L' && fromSide == fromLeft)) {
			Elevator.brakeOff();
			brakeStat = false;
		} 
		else {
			brakeStat = true;
		}
		
	}
	
	public void execute() {
		if((SwitchSide == 'L' && fromSide == fromLeft) || (SwitchSide == 'R' && fromSide == fromRight)){
			if (m_timer.get() < 1.5 && brakeStat == false) {
				Elevator.elemethod(0.55); 
				brakeStat = false;
				eleUp = false;
			} else {
				Elevator.elemethod(0.0); 
				Elevator.brakeOn();
				brakeStat = true;
				eleUp = true;
			}
			if (m_timer.get() < 5.5 && eleUp == true) {
				DriveTrain.drive(0.65, 0.0);
				inPos = false;
			} else {
				DriveTrain.stop();
				inPos = true;
			}
			if (m_timer.get() < 6.5 && inPos == true && eleUp == true) {
				Gripper.wheelGripIn();
				deposit = false;
			} else {
				Gripper.wheelGripReset();
				deposit = true;
			}
			if (m_timer.get() < 8 && inPos == true && eleUp == true && deposit == true) {
				DriveTrain.drive(-0.65, 0);
				back = false;
			} else {
				back = true;
			}
			if ((fromSide == fromRight && SwitchSide == 'L') && inPos == true && eleUp == true && deposit == true && back == true) {
				//DriveTrain.turn(-45);
			}
			
		} 
		else if((SwitchSide == 'R' && fromSide == fromLeft) ||	(SwitchSide == 'L' && fromSide == fromRight)){
			if (m_timer.get() < 4.5) {
				DriveTrain.drive(0.65, 0.0); 
			} else {
				DriveTrain.stop();
			}
		}
			
	}
	
	public void end() {
		DriveTrain.drive(0, 0);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
