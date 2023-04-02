package frc.robot.commands.auto;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.simulation.BuiltInAccelerometerSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;


public class AutoDrive extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;
  
  // CONSTANTS //
  //private double speed;
  //private double degrees;
  //private double targetAngle; 
  private boolean endCommand; 
  private Timer driveTimer; 
  private BuiltInAccelerometer accel; 
  private static final double DRIVE_TIME = 0.00002;
  private static double distanceInFeet;  
  public static boolean onChargingStation = false;  
  private static final double TILT_THRESHOLD = 1;
  //private static final double TURN_SPEED = 0.5;
  
  // VARIABLES //
  
  
  
  public AutoDrive(Robot robot, double distanceInFeet)
  {
    this.robot = robot;
    this.distanceInFeet = distanceInFeet;
    //this.speed = speed;
    //this.degrees = degrees; 
    driveTimer = new Timer();
    driveTimer.reset(); 
    accel = new BuiltInAccelerometer();
    addRequirements(robot.drivetrain);

    
    // Use addRequirements() here to declare subsystem dependencies.
    
    //addRequirements(robot.mySubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
   //targetAngle = robot.drivetrain.getGyroAngle()+degrees;
   endCommand = false; 
   SmartDashboard.putString("Robot Mode:", "AUTO DRIVE");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    driveTimer.start();
    double distance = Units.feetToMeters(distanceInFeet);
    if(driveTimer.get()>=0&&driveTimer.get()<=DRIVE_TIME){
      if(accel.getZ()>=TILT_THRESHOLD){
        onChargingStation = true;
      }
      if(!onChargingStation){
        robot.drivetrain.moveMotors((distance/DRIVE_TIME),0);
      }else{
        if(onChargingStation){
          robot.drivetrain.moveMotors((distance/DRIVE_TIME),0);
        }
      }
      if(onChargingStation&&accel.getZ()<=TILT_THRESHOLD){
        robot.drivetrain.stopMotors();
      }
    }
    driveTimer.stop();
    driveTimer.reset();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
   robot.drivetrain.stopMotors(); 
   if (SmartDashboard.getString("Robot Mode:", "").equals("AUTO DRIVE")) {
    SmartDashboard.putString("Robot Mode:", "TeleOp");
  }

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return endCommand;
  }
}
