package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Robot {
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor collection = null;
    private DcMotor lift = null;
    private Servo jewel = null;
    private NormalizedColorSensor colorSensor;
    private Telemetry telemetry;
    double intoTicks = 1120 / (4 * Math.PI);


    public Robot(DcMotor left2, DcMotor right2, DcMotor collection2, DcMotor lift2,
                 Servo jewel2, NormalizedColorSensor colorSensor2, Telemetry telemetry2) {
        left = left2;
        right = right2;
        collection = collection2;
        lift = lift2;
        jewel = jewel2;
        colorSensor = colorSensor2;
        telemetry = telemetry2;
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //collection.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left.setDirection(DcMotorSimple.Direction.REVERSE);

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
//            telemetry.addData("Left position", left.getCurrentPosition());
//            telemetry.addData("Right position", right.getCurrentPosition());
//            telemetry.addData("Left target position", left.getTargetPosition());
//            telemetry.update();
        }
        left.setPower(0);
        right.setPower(0);

    }

    public void backward(int in) {
        forward(-in);
    }

    public void turn(int degree) {
        double diameter = 15;
        double circumference = Math.PI * diameter;

        double v = degree / 360.0 * circumference * intoTicks;
        int position = (int) v;

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left.setTargetPosition(position);
        right.setTargetPosition(-position);
//        telemetry.addData("Left position", left.getCurrentPosition());
//        telemetry.addData("Right position", right.getCurrentPosition());
//        telemetry.addData("Left target position", left.getTargetPosition());
//        telemetry.addData("Right target position", right.getTargetPosition());
//        telemetry.addData("v", v);
//        telemetry.update();
        left.setPower(.75);
        right.setPower(.75);
        while (left.isBusy()) {
//            telemetry.addData("Left position", left.getCurrentPosition());
//            telemetry.addData("Right position", right.getCurrentPosition());
//            telemetry.addData("Left target position", left.getTargetPosition());
//            telemetry.update();
        }
        left.setPower(0);
        right.setPower(0);
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void collect(int sec) {
        collection.setPower(.75);
        sleep(500);
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