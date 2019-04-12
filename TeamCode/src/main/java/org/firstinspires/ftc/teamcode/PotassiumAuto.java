

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;


@Autonomous(name="Pushbot: Auto Drive By Encoder", group="Pushbot")
//@Disabled
public class PotassiumAuto extends LinearOpMode {
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor collection = null;
    private DcMotor lift = null;
    private Servo jewel = null;
    private NormalizedColorSensor colorSensor = null;


    @Override
    public void runOpMode() {
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
//        collection = hardwareMap.get(DcMotor.class, "collection");
//        lift = hardwareMap.get(DcMotor.class, "lift");
//        jewel = hardwareMap.get(Servo.class, "jewel");
//        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        Robot robot = new Robot(left, right, collection, lift, jewel, colorSensor, telemetry);

        waitForStart();

        robot.forward(130);
        robot.backward(5);
        robot.turn(90);
        robot.backward(55);
        //color sensor
        sleep(3000);

        robot.turn(90);
        robot.forward(55);

        robot.turn(270);



    }
}
