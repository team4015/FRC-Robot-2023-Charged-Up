package frc.robot.commands.arm;

import frc.robot.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RackStop extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;
  
  // CONSTANTS //
  
  
  
  // VARIABLES //
  
  
  
  public RackStop(Robot robot)
  {
    this.robot = robot;
    addRequirements(robot.arm);
    
    // Use addRequirements() here to declare subsystem dependencies.
    
    //addRequirements(robot.mySubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    robot.arm.stopRack(); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    robot.arm.stopRack();    
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