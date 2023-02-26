package frc.robot;

// import subsystems here
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.arm.*;
import frc.robot.commands.claw.*;

import edu.wpi.first.wpilibj.Compressor;
//import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
//import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
// import edu.wpi.first.apriltag.*;

import frc.robot.controls.*;
import frc.robot.subsystems.*;

public class Robot
{ 
  public Operator operator;
  public Driver driver;
  // CONSTANTS //
  
  // VARIABLES //
  
  // SUBSYSTEMS //
  public Compressor compressor;
  public Drivetrain drivetrain;
  public Arm arm;
  public Claw claw; 
  public Vision vision;
  
  // declare all subsystems here
  
  // CONSTRUCTORS //
  
  public Robot()
  { 
    // instantiate all subsystems
    drivetrain = new Drivetrain();
    arm = new Arm();
    claw = new Claw();
    vision = new Vision();

    compressor = new Compressor(PneumaticsModuleType.CTREPCM);
    operator = new Operator(this);
    driver = new Driver(this);

    // configure the trigger binding
    // set default commands for subsystems;
    initialize();
    setDefaultCommands();
  }


  private void initialize(){
    drivetrain.stopMotors();
    driver.useHighSpeed();
    
    arm.stop();
    arm.retract();

    claw.closeClaw();
  }

  
  private void setDefaultCommands(){
    drivetrain.setDefaultCommand(new Drive(this));
    arm.setDefaultCommand(new ArmStop(this));
    claw.setDefaultCommand(new ClawClose(this));
  }
  
  // METHODS //
  
  // additional helper methods}
  
}