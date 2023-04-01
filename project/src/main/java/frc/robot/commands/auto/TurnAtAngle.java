package frc.robot.commands.auto;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;



public class TurnAtAngle extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;
  
  // CONSTANTS //
  //private double speed;
  //private double degrees;
  //private double targetAngle; 
  private SendableChooser turnMode; 
  private boolean endCommand; 
  private Timer turnTimer; 
  private static final double AUTO_TURN_TIME = 0.00002;
  //private static final double TURN_SPEED = 0.5;
  
  // VARIABLES //
  
  
  
  public TurnAtAngle(Robot robot)
  {
    this.robot = robot;
    //this.speed = speed;
    //this.degrees = degrees; 
    turnTimer = new Timer();
    turnTimer.reset(); 
    turnMode = new SendableChooser<>();
    turnMode.addOption("Position", "Right");
    turnMode.addOption("Position", "Middle");
    turnMode.addOption("Position", "Left");
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
   SmartDashboard.putString("Robot Mode:", "Turn At Angle");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    turnTimer.start();
    double turnAngle = 0;
    if(turnMode.equals("Middle")){
      turnAngle = Math.toRadians(-90);
    }
    //turnAngle = Math.toRadians(angleInDegrees);
    while(turnTimer.get()>=0&&turnTimer.get()<=AUTO_TURN_TIME){
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
   if (SmartDashboard.getString("Robot Mode:", "").equals("Turn At Angle")) {
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

