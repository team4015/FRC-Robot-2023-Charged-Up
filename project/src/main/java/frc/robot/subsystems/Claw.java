package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Claw extends SubsystemBase
{
  // CONSTANTS //
 
  
  // declare constant variables here (make them private static)
  
  // VARIABLES //
  
  // declare variables here
  
  // HARDWARE //
  private DoubleSolenoid clawPiston;
  private boolean closed; 
  
  
  // declare electrical hardware here
  public static final int CLAW_PISTON_DEPLOY = 1;
  public static final int CLAW_PISTON_RETRACT = 2;
 
  // CONSTRUCTORS //
  
  public Claw()
  {
    // instantiate all electrical hardware and variable here
    clawPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, CLAW_PISTON_RETRACT, CLAW_PISTON_DEPLOY);
    closed = true; 
}
  public void openClaw()
  {
    clawPiston.set(Value.kForward);
    closed=false;
  }
  public void closeClaw()
  {
    clawPiston.set(Value.kReverse);
    closed = true;
  }
  // METHODS //
  
  // write your subsystem methods here
}
