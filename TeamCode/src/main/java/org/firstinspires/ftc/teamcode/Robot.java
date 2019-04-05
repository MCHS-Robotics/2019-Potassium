package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor collection = null;
    private DcMotor lift = null;
    private Servo jewel = null;

    double intoTicks = 1120 / 4 * Math.PI;

    public Robot(DcMotor left2, DcMotor right2, DcMotor collection2, DcMotor lift2, Servo jewel2) {
        left = left2;
        right = right2;
        collection = collection2;
        lift = lift2;
        jewel = jewel2;


    }

    public void forward(int in) {
        int position = (int) (intoTicks * in);

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left.setTargetPosition(position);
        right.setTargetPosition(position);
        left.setPower(.75);
        right.setPower(.75);
        while (left.isBusy()) {

        }
    }

    public void backward(int in) {
        forward(-in);
    }

    public void turn(int degree) {
        double diameter = 20;
        double circumference = Math.PI * diameter;

        int position = (int) (degree / 360 * circumference * intoTicks);

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left.setTargetPosition(position);
        right.setTargetPosition(-position);
        left.setPower(.75);
        right.setPower(.75);
        while (left.isBusy()) {
        }
    }

    public void collect(int sec) {
        collection.setPower(.75);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        collection.setPower(0);
    }

    public void lift(){
        int position = (int)(intoTicks * 12);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setTargetPosition(position);
        lift.setPower(.75);
        while (left.isBusy()) {
        }
    }
}