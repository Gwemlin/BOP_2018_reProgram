/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//Programmed by Laila, Jadyn, Kaleb, and Gwen 2018
//Reprogrammed for VSCode by Gwen 2018

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import org.usfirst.frc.team181.robot.AutoSwitch;
//import org.usfirst.frc.team181.robot.Camera;

public class Robot extends IterativeRobot {
	//Defines variables
	private Joystick m_drivestick = new Joystick(0);
	private Joystick m_opstick = new Joystick(1);
	//private Timer m_timer = new Timer();
	
	static SendableChooser<Object> autoChooser;
	public Command autonomousCommand;
		
	DriveTrain driveTrain = new DriveTrain(m_drivestick);
	 
	@SuppressWarnings("unused")
	private Camera cam = new Camera("stream");
	
	@Override
	public void robotInit() {	
		//DriveTrain.gyro.reset();
		autoChooser = new SendableChooser<Object>();
		autoChooser.setDefaultOption("Do Nothing", new DoNothing());
		autoChooser.addOption("From Left to Side", new AutoSwitchSide(AutoSwitchSide.fromLeft));
		autoChooser.addOption("From Right to Side", new AutoSwitchSide(AutoSwitchSide.fromRight));
		autoChooser.addOption("From Right to Front", new AutoSwitch(AutoSwitch.fromRight));
		autoChooser.addOption("From Left to Front", new AutoSwitch(AutoSwitch.fromLeft));

		SmartDashboard.putData("Autonomous Chooser", autoChooser);
	}
	
	@Override
	public void autonomousInit() {
		//DriveTrain.gyro.reset();
		//m_timer.reset();
		//m_timer.start();
		autonomousCommand = (Command) autoChooser.getSelected();
		autonomousCommand.start();
		Gripper.AutoGrip();
		//Gripper.startGrip();
	}
	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void teleopInit() {
		autoChooser = new SendableChooser<Object>();
		autoChooser.setDefaultOption("Do Nothing", new DoNothing());
		/*autoChooser.addObject("Straight Drive", new AutoSwitch(AutoSwitch.Straight, AutoSwitch.fromMiddle));
		autoChooser.addObject("Switch Deposit From Left", new AutoSwitch(AutoSwitch.Switch, AutoSwitch.fromLeft));
		autoChooser.addObject("Switch Deposit From Right", new AutoSwitch(AutoSwitch.Switch, AutoSwitch.fromRight));
		autoChooser.addObject("Scale Deposit From Left", new AutoScale(AutoScale.fromLeft));
		autoChooser.addObject("Scale Deposit From Right", new AutoScale(AutoScale.fromRight));*/
		autoChooser.addOption("From Left to Side", new AutoSwitchSide(AutoSwitchSide.fromLeft));
		autoChooser.addOption("From Right to Side", new AutoSwitchSide(AutoSwitchSide.fromRight));
		autoChooser.addOption("From Right to Front", new AutoSwitch(AutoSwitch.fromRight));
		autoChooser.addOption("From Left to Front", new AutoSwitch(AutoSwitch.fromLeft));

		SmartDashboard.putData("Autonomous Chooser", autoChooser);
		//Resets the wheel grippers to no movement
		Gripper.wheelGripReset();
	}	
	
	@Override
	public void teleopPeriodic() {
		//SmartDashboard.putNumber("Gyro Value", DriveTrain.gyro.getYaw());
		//Runs drive program, taking joystick input
		DriveTrain.drive(-m_drivestick.getY(), m_drivestick.getZ());
		//Runs shift gear program
		DriveTrain.ShiftGears();
		//Runs the Brake program
		Elevator.brake();
		//Runs the "Elemethod" program	]
		Elevator.elemethod(m_opstick.getY());
		//Runs the Grip Initialize code
		Gripper.TransferGrip();
		//Runs the Grip program
		Gripper.Grip();
		//Runs the wheel grip program
		Gripper.wheelGrip();
		//Runs the encoder test
		//Encoders.testEncoder();
		//SmartDashboard.putBoolean("Encoders", (boolean) Encoders.getencoderL());
		//Camera.camera();
		
	}
	
	@Override
	public void testPeriodic() {
		//UltraSensor.calcDistance();
		//System.out.println(DriveTrain.gyro.getYaw());
		//SmartDashboard.putNumber("Gyro Value", DriveTrain.gyro.getYaw());
		//DriveTrain.turn(90);
	}	
}