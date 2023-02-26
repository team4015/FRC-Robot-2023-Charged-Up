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

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.commands.arm.*;
import frc.robot.commands.claw.*;

public class Operator
{
    // CONSTANTS //
    
    

    // VARIABLES //
    
    Robot robot;

    // JOYSTICKS / CONTROLLERS //
    
    private Joystick dualshock;

    // BUTTONS //
    private JoystickButton armIn;
    private JoystickButton armOut; 
    private JoystickButton extendArm; 
    private JoystickButton retractArm; 
    
    private JoystickButton clawOpen;
    private JoystickButton clawClose; 
    
    //BUTTON BINDING//

    public static final int DUALSHOCK = 2;

    public static final int CLOSE_CLAW = 4;
    public static final int OPEN_CLAW = 3;

    public static final int ARM_EXTEND = 8;
    public static final int ARM_RETRACT = 7;
    public static final int ARM_IN = 6;
    public static final int ARM_OUT = 5;

    public Operator(Robot robot)
    {
        this.robot = robot;
        
		// instantiate joysticks / controllers
        dualshock = new Joystick(DUALSHOCK);
        // bind button objects to physical buttons
        armIn = new JoystickButton(dualshock, ARM_IN);
        armOut = new JoystickButton(dualshock, ARM_OUT);
        extendArm = new JoystickButton(dualshock, ARM_EXTEND);
        retractArm = new JoystickButton(dualshock, ARM_RETRACT);
       
        clawOpen = new JoystickButton(dualshock, OPEN_CLAW);
        clawClose = new JoystickButton(dualshock, CLOSE_CLAW);
        // bind buttons to commands
        armIn.whileTrue(new ArmRetract(robot));
        armOut.whileTrue(new ArmDeploy(robot));
        extendArm.whileTrue(new ArmExtend(robot));
        retractArm.whileTrue(new ArmReverse(robot));
        
        clawOpen.whileTrue(new ClawOpen(robot));
        clawClose.whileTrue(new ClawClose(robot));

    }
       // METHODS

    // Add methods here which return values for various robot controls by reading the controllers.

}
