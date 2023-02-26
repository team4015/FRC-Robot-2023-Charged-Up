package frc.robot.commands.drivetrain;

import frc.robot.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Drive extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private Robot robot;
  
  // CONSTANTS //
  
  
  
  // VARIABLES //
  
  
  
  public Drive(Robot robot)
  {
    this.robot = robot; 
    addRequirements(robot.drivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
    
    //addRequirements(robot.mySubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
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
  }
}
