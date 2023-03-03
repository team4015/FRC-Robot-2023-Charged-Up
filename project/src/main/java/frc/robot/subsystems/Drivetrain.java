package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;

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
  /*private final Victor rightFrontMotor;
  private final Victor rightBackMotor; 
  private final Victor leftFrontMotor;
  private final Victor leftBackMotor;*/ 
  private DifferentialDrive drive;

  private ADXRS450_Gyro gyro;
  private final BuiltInAccelerometer accel;
  private Timer timer; 

   // declare variables here
   //private double currentAngle;
   //private double offSetAngle;
   private boolean tilting;
  // declare electrical hardware here (PORTS)
  public static final int RIGHT_MOTOR =1 ; 
  public static final int LEFT_MOTOR = 2;
  /*public static final int RIGHT_FRONT_MOTOR = 1 ;
  public static final int RIGHT_BACK_MOTOR=2;
  public static final int LEFT_FRONT_MOTOR = 3;
  public static final int LEFT_BACK_MOTOR = 4;*/
  //ports for encoders
  
  // CONSTANTS // 
  public static final double WHEEL_RADIUS =  Units.inchesToMeters(6)/2;
  public static final double startingDistance = 0; 
  // CONSTRUCTORS //
  
  public Drivetrain()
  {
    // instantiate all electrical hardware and variable here
    leftMotor = new Victor(LEFT_MOTOR);
    rightMotor = new Victor(RIGHT_MOTOR);

    /*leftFrontMotor = new Victor(LEFT_FRONT_MOTOR);
    leftBackMotor = new Victor(LEFT_BACK_MOTOR);
    rightFrontMotor = new Victor(RIGHT_FRONT_MOTOR);
    rightBackMotor = new Victor(RIGHT_BACK_MOTOR);

    MotorControllerGroup leftMCG = new MotorControllerGroup(leftFrontMotor, leftBackMotor);
    MotorControllerGroup rightMCG = new MotorControllerGroup(rightFrontMotor, rightBackMotor);*/
    
    //leftMotor.setInverted(true);
    //rightMotor.setInverted(true);

    drive = new DifferentialDrive(leftMotor, rightMotor);
    gyro = new ADXRS450_Gyro();
    calibrateGyro();

    //currentAngle = 0;
    //offSetAngle = 0;
    tilting = false;

    accel = new BuiltInAccelerometer();
    
    timer = new Timer(); 
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
    timer.reset(); 
    timer.start();
    //double robotWheelSpeed = gyro.getRate()*WHEEL_RADIUS;
    //double currentDistance = startingDistance+(robotWheelSpeed*(timer.get())); 
    //double updatedDistance = do some math/ test and graph for data 

    // wheel diamter
    //given: wheel diameter, wheel rotation speed 
    //accel.getX();
    // 
  }
  // METHODS //
  
  // write your subsystem methods here
}
