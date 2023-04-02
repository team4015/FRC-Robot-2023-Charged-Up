/* ==================================================
 * Authors: Jason Wang
 *
 * --------------------------------------------------
 * Description:
 * This class contains most of the code that handles
 * button binding, joystick reading, and command binding.
 *
 * The "driver" is the primary drive team member that
 * drives the robot around the field.  These controls
 * are used by the main driver to move the robot's wheels.
 * ================================================== */

package frc.robot.controls;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.driver.HighSpeed;
import frc.robot.commands.driver.LowSpeed;
//import frc.robot.commands.driver.Balance;

public class Driver
{
    // CONSTANTS //
    private Joystick throttle; 
    private Joystick steer; 
    
    public static final int THROTTLE = 0;
    public static final int STEER = 1;

    // VARIABLES //
    
    // JOYSTICKS / CONTROLLERS //
    
    

    // BUTTONS //
    private JoystickButton lowSpeed;
    private JoystickButton highSpeed; 
    //private JoystickButton balance; 

    public static final double DEADZONE = 0.1; 
    public static final double THROTTLE_LOW_SPEED = 0.5; 
    public static final double THROTTLE_HIGH_SPEED = 1;
    public static final double STEER_LOW_SPEED = 0.5;
    public static final double STEER_HIGH_SPEED = 1;

    public static final int LOW_SPEED = 3;
    public static final int HIGH_SPEED = 4;
    //public static final int BALANCE = 5; 

    public double throttleSpeed = 0.7;
    public double steerSpeed = 0.6;
   

    // CONSTRUCTOR //
    
    public Driver(Robot robot)
    {
        
        // instantiate joysticks / controllers
        throttle = new Joystick(THROTTLE);
        steer = new Joystick(STEER);
        
        // bind button objects to physical buttons
        lowSpeed = new JoystickButton(throttle, LOW_SPEED);
        highSpeed = new JoystickButton(throttle,HIGH_SPEED);
        //balance = new JoystickButton(throttle,BALANCE);

        // bind buttons to commands
        lowSpeed.whileTrue(new LowSpeed(robot));
        highSpeed.whileTrue(new HighSpeed(robot));
        //balance.whileTrue(new Balance(robot));
    }

    // METHODS //
    
    public double getThrottle()
    {
        double throttleValue = throttle.getY();
        if(Math.abs(throttleValue) <DEADZONE) return 0;
        throttleValue *= throttleSpeed;
        System.out.println(throttleValue);
        return throttleValue;
    }

    public double getSteer()
    {
        double steerValue = steer.getX();
        if(Math.abs(steerValue)<DEADZONE) return 0;
        steerValue *= steerSpeed;
        System.out.println(steerValue);
        return steerValue;

    }

    public void useLowSpeed(){
        throttleSpeed = THROTTLE_LOW_SPEED;
        steerSpeed = STEER_LOW_SPEED;
        SmartDashboard.putString("Drive Speed", "LOW SPEED");
    }
    public void useHighSpeed(){
        throttleSpeed = THROTTLE_HIGH_SPEED;
        steerSpeed = STEER_HIGH_SPEED;
        SmartDashboard.putString("Drive Speed", "HIGH SPEED");
    }
	// Add methods here which return values for various robot controls by reading the controllers.
}
