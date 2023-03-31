package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.motorcontrol.Victor;
//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;

//import edu.wpi.first.apriltag.jni.*;

/*import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;*/

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends SubsystemBase
{
  // CONSTANTS //

  
  // declare constant variables here (make them private static)
  
  // VARIABLES //
  
  

  
  // HARDWARE //
  private final Victor rightMotor;
  private final Victor leftMotor; 
  /*private final Victor rightFrontMotor;
  private final Victor rightBackMotor; 
  private final Victor leftFrontMotor;
  private final Victor leftBackMotor;*/
  private DifferentialDrive drive;

  private ADXRS450_Gyro gyro;
  private final BuiltInAccelerometer accel;
  private Timer timer; 
  private Timer driveStraightTimer; 

   // declare variables here
   private double currentAngle;
   private double currentGyroAngle;
   private double offSetAngle;
   private boolean tilting;
   private double timeElapsed; 

   //private final DifferentialDriveOdometry odometry; 
  // declare electrical hardware here (PORTS)
  public static final int RIGHT_MOTOR =2 ; 
  public static final int LEFT_MOTOR = 1;
  //public static final int RIGHT_FRONT_MOTOR = 1 ;
  //public static final int RIGHT_BACK_MOTOR=2;
  //public static final int LEFT_FRONT_MOTOR = 3;
  //public static final int LEFT_BACK_MOTOR = 4;
  //ports for encoders
  private final static double ACCEL_TO_CENTRE_DIST = 0.305;//in meters 
  private final static double ACCEL_ANGLE = 66.9; //in degrees
  private final static double COS_OF_ACCEL_ANGLE = Math.cos(Math.toRadians(ACCEL_ANGLE));  

  // CONSTANTS // 
  public static final double WHEEL_RADIUS =  Units.inchesToMeters(6)/2;
  public static double distanceFromStartingPos = 0; 
  public final static double GEAR_RATIO = 1/10.71;
  public final static double driveStraightCorrectTime = 0.001; 
  
  // CONSTRUCTORS //
  
  public Drivetrain()
  {
    // instantiate all electrical hardware and variable here
    rightMotor = new Victor(RIGHT_MOTOR);
    leftMotor = new Victor(LEFT_MOTOR);
    leftMotor.setInverted(true);

    distanceFromStartingPos = 0;
    currentAngle = 0;
    currentGyroAngle = 0;
    offSetAngle = 0;
    tilting = false; 

    /*leftFrontMotor = new Victor(LEFT_FRONT_MOTOR);
    leftBackMotor = new Victor(LEFT_BACK_MOTOR);
    rightFrontMotor = new Victor(RIGHT_FRONT_MOTOR);
    rightBackMotor = new Victor(RIGHT_BACK_MOTOR);*/

    //MotorControllerGroup leftMCG = new MotorControllerGroup(leftFrontMotor, leftBackMotor);
    //MotorControllerGroup rightMCG = new MotorControllerGroup(rightFrontMotor, rightBackMotor);
    
    //leftMotor.setInverted(true);
    //rightMotor.setInverted(true);

    drive = new DifferentialDrive(leftMotor, rightMotor);
    //drive = new DifferentialDrive(leftMCG, rightMCG);
    gyro = new ADXRS450_Gyro();
    calibrateGyro();

    accel = new BuiltInAccelerometer();
    
    timer = new Timer();
    driveStraightTimer = new Timer(); 
    resetTimer(); 
    startTimer();
  }
  
  public void moveMotors(double speed, double turn){
    SmartDashboard.putNumber("Gyro Rate:", gyro.getRate());
    //drive.arcadeDrive(speed, turn);
    if(turn == 0){
      if(gyro.getAngle()>currentAngle){
        offSetAngle = gyro.getAngle()-currentAngle;
      }else if(gyro.getAngle()<currentAngle){
        offSetAngle = currentAngle-gyro.getAngle();
      }
      if(offSetAngle==0||Math.abs(offSetAngle)%360.0==0){
        drive.arcadeDrive(speed,turn);
      }else{
        driveStraightTimer.start();
        if(driveStraightTimer.get()<=driveStraightCorrectTime&&driveStraightTimer.get()>0){
          drive.arcadeDrive(0,(-Math.toRadians(offSetAngle)/driveStraightCorrectTime)*WHEEL_RADIUS);
        }
        driveStraightTimer.reset();
      }
    }else{
      drive.arcadeDrive(speed, turn);
    }
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

  public double getGyroAngle(){
    return gyro.getAngle(); 
  }

  /*public Rotation2d gyroRotation2d(){
    return gyro.getRotation2d();
  }*/

  public void resetGyroAngle(){
    currentAngle = 0;
  }

  public double reduceAngle(Double gyroAngle){
    int degreeCount = 0;
    for(int i = 1; i<=Math.abs(gyroAngle);i++){
      if(Math.abs(gyroAngle)-(180*i)>=0){
        degreeCount++; 
      }
    }
    return Math.abs(gyroAngle)-(degreeCount*Math.abs(gyroAngle));    
  }

  public double getTime(){
    return timer.get();
  }

  public void startTimer(){
    timer.start();
  }

  public void stopTimer(){
    timer.stop();
  }

  public void resetTimer(){
    timer.reset(); 
  }

  public boolean checkForTilt(){
    if(accel.getZ()!=0){
      tilting = true;     
    }else{
      tilting = false;
    }
    SmartDashboard.putBoolean("Tilting:", tilting);
    return tilting; 
  }

  @Override 
  public void periodic (){

    timeElapsed =  getTime();
    currentGyroAngle = getGyroAngle();
    double xAccel = -accel.getX();
    xAccel *= 9.8;

    double rate = gyro.getRate();
    rate = Math.toRadians(rate);

    double turningAcceleration = rate*rate*ACCEL_TO_CENTRE_DIST*COS_OF_ACCEL_ANGLE;// Thank you Lucas's physics class XDXD --> btw
                                                                              //btw you get this by taking the second derivative of rcos(angular speed*time = rad) which equals angular displacement
    xAccel+= turningAcceleration;
    
    
    SmartDashboard.putNumber("Gyro Angle:", currentGyroAngle);
    SmartDashboard.putNumber("Acceleration:", xAccel);
    

    double distanceInX = xAccel*Math.pow(timeElapsed,2);
    if(distanceFromStartingPos==0){
      if(currentGyroAngle==0){
        distanceFromStartingPos+=distanceInX;
      }else{
        distanceFromStartingPos+=distanceInX/Math.cos(currentGyroAngle); 
      }
    }else{
      if(reduceAngle(currentGyroAngle)>90){
        distanceFromStartingPos = Math.pow((Math.pow(distanceFromStartingPos,2)+Math.pow(distanceInX,2)-2*distanceInX*distanceFromStartingPos*Math.cos((90-reduceAngle(currentGyroAngle)))),0.5);
      }else if(reduceAngle(currentGyroAngle)<90){
        distanceFromStartingPos = Math.pow((Math.pow(distanceFromStartingPos,2)+Math.pow(distanceInX,2)-2*distanceInX*distanceFromStartingPos*Math.cos((90+reduceAngle(currentGyroAngle)))),0.5);
      }else{
        distanceFromStartingPos = Math.pow(((Math.pow(distanceFromStartingPos,2))+Math.pow(distanceInX,2)),0.5);
      }
    }

    SmartDashboard.putNumber("Distance from Start:", distanceFromStartingPos);
    resetGyroAngle(); 
    resetTimer();
  }

  // METHODS //
  
  // write your subsystem methods here
}
