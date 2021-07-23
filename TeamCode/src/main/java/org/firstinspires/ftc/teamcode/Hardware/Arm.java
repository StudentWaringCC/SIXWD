package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.hardwareMap;
import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.multTelemetry;

public class Arm {

    private static Servo armServo;
    private static huh currentHuh = huh.FOLDED;

    private static final double UP = 1, DOWN = 0.0, FOLDED = .5;

    public static void init(){
        armServo = hardwareMap.get(Servo.class, "demoServo");
    }


    public static void armUp() {armServo.setPosition(UP);}
    public static void armDown() {armServo.setPosition(DOWN);}
    public static void armFolded() {armServo.setPosition(FOLDED);}


    public static void armSTATE(boolean armUpDown, boolean downFold){

        switch (currentHuh){

            case UP:
                armUp();
                if (armUpDown) {newState(huh.DOWN);}
                if (downFold) {newState(huh.FOLDED);}
                break;

            case FOLDED:
                armFolded();
                if (armUpDown) {newState(huh.UP);}
                if (downFold) {newState(huh.DOWN);}
                break;

            case DOWN:
                armDown();
                if (armUpDown) {newState(huh.UP);}
                if (downFold) {newState(huh.FOLDED);}
                break;

        }

        multTelemetry.addData("STATE: ", currentHuh);

    }

    private static void newState(huh armState){
        currentHuh = armState;
    }

    private enum huh {
        UP, DOWN, FOLDED}
}


