package frc.robot.commands.auto.startMatch;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.auto.*; 
import frc.robot.commands.claw.ClawOpen;

public class AutoScore extends SequentialCommandGroup {
   private Robot robot;
   public AutoScore(Robot robot){
   super(
      new AutoMoveArm(robot, 1, 2),
      new ClawOpen(robot),
      new DriveAtDistance(robot),
      new TurnAtAngle(robot),
      new LowerArm(robot, -1,1),
      new AutoMoveArm(robot, -1, 2),
      new AutoDrive(robot, 2)        
   );
    this.robot = robot; 
 }   
}
