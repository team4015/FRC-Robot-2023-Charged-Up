package frc.robot.commands.arm;

import frc.robot.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmMove extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;
  
  // CONSTANTS //
  
  
  
  // VARIABLES //
  
  
  
  public ArmMove(Robot robot)
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
    robot.arm.stopArm(); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  { 
    //System.out.println(robot.operator.getDualshock());
    robot.arm.moveArm(robot.operator.getDualshockArm());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    robot.arm.stopArm();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}
