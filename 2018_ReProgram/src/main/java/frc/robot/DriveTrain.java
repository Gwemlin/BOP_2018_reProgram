//Created by Gwen, Laila, Jadyn, and Kaleb 2018
package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

//import e du.wpi.first.wpilibj.Encoder;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import com.kauailabs.navx.frc.AHRS;
//import edu.wpi.first.wpilibj.SPI;

public class DriveTrain {
	//Defines Variables
	static DoubleSolenoid VarSolenoid = new DoubleSolenoid(0,0,1);
	 
	static VictorSP m_frontLeft = new VictorSP(1);
	static VictorSP m_frontRight = new VictorSP(0);
	
	private static DifferentialDrive m_drive = new DifferentialDrive(m_frontLeft, m_frontRight);
	
	//static Encoder leftEncoder = new Encoder(2, 1, true, Encoder.EncodingType.k4X);
	//static Encoder rightEncoder = new Encoder(0, 3, false, Encoder.EncodingType.k4X);
	
	private static Joystick arcadestick;
	
	//public static AHRS gyro = new AHRS(SPI.Port.kMXP);
	
	public DriveTrain(Joystick arcadestick) {
		DriveTrain.arcadestick = arcadestick;
	}
	
	public static void stop() {
		//Stops the motors
		m_drive.stopMotor();
	}
	
	public static void drive(double forward, double turn) {
		//defines "drive" as an arcade drive program
		m_drive.arcadeDrive(forward, turn);
	}
	
	public static void tankDrive (double left, double right) {
		m_drive.tankDrive(left, right);
	}
	
	public static void resetEncoders() { 
		
	}
	
	public static void highGear() {
		//Defines the "highGear" program
		resetEncoders();
		VarSolenoid.set(DoubleSolenoid.Value.kForward);		//moves the solenoids forward
		//doubleSolenoid.set(true);
		resetEncoders();
	}
	
	public static void lowGear() {
		//Defines the "lowGear" program
		resetEncoders();
		VarSolenoid.set(DoubleSolenoid.Value.kReverse);		//moves the solenoids backward
		//doubleSolenoid.set();
		resetEncoders();
	}	
	
	public static void ShiftGears() {
		//If button 1 is pressed, and high gear is not yet enabled, run high gear method in DriveTrain class.
		if(arcadestick.getRawButton(1) == true){
			System.out.println("Engaging High Gear!");
			highGear();			
		}
		else{
			System.out.println("Going to Low Gear!");
			lowGear();
			}
	}
	/*
	public static void turn17(double angle) {
		//Zero out and get the Yaw of the robot from the Gyro
				for (double i = gyro.getYaw();  i >= angle+5.5 || i <= angle-5.5 ; i = gyro.getYaw()){
					
					if (i < angle){
						tankDrive(.6, -.6);
						stop();
					}
					
					if (i > angle){
						tankDrive(-.6, .6);
						stop();
					}
					
				}
				System.out.println("Stopped turning");
	}
	public static void turn(double angle) {
		double i = gyro.getYaw();
		if (i >= angle+5.5 || i <= angle-5.5) {
			if (i < angle){
				tankDrive(.6, -.6);
				stop();
			}
			
			if (i > angle){
				tankDrive(-.6, .6);
				stop();
			
			}
		}
	}
	*/
}
