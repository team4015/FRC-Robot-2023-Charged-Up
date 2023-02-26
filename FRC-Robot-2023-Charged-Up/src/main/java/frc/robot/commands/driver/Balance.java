package frc.robot.commands.driver;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Balance extends CommandBase 
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private Robot robot;

    public Balance(Robot robot){
        this.robot = robot;
        addRequirements(robot.drivetrain);

    }

    @Override
    public void initialize()
    {
        
    }

    @Override
    public void execute()
    {
        robot.drivetrain.checkForTilt(robot.driver.getThrottle(),robot.driver.getSteer());
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override 
    public boolean isFinished()
    {
        return true;
    }
}

