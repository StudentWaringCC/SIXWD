package org.firstinspires.ftc.teamcode.Hardware.Sensors;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.hardwareMap;

public class Camera {

    private OpenCvCamera webcam;

    public Camera(String id, OpenCvPipeline pipeline){

        // If we enabled display, add the cameraMonitorViewId to the creation of our webcam
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, id), cameraMonitorViewId);

        // Start streaming images
        webcam.openCameraDeviceAsync(() -> webcam.startStreaming(432, 240, OpenCvCameraRotation.UPRIGHT));

        // Route our images through the pipeline
        webcam.setPipeline(pipeline);
    }
}

