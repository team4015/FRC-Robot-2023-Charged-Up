package frc.robot.commands.auto.startMatch;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.auto.*; 
import frc.robot.commands.claw.ClawOpen;

public class AutoDriveOutOfCommunity extends SequentialCommandGroup {
   private Robot robot;
   public AutoDriveOutOfCommunity(Robot robot){
   super(
      new AutoDriveSpeed(robot)        
   );
    this.robot = robot; 
 }   
}