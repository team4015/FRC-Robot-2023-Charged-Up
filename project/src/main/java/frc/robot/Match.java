// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; 
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser; 
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.auto.startMatch.AutoScore;



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Match extends TimedRobot
{
  //private Command m_autonomousCommand;

  private Robot robot;
  private CommandBase auto; 
  private SendableChooser<CommandBase> autoMode; 
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit()
  {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    robot = new Robot();
    SmartDashboard.putData(CommandScheduler.getInstance());
    autoMode = new SendableChooser<>();
    autoMode.setDefaultOption("Default auto mode", new AutoScore(robot));
    autoMode.addOption("Do nothing (Select ONLY when necessary", null);
    SmartDashboard.putData(autoMode);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic()
  {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    SmartDashboard.putBoolean("Has Pressure:", robot.compressor.getPressureSwitchValue());
    SmartDashboard.putNumber("Time Remaining", Timer.getMatchTime());

  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link Robot} class. */
  @Override
  public void autonomousInit()
  {
    SmartDashboard.putData(CommandScheduler.getInstance());
    //m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    auto = autoMode.getSelected(); 
    if(auto!= null){
      auto.schedule();
    }
    //// schedule the autonomous command (example)
    //if (m_autonomousCommand != null) {
    //  m_autonomousCommand.schedule();
    //}
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit()
  {
    SmartDashboard.putData(CommandScheduler.getInstance());
    if (auto != null) {
      auto.cancel();
    }
    SmartDashboard.putString("Robot Mode:", "TeleOp");
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    //if (m_autonomousCommand != null) {
    //  m_autonomousCommand.cancel();
    //}

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double time = Timer.getMatchTime(); 
    if(time < 1 && time>0) robot.claw.openClaw();
  }

  @Override
  public void testInit()
  {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
