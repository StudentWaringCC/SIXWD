package org.firstinspires.ftc.teamcode.Labs.Lab1.Lab1_4;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Controls.Controller;
import org.firstinspires.ftc.teamcode.Hardware.Controls.Controller_0;

import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.multTelemetry;
import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.setOpMode;

@Disabled
@TeleOp(name="Lab 1.4 Switch", group="Iterative Opmode")
public class IterativeTeleOpSwitch extends OpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();


    private Servo demoServo;
    private Controller_0 newController;
    //Initializes set point for PID
    private int dummy = 0;
    private huh currentHuh = huh.DOWN;

    /*
     * Code to run ONCE when the driver hits INIT
     */



    @Override
    public void init() {
        setOpMode(this);
        newController = new Controller_0(gamepad1);
        hardwareMap.get(Servo.class, "demoservo" );
        multTelemetry.addData("Status", "Initialized");
        multTelemetry.update();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {



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

        newController.update();

        switch (currentHuh){

            case DOWN:
                demoServo.setPosition(0.0);
                if (newController.square.isTapped()) currentHuh = huh.UP;
                if (newController.circle.isTapped()) currentHuh = huh.FOLDED;
                break;

            case UP:
                demoServo.setPosition(1);
                if (newController.square.isTapped()) currentHuh = huh.FOLDED;
                if (newController.circle.isTapped()) currentHuh = huh.DOWN;
                break;

            case FOLDED:
                demoServo.setPosition(.5);
                if (newController.square.isTapped()) currentHuh = huh.UP;
                if (newController.circle.isTapped()) currentHuh = huh.DOWN;
                break;

        }

        multTelemetry.addData("State: ", currentHuh);

        multTelemetry.update();

        /*

                    Y O U R   C O D E   H E R E

                                                    */







        /*
             ----------- L O G G I N G -----------
                                                */
        multTelemetry.addData("Status", "TeleOp Running");
        multTelemetry.update();
    }

    @Override
    public void stop() {

        /*
                    Y O U R   C O D E   H E R E
                                                   */

    }

    private enum huh {
        UP, DOWN, FOLDED};
}