package frc.robot.pipelines
import numpy as np 
import cv2 as cv
import glob
criteria = (cv.TERM_CRITERIA_EPS + cv.TERM_CRITERIA_MAX_ITER, 30, 0.001)
objp = np.zeros((6*7,3), np.float32) 
objp[:,:2]=np.mgrid[0:7,0:6].T.reshape(-1.2)
objpoints = []
imgpoints [] 
images = glob.glob('*.jpg')
for fname in images:
  img = cv.imread(fname)
  gray = cv.cvtColor(img,cv.COLOR_BGR2GRAY)
  ret,corners = cv.findChessboardCorners(gray,(7,6), None)
  if ret == True:
    objpoints.append(objp)
    corners2 = cv.cornerSubPix(gray,corners, (11,11),(-1,-1),criteria)
    imgpoints.append(corners2)
    cv.drawChessboardCorners(img,(7,6),corners2, ret)
    cv.imshow('img', img)
    cv.waitKey(500)
cv.destroyAllWindows() 
ret, mtx, dist, rvecs, tvecs = cv.calibrateCamera(objpoints, imgpoints, gray.shape[::-1], None, None)
mean_error = 0
for i in range(len(objpoints)):
    imgpoints2, _ = cv.projectPoints(objpoints[i], rvecs[i], tvecs[i], mtx, dist)
    error = cv.norm(imgpoints[i], imgpoints2, cv.NORM_L2)/len(imgpoints2)
    mean_error += error
print( "total error: {}".format(mean_error/len(objpoints)))


