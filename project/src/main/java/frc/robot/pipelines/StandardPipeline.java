package frc.robot.commands.pipelines;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.*;
import org.opencv.imgproc.*; 
import edu.wpi.first.vision.VisionPipeline;
import frc.robot.Robot; 


public class StandardPipeline implements VisionPipeline
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private Robot robot;
  
  // CONSTANTS //
  
  
  
  // VARIABLES //
  
  
  
  public StandardPipeline(Robot robot)
  {
    this.robot = robot; 
    //addRequirements(robot.drivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
    
    //addRequirements(robot.mySubsystem);
  }

  @Override
  public void process(Mat image) {
    // TODO Auto-generated method stub
    
  }

  // Called when the command is initially scheduled.
  /*@Override
  public void initialize()
  {
    robot.drivetrain.stopMotors();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    robot.drivetrain.moveMotors(robot.driver.getThrottle(),robot.driver.getSteer());
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
    return false;
  }*/
}
