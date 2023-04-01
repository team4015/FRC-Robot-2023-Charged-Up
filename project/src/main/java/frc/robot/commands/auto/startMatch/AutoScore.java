package frc.robot.commands.auto.startMatch;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.auto.*; 
import frc.robot.commands.arm.ArmExtend;
import frc.robot.commands.claw.ClawOpen;

public class AutoScore extends SequentialCommandGroup {
   private Robot robot;
   public AutoScore(Robot robot){
   super(
      new ArmExtend(robot),
      new ClawOpen(robot),
      new DriveAtDistance(robot),
      new TurnAtAngle(robot),
      new AutoDrive(robot, 2)        
   );
    this.robot = robot; 
 }   
}
