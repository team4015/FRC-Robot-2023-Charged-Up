package frc.robot.commands.auto;
import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class DriveAtDistance extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  // CONSTANTS //
  private static final double DRIVE_SPEED = 0.5; //tbd
  
  
  // VARIABLES //
  private Robot robot;
  private double distance; 
  private Accelerometer accel;
  private boolean endCommand;
 
  
  public DriveAtDistance(Robot robot, double distance)
  {
    this.robot = robot;
    this.distance = distance; 
    accel = new BuiltInAccelerometer();
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
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    robot.drivetrain.moveMotors(DRIVE_SPEED, 0);
  }
 
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    robot.drivetrain.stopMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return endCommand;
  }
}
