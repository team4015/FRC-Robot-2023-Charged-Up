package frc.robot.commands.auto;
import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import edu.wpi.first.wpilibj.BuiltInAccelerometer;
//import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;

public class DriveAtDistance extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;
  // CONSTANTS //
  private static final double DISTANCE_DRIVE_TIME = 0.00002;
  private static double DRIVE_SPEED = 0; 
  
  
  // VARIABLES //
  //private double distance; 
  //private Accelerometer accel;
  private boolean endCommand;
  private Timer distanceTimer; 
  private SendableChooser distanceMode; 
 
  
  public DriveAtDistance(Robot robot)
  {
    this.robot = robot;

    distanceTimer = new Timer();
    distanceTimer.reset();
    distanceMode = new SendableChooser<>();
    distanceMode.addOption("Position(driving at distance)", "Right");
    distanceMode.addOption("Position(driving at distance)", "Middle");
    distanceMode.addOption("Position(driving at distance)", "Left");

    //accel = new BuiltInAccelerometer();
    addRequirements(robot.drivetrain);

    // Use addRequirements() here to declare subsystem dependencies.
    
    //addRequirements(robot.mySubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    endCommand = false;
    robot.drivetrain.stopMotors();
    SmartDashboard.putString("Robot Mode:", "Drive At Distance");
    DRIVE_SPEED = 0; 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    DRIVE_SPEED = 0; 
    distanceTimer.start(); 
    double distance = Units.feetToMeters(4); 
    if(distanceMode.equals("Right")){
      DRIVE_SPEED = 1; 
    }else if(distanceMode.equals("Left")){
      DRIVE_SPEED = -1;
    }
    while(distanceTimer.get()>=0&&distanceTimer.get()<=DISTANCE_DRIVE_TIME){
        robot.drivetrain.moveMotors(DRIVE_SPEED*(distance/DISTANCE_DRIVE_TIME),0);
    }
    distanceTimer.stop();
    distanceTimer.reset(); 

  }
 
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    robot.drivetrain.stopMotors();
    if (SmartDashboard.getString("Robot Mode:", "").equals("Drive At Distance")) {
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
