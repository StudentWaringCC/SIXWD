package org.firstinspires.ftc.teamcode.Labs.Lab3.Vision;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.openftc.easyopencv.OpenCvPipeline;

import static org.firstinspires.ftc.teamcode.Labs.Lab3.Vision.Dash_Vision.h;
import static org.firstinspires.ftc.teamcode.Labs.Lab3.Vision.Dash_Vision.h2;
import static org.firstinspires.ftc.teamcode.Labs.Lab3.Vision.Dash_Vision.w2;
import static org.firstinspires.ftc.teamcode.Labs.Lab3.Vision.Dash_Vision.x2;
import static org.firstinspires.ftc.teamcode.Labs.Lab3.Vision.Dash_Vision.y;
import static org.firstinspires.ftc.teamcode.Labs.Lab3.Vision.Dash_Vision.x;
import static org.firstinspires.ftc.teamcode.Labs.Lab3.Vision.Dash_Vision.w;
import static org.firstinspires.ftc.teamcode.Labs.Lab3.Vision.Dash_Vision.y2;
import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.multTelemetry;
import static org.opencv.core.Core.mean;
import static org.opencv.imgproc.Imgproc.rectangle;


public class DetectionPipeline extends OpenCvPipeline {

    private Scalar green = new Scalar(0, 255, 0);

    @Override
    public Mat processFrame(Mat input) {

        Rect rect1= new Rect(x, y, h, w);
        Rect rect2= new Rect(x2, y2, h2, w2);

        rectangle(input, rect1, green);
        rectangle(input, rect2, green);

        Mat crop = input.submat(rect1);

        Scalar meanColor = mean(crop);

        multTelemetry.addData("Mean Color: ", meanColor);

        return input;
    }
}
