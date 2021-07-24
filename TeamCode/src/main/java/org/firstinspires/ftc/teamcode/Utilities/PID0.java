package org.firstinspires.ftc.teamcode.Utilities;

import org.firstinspires.ftc.teamcode.DashConstants.PIDConstants;
import org.firstinspires.ftc.teamcode.R;

import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.multTelemetry;

public class PID0 {

    //Initializes proportional, integral, and derivative weights
    private double proportionalWeight;
    private double integralWeight;
    private double derivativeWeight;



    //Initializes "integralSum", the sum off all the error values
    private double integralSum = 0;

    //Initializes previous error and time, which we use to calculate the rate of change
    private double previousError = 0;
    private long previousTime = System.currentTimeMillis();

    private int integralLength;

    private RingBuffer <Double> integralBuffer;

    private double pComponent, iComponent, dComponent, fComponent;


    //method constructor, inputs weights and creates the PID object
    public PID0(double proportional, double integral, double derivative, double fComponent, int integralLength) {
        this.proportionalWeight = proportional;
        this.integralWeight = integral;
        this.derivativeWeight = derivative;
        this.fComponent = fComponent;
        integralBuffer = new RingBuffer<Double>(3, 0.0);
        this.integralLength = integralLength;
    }

    //method constructor, inputs weights and creates the PID object
    public PID0(double proportional, double integral, double derivative, int integralLength) {
        this.proportionalWeight = proportional;
        this.integralWeight = integral;
        this.derivativeWeight = derivative;
        this.fComponent = 0;
        integralBuffer = new RingBuffer<Double>(3, 0.0);
        this.integralLength = integralLength;
    }

    //Update method, updates the error and returns the correction
    public double update(double error){

        integralSum += error; //Adds each returned error to the integralSum

        double deltaTime = (System.currentTimeMillis() - previousTime) / 1000.0; // Calculates the change of time in seconds NOT milliseconds
        double deltaError = error - previousError; // Calculates the difference in error
        double rateOfChange = deltaError / deltaTime; // Uses "deltaTime" and "deltaError" to find the rate of change

        pComponent = error * proportionalWeight; //Calculates the "pComponent" by multiplying the error by the weight
        iComponent = integralSum * integralWeight; //Calculates the "iComponent" by multiplying the sum by the weight
        dComponent = rateOfChange * derivativeWeight; // Calculates the "dComponent" by multiplying the rate of change by the weight

        // Sets previous error and time for the next loop
        previousError = error;
        previousTime = System.currentTimeMillis();


        return pComponent + iComponent + dComponent + fComponent; // Combines the p, i, and d components to finally return the correction
    }





    public double update(double error, boolean isTuning){

        integralSum += error; //Adds each returned error to the integralSum
        if(integralLength != 0) integralSum -= integralBuffer.updateCurWith(error);

        double deltaTime = (System.currentTimeMillis() - previousTime) / 1000.0; // Calculates the change of time in seconds NOT milliseconds
        double deltaError = error - previousError; // Calculates the difference in error
        double rateOfChange = deltaError / deltaTime; // Uses "deltaTime" and "deltaError" to find the rate of change

        pComponent = error * PIDConstants.propWeight; //Calculates the "pComponent" by multiplying the error by the weight
        iComponent = integralSum * PIDConstants.intWeight; //Calculates the "iComponent" by multiplying the sum by the weight
        dComponent = rateOfChange * PIDConstants.dervWeight; // Calculates the "dComponent" by multiplying the rate of change by the weight

        // Sets previous error and time for the next loop
        previousError = error;
        previousTime = System.currentTimeMillis();


        multTelemetry.addData("pComp", pComponent);
        multTelemetry.addData("iComp", iComponent);
        multTelemetry.addData("dComp", dComponent);
        multTelemetry.addData("fComp", fComponent);


        return pComponent + iComponent + dComponent + fComponent; // Combines the p, i, and d components to finally return the correction
    }

    public void setF(double f){
        fComponent = f;
    }

    public void setIntegralSum(double integralSum){
        this.integralSum = integralSum;
    }

}
