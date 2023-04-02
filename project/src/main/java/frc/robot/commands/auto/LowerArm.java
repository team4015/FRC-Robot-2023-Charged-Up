package frc.robot.commands.auto;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;



public class LowerArm extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;
  
  // CONSTANTS //
  //private double speed;
  //private double degrees;
  //private double targetAngle; 
  private boolean endCommand; 
  private Timer armTimer; 
  private static double armSpeed; 
  private static double time; 
  //private static final double TURN_SPEED = 0.5;
  
  // VARIABLES //
  
  
  
  public LowerArm(Robot robot, double armSpeed, double time)
  {
    this.robot = robot;
    this.armSpeed = armSpeed;
    this.time = time; 
    //this.speed = speed;
    //this.degrees = degrees; 
    armTimer = new Timer();
    armTimer.reset(); 
    addRequirements(robot.arm);

    
    // Use addRequirements() here to declare subsystem dependencies.
    
    //addRequirements(robot.mySubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
   //targetAngle = robot.drivetrain.getGyroAngle()+degrees;
   endCommand = false; 
   SmartDashboard.putString("Robot Mode:", "Lower Arm");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    armTimer.start();
    if(armTimer.get()>=0&&armTimer.get()<=time){
      robot.arm.moveArm(armSpeed);
    }
    armTimer.stop();
    armTimer.reset(); 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
   robot.arm.stopArm(); 
   if (SmartDashboard.getString("Robot Mode:", "").equals("Lower Arm")) {
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
