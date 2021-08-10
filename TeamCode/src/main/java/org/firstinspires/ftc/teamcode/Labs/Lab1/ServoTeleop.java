package org.firstinspires.ftc.teamcode.Labs.Lab1;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Controls.Controller_0;
import org.firstinspires.ftc.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.Hardware.Shooter;
import org.firstinspires.ftc.teamcode.Hardware.WestCoast;

import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.multTelemetry;
import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.setOpMode;


@TeleOp(name="ServoTest TeleOp", group="Iterative Opmode")
public class ServoTeleop extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private Controller_0 controller1;

    private Servo pleatherLeft, pleatherRight;


    private static final double servoOffset = .05;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        setOpMode(this);

        controller1 = new Controller_0(gamepad1);

        pleatherLeft = hardwareMap.get(Servo.class, "pleatherleft");
        pleatherRight = hardwareMap.get(Servo.class, "pleatherright");
        pleatherRight.setDirection(Servo.Direction.REVERSE);

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


        if (controller1.square.isPressed()) {
            pleatherRight.setPosition(.4);
            pleatherLeft.setPosition(0.4);
        }
        if (controller1.circle.isPressed()) {
            pleatherRight.setPosition(.15);
            pleatherLeft.setPosition(0.15);
        }
        if (controller1.triangle.isPressed()) {
            pleatherRight.setPosition(.04);
            pleatherLeft.setPosition(0.01);
        }
        if (controller1.cross.isPressed()) {
            pleatherRight.setPosition(.0);
            pleatherLeft.setPosition(0.0);
        }

        /*
             ----------- L O G G I N G -----------
                                                */
        multTelemetry.addData("Status", "TeleOp Running");
        multTelemetry.update();
    }

    @Override
    public void stop() {

       Intake.intakeOff();

       Shooter.shooterOff();

    }
}