package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.hardware.DistanceSensor;


@Autonomous(name="Potassium Auto Red", group="Pushbot")
//@Disabled
public class PotassiumAutoRed extends LinearOpMode {
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor collect = null;
    private DcMotor lift = null;
    private Servo jewel = null;
    private ColorSensor sensorColor;
    private DistanceSensor sensorDistance;

    @Override
    public void runOpMode() {


        Robot robot = new Robot(left, right, collect, lift, jewel, telemetry, sensorColor,hardwareMap);

        robot.initLift();

        waitForStart();


        robot.lift(6);
        robot.forward(130);
        sleep(500);
        robot.backward(8);
        robot.turn(90);

        robot.turnOnMotorsBackward();

        while (!robot.isBeaconThere()){
            telemetry.update();
        }
        robot.turnOffMotors();

        Dye color = robot.senseColorAndDistance();
// for red side
        if (color == Dye.BLUE){
            robot.backward(20);
            robot.jewelDeploy();
            robot.forward(20);
        }
        else if (color == Dye.RED){
            robot.forward(30);
            robot.jewelDeploy();
            robot.backward(30);
        }
        else{
            robot.forward(30);
            robot.jewelDeploy();
            robot.backward(30);
        }
        robot.turn(100);
        robot.lift(-9);
        robot.forward(45);

        robot.turn(25);

        robot.backward(50);


    }
}
