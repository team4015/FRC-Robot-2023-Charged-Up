package frc.robot.commands.driver;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LowSpeed extends CommandBase 
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private Robot robot;


    public LowSpeed(Robot robot){

        this.robot = robot;
    }

    @Override
    public void initialize()
    {
        
    }

    @Override
    public void execute()
    {
        robot.driver.useLowSpeed();
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

