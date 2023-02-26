package frc.robot.commands.auto;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class AutoDrive extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private Robot robot;
    private Timer timer;
    private double speed;
    private double turn; 
    private double duration; 

  
  // CONSTANTS //
  
  
  
  // VARIABLES //
  
  
  
  public AutoDrive(Robot robot, double speed, double turn, double duration)
  {
    this.robot = robot;
    timer = new Timer();
    this.speed = speed;
    this.turn = turn;
    this.duration = duration; 

    addRequirements(robot.drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    timer.start();
    timer.reset(); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    while(timer.get()<duration){
        robot.drivetrain.moveMotors(speed, turn);
    }
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
    return true;
  }
}

