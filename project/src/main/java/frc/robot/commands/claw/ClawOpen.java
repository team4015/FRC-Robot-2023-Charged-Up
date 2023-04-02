package frc.robot.commands.claw;

import frc.robot.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClawOpen extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;
  
  // CONSTANTS //
  
  
  
  // VARIABLES //
  
  
  
  public ClawOpen(Robot robot)
  {
    this.robot = robot;
    addRequirements(robot.claw);
    
    // Use addRequirements() here to declare subsystem dependencies.
    
    //addRequirements(robot.mySubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {    
    robot.claw.openClaw();
    //System.out.println("claw doing opening?!?!??!");

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
    return true;
  }
}
