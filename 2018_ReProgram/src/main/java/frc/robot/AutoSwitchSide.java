package frc.robot;

import edu.wpi.first.wpilibj.Timer; 
//import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

import frc.robot.DriveTrain;

public class AutoSwitchSide extends Command{
	//Character variables for determining which side the field elements are
	char switchSide = 0;
	char scaleSide = 1;
	
	//Character variables for determining which side the robot starts on
	char fromSide;
	static char fromLeft = 0;
	static char fromRight = 1;
	
	//Defines turn variables
	static double turnRight = 90;
	static double turnLeft = -90;
	
	//Timers for movement
	private Timer m_timer = new Timer();
	
	//Boolean Variables for determining switch/scale side
	boolean rightSwitch = (switchSide == 'R' && fromSide == fromRight);
	boolean leftSwitch = (switchSide == 'L' && fromSide == fromLeft);
	boolean rightScale = (scaleSide == 'R' && fromSide == fromRight);
	boolean leftScale = (scaleSide == 'L' && fromSide == fromLeft);
	
	//Variables used for autonomous code ordering
	boolean brakeStat = true;		//Defines the state of the brake
	boolean eleUp = false;			//Defines the state of the Elevator
	boolean inSwitchPos = false;	//Defines if the robot is in position for switch deposit
	boolean switchTurn = false;		//Defines if the robot is turned to face the switch
	boolean againstSw = false;
	boolean cubeDeposit = false;
	
	//Allows for the input of the "fromSide" variable from the smartdashboard
	AutoSwitchSide(char fromSide) {
		this.fromSide = fromSide;	//defines the variable "fromSide" for this code as the variable returned from the smartdashboard
	}

	public void initialize() {
		m_timer.reset();
		m_timer.start();
		
		//Returns the sides for the scale and switch
		String gameData;
		
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if(gameData.length() > 0){
			switchSide = gameData.charAt(0);
			scaleSide = gameData.charAt(1);
		}
		//checks if the scale or switch is on the same side as the robot, 
		if((switchSide == 'L' && fromSide == fromLeft) || (switchSide == 'R' && fromSide == fromRight)){
			Elevator.brakeOff();
			brakeStat = false;
		} 
		else {
			brakeStat = true;
		}
		
	}
	
	public void execute() {
		if ((switchSide == 'L' && fromSide == fromLeft) || (switchSide == 'R' && fromSide == fromRight)){
			//lift elevator if either Switch or Scale is on the same side
			if (m_timer.get() < 1.5 && brakeStat == false) {
				Elevator.elemethod(0.55); 
				brakeStat = false;
				eleUp = false;
			} 
			else {
				Elevator.elemethod(0.0); 
				Elevator.brakeOn();
				brakeStat = true;
				eleUp = true;
			}
			//If the elevator is up, drive forward until the timer reaches 5.5 seconds
			if (m_timer.get() < 5.25 && eleUp == true) {
				DriveTrain.drive(0.7, 0.0);
				inSwitchPos = false;
			} else if (eleUp == true){
				DriveTrain.stop();
				inSwitchPos = true;
			}
			//If the switch is on the same side as the robot (left) and the previous actions are complete, then turn to the switch
			if (m_timer.get() < 6 && (switchSide == 'L' && fromSide == fromLeft) && (eleUp == true && inSwitchPos == true)) {
					DriveTrain.tankDrive(0.75, -0.75);
					switchTurn = false;			
			//If the switch is on the same side as the robot (right) and the previous actions are complete, then turn to the switch
			} else if (m_timer.get() < 6 && (switchSide == 'R' && fromSide == fromRight) && (eleUp == true && inSwitchPos == true)) {
					DriveTrain.tankDrive(-0.75, 0.75);
					switchTurn = false;	
			} else if(eleUp == true && inSwitchPos == true){
				DriveTrain.stop();
				switchTurn = true;
			}
			if(m_timer.get() < 8 && (switchTurn == true && eleUp == true)) {
				DriveTrain.drive(0.65, 0);
				againstSw = false;
				System.out.println("Driving forward");
			} else if(switchTurn == true && eleUp == true){
				DriveTrain.stop();
				againstSw = true;
			}
			if(m_timer.get() < 8.5 && (againstSw = true && eleUp == true && switchTurn == true)) {
				Gripper.wheelGripIn();
				cubeDeposit = false;
			} else if(againstSw = true && eleUp == true) {
				Gripper.wheelGripReset();
				cubeDeposit = true;
			}
		}
		//If neither switch or scale are on the same side as the robot, drive forward and stop
		else if((switchSide == 'L' && fromSide == fromRight) || (switchSide == 'R' && fromSide == fromLeft)){
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
