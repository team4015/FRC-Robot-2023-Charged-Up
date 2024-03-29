package frc.robot.commands.auto;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;



public class AutoTurn extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;
  
  // CONSTANTS //
  //private double speed;
  //private double degrees;
  //private double targetAngle; 
  private boolean endCommand; 
  private Timer turnTimer; 
  private static final double AUTO_TURN_TIME = 0.00002;
  private static double angleInDegrees; 
  //private static final double TURN_SPEED = 0.5;
  
  // VARIABLES //
  
  
  
  public AutoTurn(Robot robot, double angleInDegrees)
  {
    this.robot = robot;
    this.angleInDegrees = angleInDegrees;
    //this.speed = speed;
    //this.degrees = degrees; 
    turnTimer = new Timer();
    turnTimer.reset(); 
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
   SmartDashboard.putString("Robot Mode:", "Auto Turn");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    turnTimer.start();
    double turnAngle = Math.toRadians(angleInDegrees);
    //turnAngle = Math.toRadians(angleInDegrees);
    if(turnTimer.get()>=0&&turnTimer.get()<=AUTO_TURN_TIME){
      robot.drivetrain.moveMotors(0,(turnAngle/AUTO_TURN_TIME)*robot.drivetrain.WHEEL_RADIUS);
    }
    turnTimer.stop();
    turnTimer.reset(); 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
   robot.drivetrain.stopMotors(); 
   if (SmartDashboard.getString("Robot Mode:", "").equals("Auto Turn")) {
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