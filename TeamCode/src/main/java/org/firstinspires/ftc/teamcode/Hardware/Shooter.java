package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.DashConstants.Dash_Shooter;
import org.firstinspires.ftc.teamcode.Utilities.PID0;
import org.firstinspires.ftc.teamcode.Utilities.RingBuffer;

import static org.firstinspires.ftc.teamcode.DashConstants.Dash_Servos.LOCK_SERVO_MAX;
import static org.firstinspires.ftc.teamcode.DashConstants.Dash_Servos.LOCK_SERVO_MIN;
import static org.firstinspires.ftc.teamcode.DashConstants.Dash_Servos.SHOOT_SERVO_MAX;
import static org.firstinspires.ftc.teamcode.DashConstants.Dash_Servos.SHOOT_SERVO_MIN;
import static org.firstinspires.ftc.teamcode.DashConstants.Dash_Shooter.RPM;
import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.hardwareMap;
import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.multTelemetry;

public class Shooter {

    private static DcMotor shooter1, shooter2;
    private static Servo lockServo, feedServo;

    public static PID0 shooterPID = new PID0(Dash_Shooter.p, Dash_Shooter.i, Dash_Shooter.d,0);

    static RingBuffer <Long> timeRing = new RingBuffer<>(2, 0L);
    static RingBuffer <Double> positionRing = new RingBuffer<>(2, 0.0);

    private static final double TICKS_PER_ROTATION = 28;
    private static double shooterRPM;

    private static ShooterState curShooterState = ShooterState.OFF;

    private static FeederState curFeederState = FeederState.IDLE;

    private static ElapsedTime feederTime = new ElapsedTime();

    private static final double SHOOT_POSITION =    SHOOT_SERVO_MIN;
    private static final double RESET_POSITION =    SHOOT_SERVO_MAX;

    private static final double LOCK_POSITION =     LOCK_SERVO_MAX;
    private static final double UNLOCK_POSITION =   LOCK_SERVO_MIN;

    private static boolean isFeederLocked = false;

    private static final double LOCK_TIME = .8;
    private static final double UNLOCK_TIME = .1;
    private static final double FEED_TIME = .23;
    private static final double RESET_TIME = .2;

    public static void init(){
        shooter1 = hardwareMap.get(DcMotor.class, "shooter1");
        shooter1.setDirection(DcMotorSimple.Direction.REVERSE);
        shooter2 = hardwareMap.get(DcMotor.class, "shooter2");
        shooter2.setDirection(DcMotorSimple.Direction.REVERSE);

        shooter1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooter2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        feedServo = hardwareMap.get(Servo.class, "feedservo");
        lockServo = hardwareMap.get(Servo.class, "lockservo");

        curShooterState = ShooterState.OFF;
        curFeederState = FeederState.IDLE;
    }

    public static long getPosition(){
        return ((shooter1.getCurrentPosition() + shooter2.getCurrentPosition()) / 2);
    }

    public static void shooterOff(){
        shooter1.setPower(0); shooter2.setPower(0);
    }

    public static void setRPM(int targetRpm, PID0 pid){


        pid.setF(targetRpm / 10000.0);
        double shooterPower = pid.update(targetRpm - updateRPM(), true);

        //if (getRPM() < targetRpm * .9) pid.setIntegralSum(targetRpm * 3);

        shooterPower = Range.clip(shooterPower,0.0,1.0);
        setPower(shooterPower);

        multTelemetry.addData("RPM", getRPM());
        multTelemetry.addData("Target", targetRpm);
    }

    public static double updateRPM(){
        long currentTime = System.currentTimeMillis();
        long deltaMili = currentTime - timeRing.updateCurWith(currentTime);
        double deltaMinutes = deltaMili / 60000.0;

        double currentPosition = getPosition();
        double deltaTicks = currentPosition - positionRing.updateCurWith(currentPosition);
        double deltaRotations = deltaTicks / TICKS_PER_ROTATION;

        shooterRPM  = Math.abs(deltaRotations / deltaMinutes);

        return shooterRPM;
    }

    public static double getRPM() { return shooterRPM; }

    public static void setPower(double power){
        shooter1.setPower(power);
        shooter2.setPower(power);
    }


    public static void shooterState(boolean triangle){

        switch (curShooterState){

            case ON:
                setRPM(RPM, shooterPID);
                if (triangle) newShooterState(ShooterState.OFF);
                break;
            case OFF:
                shooterOff();
                if (triangle) newShooterState(ShooterState.ON);
                break;
        }
    }

    public static void lockFeeder(){ lockServo.setPosition(LOCK_POSITION); isFeederLocked = true; }

    public static void unlockFeeder(){ lockServo.setPosition(UNLOCK_POSITION); isFeederLocked = false;}

    public static void resetFeeder(){ feedServo.setPosition(RESET_POSITION);}

    public static void feedRing(){ feedServo.setPosition(SHOOT_POSITION);}

    public static boolean isFeederLock(){ return (lockServo.getPosition() == LOCK_POSITION ); }

    public static void feederState(boolean trigger){

        switch (curFeederState){

            case IDLE:

                if (trigger) newFeederState(FeederState.FEED);

                if (feederTime.seconds() > LOCK_TIME) lockFeeder();
                else unlockFeeder();

                resetFeeder();

                break;

            case FEED:

                if (isFeederLock()) {
                    if (feederTime.seconds() > UNLOCK_TIME) feedRing();
                    if (feederTime.seconds() > (UNLOCK_TIME + LOCK_TIME)) newFeederState(FeederState.RESET);
                } else {
                    feedRing();
                    if (feederTime.seconds() > FEED_TIME) newFeederState(FeederState.RESET);
                }

                break;

            case RESET:
                resetFeeder();
                if (feederTime.seconds() > RESET_TIME) newFeederState(FeederState.IDLE);

                break;
        }
    }

    private static void newFeederState(FeederState feederState){
        curFeederState = feederState;
        feederTime.reset();
    }

    private static void newShooterState(ShooterState shooterState){
        curShooterState = shooterState;
    }

    private enum ShooterState{
        ON, OFF
    }

    private enum FeederState {
        IDLE, FEED, RESET
    }

}
