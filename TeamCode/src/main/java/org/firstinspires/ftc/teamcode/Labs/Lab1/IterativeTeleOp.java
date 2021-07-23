package org.firstinspires.ftc.teamcode.Labs.Lab1;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Arm;
import org.firstinspires.ftc.teamcode.Hardware.Bumper;
import org.firstinspires.ftc.teamcode.Hardware.Controls.Controller_0;
import org.firstinspires.ftc.teamcode.Hardware.WestCoast;

import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.multTelemetry;
import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.setOpMode;


@TeleOp(name="LAB 1: TeleOp", group="Iterative Opmode")
public class IterativeTeleOp extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private WestCoast robbot;
    private Controller_0 controller1;



    // Variable to store set point for PID angle
    private double PID_Angle = 0;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        setOpMode(this);

        Arm.init();
        // Bumper.init();

        robbot = new WestCoast();
        controller1 = new Controller_0(gamepad1);

        multTelemetry.addData("Status", "Initialized");
        multTelemetry.update();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {

        /*
                    Y O U R   C O D E   H E R E
                                                   */


        multTelemetry.addData("Status", "InitLoop");
        multTelemetry.update();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
        PID_Angle = robbot.imu.getAngle();

        /*
                    Y O U R   C O D E   H E R E
                                                   */

        multTelemetry.addData("Status", "Started");
        multTelemetry.update();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        controller1.update();

        // Bumper.bumperState(controller1.square.isPressed(), controller1.circle.isPressed());

        Arm.armSTATE(controller1.RB.isPressed(), controller1.LB.isPressed());




        double regTurn = controller1.leftStick().x; // Set turn value

        double curAngle = robbot.imu.getAngle();

         /* if (Math.abs(regTurn) >= .05) {
            PID_Angle = curAngle;
        }else{
            regTurn = robbot.drivePID.update(PID_Angle - curAngle) * -1;
            }  */




        // robbot.setDrivePower(controller1.RT.getRawValue() - controller1.LT.getRawValue(), regTurn, controller1.triangle.isToggle());


        /*
             ----------- L O G G I N G -----------
                                                */
        multTelemetry.addData("Status", "TeleOp Running");
        multTelemetry.addData("boopy", 0.0 - 0.0);
        multTelemetry.addData("woopsy", PID_Angle - 0.0);
        multTelemetry.addData("doopsy", 0.0 - robbot.imu.getAngle());
        multTelemetry.addData("poopsy", 0.0 - curAngle);
        multTelemetry.addData("scoopty", PID_Angle - curAngle);


        multTelemetry.addData("angle", robbot.imu.getAngle());
        multTelemetry.addData("pid output", robbot.drivePID.update(PID_Angle - robbot.imu.getAngle()));
        multTelemetry.addData("Drive", controller1.RT.getRawValue() - controller1.LT.getRawValue());
        multTelemetry.addData("regTurn", regTurn);
        multTelemetry.addData("PID_Angle", PID_Angle);
        multTelemetry.addData("Precision", controller1.RB.isToggle());
        multTelemetry.update();
    }

    @Override
    public void stop() {

        /*
                    Y O U R   C O D E   H E R E
                                                   */

    }
}