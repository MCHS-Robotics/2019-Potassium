package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;


import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

public class Robot {
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor collection = null;
    private DcMotor lift = null;
    private Servo jewel = null;
    private Telemetry telemetry;
    private ColorSensor sensorColor;
    private DistanceSensor sensorDistance;
    double intoTicks = 1120 / (4 * Math.PI);

    int relativeLayoutId;
    final View relativeLayout;

    // hsvValues is an array that will hold the hue, saturation, and value information.
    float hsvValues[] = {0F, 0F, 0F};

    // values is a reference to the hsvValues array.
    final float values[] = hsvValues;

    // sometimes it helps to multiply the raw RGB values with a scale factor
    // to amplify/attentuate the measured values.
    final double SCALE_FACTOR = 255;




    public Robot(DcMotor left2, DcMotor right2, DcMotor collection2, DcMotor lift2,
                 Servo jewel2, Telemetry telemetry2, ColorSensor sensorColor2, HardwareMap hardwareMap) {
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
        sensorColor = hardwareMap.get(ColorSensor.class, "sensorColorDistance");
        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensorColorDistance");
//        collection = hardwareMap.get(DcMotor.class, "collection");
//        lift = hardwareMap.get(DcMotor.class, "lift");
//        jewel = hardwareMap.get(Servo.class, "jewel");

        telemetry = telemetry2;
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //collection.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left.setDirection(DcMotorSimple.Direction.REVERSE);

        relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

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

    public Dye senseColorAndDistance(){
        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);
        telemetry.addData("Distance (cm)",
                String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
        telemetry.addData("Alpha", sensorColor.alpha());
        telemetry.addData("Red  ", sensorColor.red());
        telemetry.addData("Green", sensorColor.green());
        telemetry.addData("Blue ", sensorColor.blue());
        telemetry.addData("Hue", hsvValues[0]);
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });
        telemetry.update();
        sleep(1000);
        Dye color = null;
        if ((sensorColor.red() - sensorColor.blue()) > 30){
            color = Dye.RED;
            telemetry.addData("Color Detected: ", "RED");
        }
        else if ((sensorColor.blue() - sensorColor.red()) > 30){
            color = Dye.BLUE;
            telemetry.addData("Color Detected: ", "BLUE");
        }
        else {
            color = Dye.NONE;
            telemetry.addData("Color Detected: ", "NONE");
        }
        telemetry.update();

        return color;

    }

    public boolean isBeaconThere(){
        if (sensorDistance.getDistance(DistanceUnit.CM) <= 15){
            telemetry.addData("Is Beakon There? ", "true");
            telemetry.update();

            return true;
        }
        else {
            telemetry.addData("Is Beakon There?", "false");
            telemetry.update();


            return false;
        }
    }

    public void turnOnMotorsBackward(){
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left.setPower(-0.5);
        right.setPower(-0.5);
    }

    public void turnOffMotors(){
        left.setPower(0);
        right.setPower(0);
    }

}
