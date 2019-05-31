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

        waitForStart();


        robot.lift(9);
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
            robot.backward(20); //guess again
            sleep(2000);
            robot.forward(20);
        }
        else if (color == Dye.RED){
            robot.forward(30); //guess
            sleep(2000);//dump marker
            robot.backward(30);
        }
        else{
            robot.forward(30); //guess
            sleep(2000);//dump marker
            robot.backward(30);
        }
        robot.turn(100);
        robot.lift(-9);
        robot.forward(50);

        robot.turn(25);

        robot.backward(54);
        sleep(300);

        robot.turn(-100);



    }
}
