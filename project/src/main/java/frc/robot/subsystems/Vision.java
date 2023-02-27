/*****************************************************
 * Authors: Fawaaz Siddiqui, Lemi Miyu
 * ---------------------------------------------------
 * Description: Contains basic code for vision subsystem.
 * 
*****************************************************/

package frc.robot.subsystems;


import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonTargetSortMode;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase
{
  // CONSTANTS //
  
  // declare constant variables here (make them private static)
  
  // VARIABLES //
  private PhotonCamera camera;
  private PhotonPipelineResult result;
  private PhotonTrackedTarget target;

  

  
  // HARDWARE //
  
  // declare electrical hardware here
  
  // CONSTRUCTORS //
  
  public Vision()
  {

    // instantiate all electrical hardware and variables here
    camera = new PhotonCamera("photonvision");

    // get latest result
    result = camera.getLatestResult();

    getTargetData();

  }

  /***************************************
   * getTargetData(): calculates information from a 
   * target when detected by a camera. Must be able 
   * to return a value.
  ***************************************/

  public void getTargetData() { // there must be a return type
    if (result.hasTargets()) {
  
      // get the current best target
      target = result.getBestTarget();

      // get target info
      double yaw = target.getYaw();                  // yaw of target in degrees; positive right
      double pitch = target.getPitch();              // pitch of target in degrees; positive up
      double area = target.getArea();                // area of target on screen as a percent 
      double skew = target.getSkew();                // skew of target in degrees; positive is counter-clockwise
      // Transform2d pose = target.getCameraToTarget();
      // List<TargetCorner> corners = target.getCorners();
      var camToTarget = target.getBestCameraToTarget();

    }
  }
  
  // write your subsystem methods here
}

