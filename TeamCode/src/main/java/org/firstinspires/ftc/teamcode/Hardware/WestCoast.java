package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DashConstants.PIDConstants;
import org.firstinspires.ftc.teamcode.Hardware.Sensors.IMU;
import org.firstinspires.ftc.teamcode.Utilities.PID0;
import org.opencv.core.Mat;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static com.qualcomm.robotcore.util.Range.clip;
import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.hardwareMap;
import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.multTelemetry;


public class WestCoast {

   public IMU imu;

   public PID0 drivePID = new PID0 (PIDConstants.propWeight, PIDConstants.intWeight, PIDConstants.dervWeight, 0, 0);

   public static ElapsedTime time = new ElapsedTime();

   private DcMotor backleft, backright, frontright, frontleft;

   public WestCoast(){
      initRobot();
   }

   public WestCoast(IMU imu) {
      this.imu = imu;
   }

   public void initRobot() {

      backleft = hardwareMap.get(DcMotor.class, "backleft");
      backleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      backleft.setDirection(FORWARD);

      backright = hardwareMap.get(DcMotor.class, "backright");
      backright.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      backright.setDirection(REVERSE);

      frontleft = hardwareMap.get(DcMotor.class, "frontleft");
      frontleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      frontleft.setDirection(FORWARD);

      frontright = hardwareMap.get(DcMotor.class, "frontright");
      frontright.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      frontright.setDirection(REVERSE);

      imu = new IMU("imu");

      multTelemetry.addData("Status", "Initialized");
      multTelemetry.update();
   }

   public void resetMotors(){

   }

   /**
    * @param power
    */
   public void setAllPower(double power){
        /*

                Y O U R   C O D E   H E R E

         */
   }

   /**
    * @param drive
    */
   public void setDrivePower(double drive, double turn, boolean persicion){

      double power = .8;

      if (persicion){
         power = .3;
      }

      turn = clip(turn, -0.8, 0.8);

      backleft.setPower((drive + turn) * power);
      frontleft.setPower((drive + turn) * power);
      backright.setPower((drive - turn) * power);
      frontright.setPower((drive - turn) * power);

   }


   /**
    * IMPLEMENT ME
    * @param ticks
    */
   public void strafe(double ticks){
        /*

                Y O U R   C O D E   H E R E

         */
   }

   /**
    * IMPLEMENT ME
    * @param degrees
    * @param moe
    */
   public void turn(double degrees, double moe){
        /*

                Y O U R   C O D E   H E R E

         */
   }


   /**
    * @param position
    * @param distance
    * @param acceleration
    * @return the coefficient [0, 1] of our power
    */
   public static double powerRamp(double position, double distance, double acceleration){
        /*

                Y O U R   C O D E   H E R E

         */
      return 0;
   }

}