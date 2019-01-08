//Created by Gwen and Laila 2018
package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;

public class Elevator {
	//Defines variables
	static Spark m_elevator = new Spark(2);
	
	static DoubleSolenoid BrakeSol = new DoubleSolenoid(0,4,5);

	public static Joystick opstick = new Joystick (1);

	public Elevator(Joystick opstick) {
		Elevator.opstick = opstick;
	}
	
	public static void elemethod(double y) {	//Defines the "Elemethod" program
		m_elevator.set(y);	//sets the input for "elemethod" to the "y" for the Spark motor-controller
	}
		
	public static void resetEncoders() { 
		
	}
	
	public static void brakeOn() {
		BrakeSol.set(DoubleSolenoid.Value.kForward);		//moves the solenoids forward

	}
	
	public static void brakeOff() {
		BrakeSol.set(DoubleSolenoid.Value.kReverse);		//moves the solenoids backward
	}	

	public static void brake() {
		double yValue = opstick.getY();
		Boolean yChange = ((yValue < -0.055) || (yValue > 0.055));
		//Auto Brake disable
		if(yChange == true) {
			brakeOff();
			System.out.println("Releasing Brake");
		}
		//Auto Brake enable
		if(yChange == false) {
			brakeOn();
			System.out.println("Engaging Brake");
		}
	}
}


