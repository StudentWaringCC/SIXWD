package org.firstinspires.ftc.teamcode.Hardware.Controls;



import com.qualcomm.robotcore.hardware.Gamepad;

import org.opencv.core.Point;

public class Controller_0 {

    public Button cross, circle, triangle, square, up, down, left, right, RB, LB, RS, LS, share, touchpad, RT, LT;


    private final Gamepad gamepad;

    public Controller_0(Gamepad gamepad) {

        this.gamepad = gamepad;
        cross =  new Button();
        square =  new Button();
        triangle =  new Button();
        circle =  new Button();
        up =  new Button();
        down =  new Button();
        left =  new Button();
        right =  new Button();
        RB =  new Button();
        LB =  new Button();
        LS =  new Button();
        RS =  new Button();
        share =  new Button();
        touchpad =  new Button();
        RT = new Button();
        LT = new Button();

    }

    public void update(){

        cross.update(gamepad.cross); square.update(gamepad.square); triangle.update(gamepad.triangle); circle.update(gamepad.circle);
        up.update(gamepad.dpad_up); down.update(gamepad.dpad_down); left.update(gamepad.dpad_left); right.update(gamepad.dpad_right);
        RB.update(gamepad.right_bumper); LB.update(gamepad.left_bumper); LS.update(gamepad.left_stick_button); RS.update(gamepad.right_stick_button);
        share.update(gamepad.share); touchpad.update(gamepad.touchpad); RT.update(gamepad.right_trigger); LT.update(gamepad.left_trigger);

    }

    public Point leftStick(){
        return new Point(gamepad.left_stick_x, gamepad.left_stick_y);
    }

    public Point rightStick(){
        return new Point(gamepad.right_stick_x, gamepad.right_stick_y);
    }

    public class Button {

        boolean isPressed = false;
        boolean lastPressed = false;
        boolean toggle = false;
        boolean isTapped = false;
        float rawValue;

        public void update(boolean button){

            isPressed = button;

            isTapped = isPressed && !lastPressed;

            if(isTapped){
                toggle = !toggle;
            }

            lastPressed = isPressed;
        }

        public void update(float trigger){

            update(trigger >= .4);
            rawValue = trigger;

        }

        public float getRawValue(){
            return rawValue;
        }

        public boolean isTapped() {
            return isTapped;
        }

        public boolean isToggle() {
            return toggle;
        }

        public boolean isPressed() {
            return isPressed;
        }

    }

    }