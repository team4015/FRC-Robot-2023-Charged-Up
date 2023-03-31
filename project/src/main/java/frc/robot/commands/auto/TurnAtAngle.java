package frc.robot.commands.auto;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class TurnAtAngle extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;
  
  // CONSTANTS //
  private double speed;
  private double degrees;
  private double targetAngle; 
  private boolean endCommand; 
  
  private static final double TURN_SPEED = 0.5;
  
  // VARIABLES //
  
  
  
  public TurnAtAngle(Robot robot, double speed, double degrees)
  {
    this.robot = robot;
    this.speed = speed;
    this.degrees = degrees; 

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
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    double currentAngle = robot.drivetrain.getGyroAngle();
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}

