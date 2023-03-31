package frc.robot.commands.auto.startMatch;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.auto.*; 

public class AutoScore extends SequentialCommandGroup {
 private Robot robot;
 public AutoScore(Robot robot){
    this.robot = robot; 
 }   
}
