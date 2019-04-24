

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.hardware.DistanceSensor;


@Autonomous(name="Pushbot: Auto Drive By Encoder", group="Pushbot")
//@Disabled
public class PotassiumAuto extends LinearOpMode {
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor collection = null;
    private DcMotor lift = null;
    private Servo jewel = null;
    private ColorSensor sensorColor;
    private DistanceSensor sensorDistance;


    @Override
    public void runOpMode() {


        Robot robot = new Robot(left, right, collection, lift, jewel, telemetry, sensorColor,hardwareMap);


        waitForStart();



        robot.forward(130);
        robot.turn(88);

        sleep(1000);
        robot.backward(12);

        while (!robot.isBeaconThere()){
            robot.turnOnMotors();
        }
        robot.turnOffMotors();

        Dye color = robot.senseColorAndDistance();

        if (color == Dye.RED){
            robot.forward(30); //guess
            //dump marker
            robot.backward(30);
        }
        else if (color == Dye.BLUE){
            robot.backward(20); //guess again
            //dump marker
            robot.forward(20);
        }
        sleep(3000);

        robot.turn(93);
        robot.forward(55);

        robot.turn(360);



    }
}
