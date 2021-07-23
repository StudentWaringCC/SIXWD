package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.hardwareMap;
import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.multTelemetry;
import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.telemetry;

public class Leg {

    private static double position = 0.0;

    private static Servo legServo;
    private static kickExtension curKickExtension = kickExtension.WINDUP;


    public static void init(){
        legServo = hardwareMap.get(Servo.class, "legServo");
    }

    public static void legPos(kickExtension state){
        if (state == kickExtension.FULL) position = 1.0;
        else if (state == kickExtension.MID) position = 0.5;
        legServo.setPosition(position);
    }

    public static void legState(boolean kicker, boolean winding){

        switch (curKickExtension){

            case FULL:
                legPos(curKickExtension);
                if (kicker) curKickExtension = kickExtension.MID;
                if (winding) curKickExtension = kickExtension.WINDUP;
                break;

            case MID:
                legPos(curKickExtension);
                if (kicker) curKickExtension = kickExtension.FULL;
                if (winding) curKickExtension = kickExtension.WINDUP;
                break;

            case WINDUP:
                legPos(curKickExtension);
                if (kicker) curKickExtension = kickExtension.FULL;
                if (winding) curKickExtension = kickExtension.MID;
                 break;
        }

        multTelemetry.addData("STATE: ", curKickExtension);
        telemetry.update();

    }

    private enum kickExtension {
        FULL, MID, WINDUP
    }
}

