package frc.robot.commands.driver;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class HighSpeed extends CommandBase 
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private Robot robot;

    public HighSpeed(Robot robot){
        this.robot = robot;

    }

    @Override
    public void initialize()
    {
        
    }

    @Override
    public void execute()
    {
        robot.driver.useHighSpeed();
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
