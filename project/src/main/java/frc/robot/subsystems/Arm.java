package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.motorcontrol.Spark; 
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

public class Arm extends SubsystemBase
{
  // CONSTANTS //
  //public static final double ARM_RACK_PINION_SPEED = 1;
  //public static final double ARM_RACK_PINION_REVERSE_SPEED= -1;
  //public static final double ARM_EXTENSION_SPEED = 1; 
  //public static final double ARM_RETRACT_SPEED = -1; 

  
  // declare constant variables here (make them private static)
  
  // VARIABLES //
  private boolean deployed;
  private boolean extending;  
  
  // declare variables here
  
  // HARDWARE //
  private PWMSparkMax rackMotor;
  private Spark armMotor;
  
  // declare electrical hardware here
  public final static int ARM_EXTENSION = 3;
  public static final int ARM_RACK_PINION = 4; 
  
  // CONSTRUCTORS //
  
  public Arm()
  {
    rackMotor = new PWMSparkMax(ARM_RACK_PINION);
    armMotor = new Spark(ARM_EXTENSION);
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

  public void moveArm(double moveArmSpeed)
  {
    armMotor.set(moveArmSpeed);
    //System.out.println(moveArmSpeed);
    if(moveArmSpeed>0){
      deployed=true;
    }
    else if(moveArmSpeed<=0){
      deployed = false; 
    }
  }

  public void extendArm(double extendArmSpeed){
    rackMotor.set(extendArmSpeed);
    if(extendArmSpeed>0){
      extending=true;
    }
    else if(extendArmSpeed<=0){
      extending = false; 
    }
  }

  public void stopArm()
  {
    armMotor.set(0);
    deployed = false; 
  }

  public void stopRack()
  {
    rackMotor.set(0);
    extending = false; 
  }

  // METHODS //
  
  // write your subsystem methods here
}
