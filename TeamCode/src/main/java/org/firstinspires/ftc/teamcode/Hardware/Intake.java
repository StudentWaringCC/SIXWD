package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.hardwareMap;
import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.multTelemetry;

public class Intake {

    private static DcMotor intake1, intake2;
    private static CRServo bottomRoller;

    private static intakeState curIntakeState = intakeState.OFF;
    private static intakeState prevIntakeState;

    private static Servo pleatherLeft, pleatherRight;
    private static pleatherState curPleatherState = pleatherState.RETRACTED;
    private static pleatherState prevPleatherState = curPleatherState;


    private static final double servoOffset = .05;

    public static void init(){
        intake1 = hardwareMap.get(DcMotor.class, "intake1");
        intake2 = hardwareMap.get(DcMotor.class, "intake2");
        bottomRoller = hardwareMap.get(CRServo.class, "bottomroller");

        pleatherLeft = hardwareMap.get(Servo.class, "pleatherleft");
        pleatherRight = hardwareMap.get(Servo.class, "pleatherright");
        pleatherRight.setDirection(Servo.Direction.REVERSE);

        curPleatherState = pleatherState.RETRACTED;
        curIntakeState = intakeState.OFF;
    }

    public static void intakeOn(){
        intake1.setPower(1.0);
        intake2.setPower(1.0);
        bottomRoller.setPower(1);
    }

    public static void intakeOff(){
        intake1.setPower(0);
        intake2.setPower(0.0);
        bottomRoller.setPower(0);
    }

    public static void intakeReverse(){
        intake1.setPower(-1);
        intake2.setPower(-1);
        bottomRoller.setPower(-1);
    }


    public static void runIntake(boolean onOff, boolean reverse) {

        switch (curIntakeState){

            case ON:
                intakeOn();
                if (onOff) newIntakeState(intakeState.OFF);
                if (reverse) newIntakeState(intakeState.REVERSE);
                break;

            case OFF:
                intakeOff();
                if (onOff) {
                    curPleatherState = pleatherState.ROLLING;
                    newIntakeState(intakeState.ON);
                }
                if (reverse) newIntakeState(intakeState.REVERSE);
                break;

            case REVERSE:
                intakeReverse();
                curPleatherState = pleatherState.REVERSE;
                if (!reverse) curIntakeState = prevIntakeState;
                break;
        }

        multTelemetry.addData("STATE: ", curIntakeState);
    }

    public static void newIntakeState(intakeState state){
        prevIntakeState = curIntakeState;
        curIntakeState = state;
    }

        public static void pleatherPos(pleatherState state){
            double position = (state == pleatherState.RETRACTED) ? 0.4 :
                    (state == pleatherState.ROLLING) ? 0.15 : (state == pleatherState.FLAT) ? 0.01 : 0.0;
            pleatherRight.setPosition(position);
            pleatherLeft.setPosition(position + servoOffset);
        }

        public static void pleatherPos(){
            pleatherPos(curPleatherState);
        }

        public static void pleatherState(boolean kicker, boolean winding){

            switch (curPleatherState){

                case RETRACTED:
                    if (kicker) newPleatherState(pleatherState.ROLLING);
                    if (winding) newPleatherState(pleatherState.FLAT);
                    break;

                case ROLLING:
                    if (kicker) newPleatherState(pleatherState.FLAT);
                    if (winding) newPleatherState(pleatherState.RETRACTED);
                    break;

                case FLAT:
                    if (kicker) newPleatherState(pleatherState.RETRACTED);
                    if (winding) newPleatherState(pleatherState.ROLLING);
                    break;

                case REVERSE:
                    if (curIntakeState != intakeState.REVERSE) curPleatherState = prevPleatherState;
                    break;
            }

            pleatherPos();

            multTelemetry.addData("STATE: ", curPleatherState);


        }
        public static void newPleatherState (pleatherState state){
            prevPleatherState = curPleatherState;
            curPleatherState = state;
        }


        public enum pleatherState {
            RETRACTED, ROLLING, FLAT, REVERSE
        }

    private enum intakeState {
        OFF, ON, REVERSE
    }

}

