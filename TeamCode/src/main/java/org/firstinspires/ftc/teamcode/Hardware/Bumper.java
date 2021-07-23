package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.hardwareMap;

public class Bumper {

    private static Servo bumperServo;
    private static final double UP = 1.0, DOWN = 0.0, FOLDED = 0.5;
    private static bumperState curBumperState = bumperState.FOLDED;

    public static void init(){
        bumperServo = hardwareMap.get(Servo.class, "armservo");
    }

    public static void armPos (bumperState position){
        if (position == bumperState.UP) bumperServo.setPosition(UP);
        else if (position == bumperState.DOWN) bumperServo.setPosition(DOWN);
        else if (position == bumperState.FOLDED) bumperServo.setPosition(FOLDED);
    }


    public static void bumperState(boolean bumperUpDown, boolean downFolded){

        switch (curBumperState){

            case FOLDED:
                armPos(curBumperState);
                if (bumperUpDown) newState(bumperState.UP);
                if (downFolded) newState(bumperState.DOWN);
                break;

            case DOWN:
                armPos(curBumperState);
                if (bumperUpDown) newState(bumperState.UP);
                if (downFolded) newState(bumperState.FOLDED);
                break;

            case UP:
                armPos(curBumperState);
                if (bumperUpDown) newState(bumperState.DOWN);
                if (downFolded) newState(bumperState.FOLDED);
                break;
        }
    }

    /* private static void bumperUp() {bumperServo.setPosition(UP);}
    private static void bumperDown() {bumperServo.setPosition(DOWN);}
    private static void bumperFolded() {bumperServo.setPosition(FOLDED);} */

    private static void newState(bumperState newState){
        curBumperState = newState;
    }

    private enum bumperState {
        UP, DOWN, FOLDED
    }

}
