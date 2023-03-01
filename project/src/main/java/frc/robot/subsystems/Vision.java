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
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase
{
  // CONSTANTS (make them private static)
  private static final double CAMERA_HEIGHT_METERS = 0.5; // to be finalized later
  private static final double TARGET_HEIGHT_METERS = 0.5;
  private static final double CAMERA_PITCH_RADIANS = 0.5;
  private static final double GOAL_RANGE_METERS = 0.5;
  
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
   * 
   * return: {yaw, pitch, area, skew, distance}
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
    else {
      // return {0, 0, 0, 0, 0};
    }
  }

  public double getYaw() {
    return result.hasTargets()? target.getYaw(): 0;
  }

  public double getPitch() {
    return result.hasTargets()? target.getPitch(): 0;
  }

  public double getArea() {
    return result.hasTargets()? target.getArea(): 0;
  }

  public double getSkew() {
    return result.hasTargets()? target.getSkew(): 0;
  }

  /*********************************************
   * getDistanceToTaret(): calculates distance
   * of robot from target and returns a double 
   * range value in meters. Takes variables such
   * as camera height and target height into 
   * consideration.
  *********************************************/

  public double getDistanceToTarget() {
    result = camera.getLatestResult();

    if (result.hasTargets()) {

      // calculate range of target
      double range = PhotonUtils.calculateDistanceToTargetMeters(
                                CAMERA_HEIGHT_METERS,
                                TARGET_HEIGHT_METERS,
                                CAMERA_PITCH_RADIANS,
                                Units.degreesToRadians(result.getBestTarget().getPitch()));

                // Use this range as the measurement we give to the PID controller.
                //-1.0 required to ensure positive PID controller effort _increases_ range
                // forwardSpeed = -controller.calculate(range, GOAL_RANGE_METERS);
      return range;
    }
    return -1; // no target found

  }
  
  // write your subsystem methods here
}

