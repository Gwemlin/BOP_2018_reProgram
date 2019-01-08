package frc.robot;

import edu.wpi.first.wpilibj.Timer; 
//import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

import frc.robot.DriveTrain;

public class AutoSwitchScale extends Command{
	//Character variables for determining which side the field elements are
	char switchSide = 0;
	char scaleSide = 1;
	
	//Character variables for determining which side the robot starts on
	char fromSide;
	static char fromLeft = 0;
	static char fromRight = 1;
	
	//Defines turn variables
	static double rightTurn = -90;
	static double leftTurn = 90;
	
	//Timers for movement
	private Timer driveTimer1 = new Timer();
	private Timer elevTimer = new Timer();
	private Timer driveTimerSw = new Timer();
	
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
	
	//Allows for the input of the "fromSide" variable from the smartdashboard
	AutoSwitchScale(char fromSide) {
		this.fromSide = fromSide;	//defines the variable "fromSide" for this code as the variable returned from the smartdashboard
	}

	public void initialize() {
		driveTimer1.reset();
		elevTimer.reset();
		driveTimerSw.reset();
		driveTimer1.start();
		elevTimer.start();
		driveTimerSw.start();
		//Returns the sides for the scale and switch
		String gameData;
		
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if(gameData.length() > 0){
			switchSide = gameData.charAt(0);
			scaleSide = gameData.charAt(1);
		}
		//checks if the scale or switch is on the same side as the robot, 
		if(leftSwitch == true || rightSwitch == true || leftScale == true || rightScale == true){
			Elevator.brakeOff();
			brakeStat = false;
		} 
		else {
			brakeStat = true;
		}
		
	}
	
	public void execute() {
		if (leftSwitch == true || rightSwitch == true || leftScale == true || rightScale == true){
			//lift elevator if either Switch or Scale is on the same side
			if (elevTimer.get() < 1.5 && brakeStat == false) {
				Elevator.elemethod(0.55); 
				brakeStat = false;
				eleUp = false;
			} 
			else if(elevTimer.get() > 1.5){
				Elevator.elemethod(0.0); 
				Elevator.brakeOn();
				brakeStat = true;
				eleUp = true;
			}
			//If the elevator is up, drive forward until the timer reaches 5.5 seconds
			if (driveTimer1.get() < 5.5 && eleUp == true) {
				DriveTrain.drive(0.65, 0.0);
				inSwitchPos = false;
			} else if (eleUp == true){
				DriveTrain.stop();
				inSwitchPos = true;
			}
			//If the switch is on the same side as the robot (left) and the previous actions are complete, then turn to the switch
			if (leftSwitch == true && (eleUp == true && inSwitchPos == true)) {
				//DriveTrain.turn(leftTurn);
				switchTurn = false;				
			//If the switch is on the same side as the robot (right) and the previous actions are complete, then turn to the switch
			} else if (rightSwitch == true && (eleUp == true && inSwitchPos == true)) {
				//DriveTrain.turn(rightTurn);
				switchTurn = false;
			} else if(eleUp == true && inSwitchPos == true){
				DriveTrain.stop();
				switchTurn = true;
			}
			/*
			if (switchTurn = true && eleUp == true && inSwitchPos == true) {
				if (driveTimerSw.get() < 10) {
					DriveTrain.drive(0.65, 0.0);
				} else if (driveTimerSw.get() > 10){
					DriveTrain.stop();
				}
			}*/
		}
		//If neither switch or scale are on the same side as the robot, drive forward and stop
		else if((fromSide == fromRight && rightSwitch == false && rightScale == false) 
			 || (fromSide == fromLeft && leftSwitch == false && leftScale == false)){
			if (driveTimer1.get() < 4.5) {
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
