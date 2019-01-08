//Created by Gwen Miller 2018
package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;

public class Gripper {
	//Defines variables
	static DoubleSolenoid GripperSol = new DoubleSolenoid(0,2,3);
	static Joystick drivestick = new Joystick(0);
	static Joystick opstick = new Joystick(1);
	
	static VictorSP cubegrab = new VictorSP(3);
	
	static boolean GripAuto = true;
	
	public static void AutoGrip() {
		GripAuto = true;
	}
	
	public static void TransferGrip() {
		if(drivestick.getRawButton(2) == true || opstick.getRawButton(2) == true) {
			GripAuto = false;
		}
	}
	
	public static void startGrip() {
		GripperSol.set(DoubleSolenoid.Value.kReverse);	//moves solenoid forward
	}
	
	public static void unGrip() {
		GripperSol.set(DoubleSolenoid.Value.kForward);	//moves solenoid backwards
	}
	
	public static void Grip() {
		if(drivestick.getRawButton(2) == true || opstick.getRawButton(2) == true) {	//if the "2" button pressed on the driver stick, run the program
			startGrip();	//runs the "unGrip" program
			System.out.println("Letting Go");
		}
		else if(drivestick.getRawButton(2) == false && opstick.getRawButton(2) == false  && GripAuto == false) {	//if the "2" button released on the driver stick, run the program
			unGrip();	//runs the "startGrip" program
			System.out.println("Gripping");
		}
	}
	
	public static void wheelGripReset() {
		cubegrab.set(0);	//changes the movement of the cube-grabbing motor to zero
	}
	
	public static void wheelGripIn() {
		cubegrab.set(1.0);	//changes the movement of the cube-grabbing motor to forward
	}
	
	public static void wheelGripOut() {
		cubegrab.set(-1.0);	//changes the movement of the cube-grabbing motor to reverse
	}
	
	public static void wheelGrip() {
		if(drivestick.getRawButton(5) == true && drivestick.getRawButton(3) == false && opstick.getRawButton(3) == false && opstick.getRawButton(5) == false) {		//if the "5" button pressed on the driver stick, run the program
			wheelGripIn();	//run the "wheelGripIn" program
		}
		if(drivestick.getRawButton(3) == true && drivestick.getRawButton(5) == false && opstick.getRawButton(3) == false && opstick.getRawButton(5) == false) {		//if the "3" button pressed on the driver stick, run the program
			wheelGripOut();		//run the "wheelGripOut" program	
		}	
		if(opstick.getRawButton(5) == true && opstick.getRawButton(3) == false && drivestick.getRawButton(5) == false && drivestick.getRawButton(3) == false) {		//if the "5" button pressed on the driver stick, run the program
			wheelGripIn();	//run the "wheelGripIn" program
		}
		if(opstick.getRawButton(3) == true && opstick.getRawButton(5) == false && drivestick.getRawButton(5) == false && drivestick.getRawButton(3) == false) {		//if the "3" button pressed on the driver stick, run the program
			wheelGripOut();		//run the "wheelGripOut" program
		}
		if(opstick.getRawButton(3) == false && opstick.getRawButton(5) == false && drivestick.getRawButton(5) == false && drivestick.getRawButton(3) == false) {	//if the "3" button released on the driver stick, run the program
			wheelGripReset();	//run the "wheelGripReset" program
		}
	}
	
}