package frc.robot.subsystems;

import java.util.ArrayList; 
import java.util.LinkedList; 
import java.util.List;

//Photonvision imports 
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.common.hardware.VisionLEDMode;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

//cameraserver imports 
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;

//opencv imports
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc; 

//apriltag imports
import edu.wpi.first.apriltag.*;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;

//smartdashboard imports
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//math imports
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.MatBuilder;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.util.Units;

//other imports
import edu.wpi.first.vision.VisionThread; 
import frc.robot.pipelines.*; 
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.Optional;
import java.io.IOException;

import edu.wpi.first.wpilibj.Solenoid; 
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.TimedRobot; 
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.math.controller.SimpleMotorFeedforward; 
import edu.wpi.first.math.controller.PIDController; 

public class Vision extends SubsystemBase
{
  // CONSTANTS //
  private Solenoid light; 
  private ADXRS450_Gyro gyro;  
  private final static int LIGHT_PORT = 3;

  final static int IMG_HEIGHT = 120; 
  final static int IMG_WIDTH = 160; 
  final static int FPS = 30; 
  

  Thread m_visionThread;
  private VisionThread visionThread; 
  
  //photonvision 
  private static boolean hasTarget; 
  private static PhotonPipelineResult result; 
  private static List<PhotonTrackedTarget> targets; 
  private static PhotonTrackedTarget target; 
  private static PhotonCamera camera;
  private static PhotonCamera cam;
  private PhotonPoseEstimator photonPoseEstimator;

  private static AprilTagFieldLayout aprilTagFieldLayout; 

  private double CAMERA_HEIGHT_METERS; 
  private double TARGET_HEIGHT_METERS;
  private double CAMERA_PITCH_RADIANS; 
  private double TARGET_PITCH_RADIANS;

  private double xCentre;
  private double width; 

  private PipelineSettings settings; 

  private double range; 
  private Pose2d robotPose;
  private Pose2d targetPose; 
  private Translation2d translation;
  private Transform2d cameraToRobot; 
  private Transform3d cameraToRobot2;

  private int PIPELINE_INDEX;
  
  // declare constant variables here (make them private static)
  
  // VARIABLES //
  
  // declare variables here
  
  // HARDWARE //
  
  // declare electrical hardware here
  
  // CONSTRUCTORS //
  
  public Vision()
  {
    light = new Solenoid (PneumaticsModuleType.CTREPCM, LIGHT_PORT);
    light.set(true);

    
    PhotonCamera camera = new PhotonCamera("photonvision");
    PhotonCamera cam = new PhotonCamera("testCamera");
    Transform3d robotToCam = new Transform3d(new Translation3d(0.5,0.0,0.5), new Rotation3d(0,0,0));
    try {
      aprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
      photonPoseEstimator = new PhotonPoseEstimator(aprilTagFieldLayout, PoseStrategy.CLOSEST_TO_REFERENCE_POSE, cam, robotToCam);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    result = camera.getLatestResult(); 
    hasTarget = result.hasTargets(); 
    gyro = new ADXRS450_Gyro();
    calibrateGyro();
    // instantiate all electrical hardware and variable here
  }
  
  public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
    photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);
    return photonPoseEstimator.update();
  }

  //setReferencePose(Pose3d referencePose) updates the stored reference pose when using the
  //CLOSEST_TO_REFERENCE_POSE strategy 
  //setLastPose(Pose3d lastPose) updates the stored last pose, which is useful for setting the initial estimate when using the
  //CLOSEST_TO_LAST_POSE strategy
  

  public void cameraSetDriverMode(){
    camera.setDriverMode(true);
  }

  public void cameraSetPipelineIndex(){
    camera.setPipelineIndex(PIPELINE_INDEX);
  }

  public double getLatencySeconds(){
    return result.getLatencyMillis()/1000.0;
  }

  public void turnOnLimelight(){
    camera.setLED(VisionLEDMode.kBlink);
  }
  
  /*public void drivetrainPoseEstimator(){
    var poseEstimator = new DifferentialDrivePoseEstimator(new Rotation2d(), new Pose2d(),
    new MatBuilder<>(Nat.N5(), Nat.N1()).fill(0.02, 0.02, 0.01, 0.02, 0.02), //state measurement standard deviations. X,Y,theta
    new MatBuilder<>(Nat.N3(), Nat.N1()).fill(0.02, 0.02, 0.01), //local measurement standard deviations. left encoder, right encoder, gyro
    new MatBuilder<>(Nat.N3(), Nat.N1()).fill(0.1, 0.1, 0.01)); //global measurement standard deviation. x,y,and theta
    getEstimatedGlobalPose(poseEstimator);
    addVisionMeasurement();
  }*/
  
  public static void displayBestTarget(){
    if(result.hasTargets()){
      List<PhotonTrackedTarget> targets = result.getTargets();
      target = result.getBestTarget();
    }
  }


  public static double getYaw(){
    return target.getYaw(); 
  }

  public static double getPitch(){
    return target.getPitch();
  }

  public static double getArea(){
    return target.getArea();
  }

  public static double getSkey(){
    return target.getSkew();
  }

  public static Transform3d return3dPose(){
    return target.getBestCameraToTarget();
  }

  public static Transform3d alternateReturn3dPose(){
    return target.getAlternateCameraToTarget();
  }

  public static int returnTarget(){
    return target.getFiducialId();
  }

  public static double returnPoseAmbiguity(){
    return target.getPoseAmbiguity();
  }

  public static void snapshots(){//maybe use this for object recognition when you get there
    camera.takeInputSnapshot();
    camera.takeOutputSnapshot();
  }
  /*public static List<TargetCorner> returnCorners(){//not sure why this doesn't work, gotta ask
    return targets.getCorners();
  }*/


