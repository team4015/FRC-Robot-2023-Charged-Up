/*****************************************************
 * Authors: Jason Wang
 * ---------------------------------------------------
 * Description: Contains all the basic code for controlling
 * the robot drivetrain with joysticks.
 * 
*****************************************************/


package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.motorcontrol.Victor;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

//import edu.wpi.first.apriltag.jni.*;

//import edu.wpi.first.math.geometry.Pose2d;
//import edu.wpi.first.math.geometry.Pose3d;
//import edu.wpi.first.math.geometry.Rotation2d;
//import edu.wpi.first.math.geometry.Rotation3d;

public class Drivetrain extends SubsystemBase
{
  // CONSTANTS //

  
  // declare constant variables here (make them private static)
  
  // VARIABLES //
  
  

  
  // HARDWARE //
  private final Victor rightMotor;
  private final Victor leftMotor; 
  private DifferentialDrive drive;

  private ADXRS450_Gyro gyro;
  private final BuiltInAccelerometer accel;

  // declare variables here
  //private double currentAngle;
  //private double offSetAngle;
  private boolean tilting;


  // declare electrical hardware here (PORTS)
  public static final int RIGHT_MOTOR = 2;
  public static final int LEFT_MOTOR = 1;
  // ports for encoders
  
  // CONSTRUCTORS //
  
  public Drivetrain()
  {
    // instantiate all electrical hardware and variable here
    rightMotor = new Victor(RIGHT_MOTOR);
    leftMotor = new Victor(LEFT_MOTOR);
    leftMotor.setInverted(true);

    drive = new DifferentialDrive(rightMotor, leftMotor);
    gyro = new ADXRS450_Gyro();
    calibrateGyro();

    //currentAngle = 0;
    //offSetAngle = 0;
    tilting = false;

    accel = new BuiltInAccelerometer();
    
  }
  
  public void moveMotors(double speed, double turn){
    drive.arcadeDrive(speed, turn);
    /*if(turn == 0){
      if(gyro.getAngle()>currentAngle){
        offSetAngle = gyro.getAngle()-currentAngle;
      }else if(gyro.getAngle()<currentAngle){
        offSetAngle = currentAngle-gyro.getAngle();
      }
      if(offSetAngle!=0&&!(Math.abs(offSetAngle)%360.0==0)){
        drive.arcadeDrive(speed,turn);
      }
    }else{
      drive.arcadeDrive(0, turn);
    }*/
  }

  public void stopMotors(){
    drive.stopMotor();
  }

  public void calibrateGyro(){
    resetGyro();
    gyro.calibrate();
  }

  public void resetGyro(){
    gyro.reset();
  }

  public void checkForTilt(double speed, double turn){
    if(accel.getZ()!=0){
      tilting = true; 
    }
    while(tilting){
      drive.arcadeDrive(speed, turn);
      if(accel.getZ()==0){
        tilting = false;
      }
    }
  }


  @Override
  public void periodic(){
    //accel.getX();
  }
  // METHODS //
  
  // write your subsystem methods here
}
