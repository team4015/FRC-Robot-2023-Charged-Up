/* ==================================================
 * Authors: Jason Wang
 *
 * --------------------------------------------------
 * Description:
 * This class contains most of the code that handles
 * button binding, joystick reading, and command binding.
 *
 * The "operator" is the secondary drive team member that
 * controls all other robot subsystems other than the wheels.
 * These controls allow the operator to use the subsystems.
 * ================================================== */

package frc.robot.controls;

import frc.robot.Robot;

import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.commands.claw.*;
import edu.wpi.first.wpilibj.XboxController;

public class Operator
{
    // CONSTANTS //
    
    

    // VARIABLES //
    
    Robot robot;

    // JOYSTICKS / CONTROLLERS //
    
    //private PS4Controller dualshock;
    private XboxController dualshock;


    // BUTTONS // 
    private JoystickButton clawOpen;
    private JoystickButton clawClose; 
    
    //BUTTON BINDING//

    public static final int DUALSHOCK = 2;

    public static final int CLOSE_CLAW = 4;
    public static final int OPEN_CLAW = 3;

    public static final double DEADZONE = 0.1; 
    public double dualshockSpeed = 1; 

    public Operator(Robot robot)
    {
        this.robot = robot;
        
		// instantiate joysticks / controllers
        dualshock = new XboxController(DUALSHOCK);
        // bind button objects to physical buttons
        //armIn = new JoystickButton(dualshock, ARM_IN);
        //armOut = new JoystickButton(dualshock, ARM_OUT);

        clawOpen = new JoystickButton(dualshock, OPEN_CLAW);
        clawClose = new JoystickButton(dualshock, CLOSE_CLAW);
        // bind buttons to commands
        //armIn.whileTrue(new ArmReverse(robot));
        //armOut.whileTrue(new ArmExtend(robot));
        
        clawOpen.whileTrue(new ClawOpen(robot));
        clawClose.whileTrue(new ClawClose(robot));

    }
    public double getDualshockArm(){
        double dualShockArmValue = dualshock.getLeftY();
        if(Math.abs(dualShockArmValue) <DEADZONE) return 0;
        dualShockArmValue *= dualshockSpeed;
        //System.out.println(-dualShockValue);
        return dualShockArmValue;
    }

    public double getDualshockRack(){
        double dualShockRackValue = dualshock.getRightY();
        if(Math.abs(dualShockRackValue)<DEADZONE) return 0;
        dualShockRackValue *= dualshockSpeed; 
        return dualShockRackValue; 
    }

    /*public double getDualshock(){
        double dualShockValue = dualshock.getY();
        if(Math.abs(dualShockValue) <DEADZONE) return 0;
        dualShockValue *= dualshockSpeed;
        //System.out.println(dualShockValue);
        return dualShockValue;
    }*/
       // METHODS

    // Add methods here which return values for various robot controls by reading the controllers.

}