public double returnRange(){
  if(result.hasTargets()){
    range = PhotonUtils.calculateDistanceToTargetMeters(CAMERA_HEIGHT_METERS,TARGET_HEIGHT_METERS,CAMERA_PITCH_RADIANS,Units.degreesToRadians(result.getBestTarget().getPitch()));
  }
  return range;
}

public Pose2d returnRobotPose(){
  return PhotonUtils.estimateFieldToRobot(CAMERA_HEIGHT_METERS, TARGET_HEIGHT_METERS, CAMERA_PITCH_RADIANS, TARGET_PITCH_RADIANS, Rotation2d.fromDegrees(-target.getYaw()), gyro.getRotation2d(),targetPose, cameraToRobot);
}
public double returnDistanceBetweenPose(){
  return PhotonUtils.getDistanceToPose(returnRobotPose(), targetPose);
}

public Translation2d returnTranslationToTarget(){
  return PhotonUtils.estimateCameraToTargetTranslation(returnRange(), Rotation2d.fromDegrees(-target.getYaw()));
}

public Pose3d returnRobotPoseApriltag(){
  return PhotonUtils.estimateFieldToRobotAprilTag(return3dPose(),(aprilTagFieldLayout.getTagPose(returnTarget())).get(),cameraToRobot2);

}

public Rotation2d targetYaw(){
  return PhotonUtils.getYawToPose(robotPose,targetPose);
}

public void calibrateGyro(){
  resetGyro();
  gyro.calibrate();
}

public void resetGyro(){
  gyro.reset();
}

  public void intCamera(){
    m_visionThread = 
    new Thread(
        () -> {
            UsbCamera camera = CameraServer.startAutomaticCapture();
            camera.setResolution(640,480);
            
            CvSink cvSink = CameraServer.getVideo();
            CvSource outputStream = CameraServer.putVideo(("Rectangle"), 640, 480);
            

            Mat mat = new Mat();
            while(!Thread.interrupted()){
                if(cvSink.grabFrame(mat)==0){
                    outputStream.notifyError(cvSink.getError());
                    continue;
                }
                Imgproc.rectangle(
                    mat, new Point(100,100), new Point(400,400), new Scalar(255, 255, 255), 5);
                    outputStream.putFrame(mat);
                }
            });
            m_visionThread.setDaemon(true);
            m_visionThread.start();
    
  }
  }
 
  // METHODS //
  
  // write your subsystem methods here

