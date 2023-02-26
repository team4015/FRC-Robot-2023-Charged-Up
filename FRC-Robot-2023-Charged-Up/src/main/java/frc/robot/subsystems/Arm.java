package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.motorcontrol.Spark; 

public class Arm extends SubsystemBase
{
  // CONSTANTS //
  public static final double ARM_SPEED = 1;
  public static final double ARM_REVERSE_SPEED= -1;
  
  // declare constant variables here (make them private static)
  
  // VARIABLES //
  private boolean deployed;
  private boolean extending;  
  
  // declare variables here
  
  // HARDWARE //
  private Spark armMotor;
  private DoubleSolenoid armPiston;
  
  // declare electrical hardware here
  public static final int ARM_PISTON_DEPLOY = 3;
  public static final int ARM_PISTON_RETRACT = 4;
  public static final int ARM_JAGUAR = 5; 
  
  // CONSTRUCTORS //
  
  public Arm()
  {
    armMotor = new Spark(ARM_JAGUAR);
    armPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,ARM_PISTON_DEPLOY,ARM_PISTON_RETRACT);
    deployed = false;
    extending = false;
    // instantiate all electrical hardware and variable here  
  }

  public boolean isDeployed(){
    return deployed; 
  } 
  
  public boolean isExtending(){
    return extending; 
  }    

  public void deploy()
  {
    armPiston.set(Value.kForward);
    deployed=true;
  }

  public void retract()
  {
    armPiston.set(Value.kReverse);
    deployed = false;
  }

  public void stop()
  {
    armMotor.set(0);
    extending = false; 
  }
  public void extend()
  {
    if(deployed){
        armMotor.set(ARM_SPEED);
        extending = true;
    }
  }

  public void reverse()
  {
    if(deployed)
    {
        armMotor.set(ARM_REVERSE_SPEED);
    }
  }
  // METHODS //
  
  // write your subsystem methods here
}
