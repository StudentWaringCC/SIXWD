package org.firstinspires.ftc.teamcode.Labs.Lab1;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Controls.Controller_0;
import org.firstinspires.ftc.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.Hardware.Shooter;
import org.firstinspires.ftc.teamcode.Hardware.WestCoast;

import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.multTelemetry;
import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.setOpMode;


@TeleOp(name="LAB 1: TeleOp", group="Iterative Opmode")
public class IterativeTeleOp extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private WestCoast robbot;
    private Controller_0 controller1;
    private Controller_0 controller2;


    private static final double servoOffset = .05;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        setOpMode(this);

        Intake.init();

        Shooter.init();

        robbot = new WestCoast();

        controller1 = new Controller_0(gamepad1);
        controller2 = new Controller_0(gamepad2);


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
        controller2.update();

        double regTurn = controller1.leftStick().x; // Set turn value

        Shooter.shooterState(controller2.triangle.isTapped());
        Shooter.feederState(controller2.square.isTapped());

        Intake.runIntake(controller2.cross.isTapped(), controller2.circle.isPressed());
        Intake.pleatherState(controller2.RB.isTapped(), controller2.LB.isTapped());

        robbot.setDrivePower(controller1.RT.getRawValue() - controller1.LT.getRawValue(), regTurn, controller1.triangle.isToggle());


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
}