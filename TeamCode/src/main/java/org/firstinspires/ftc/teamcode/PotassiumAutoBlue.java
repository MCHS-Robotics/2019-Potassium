

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.hardware.DistanceSensor;


@Autonomous(name="Potassium Auto Blue", group="Pushbot")
//@Disabled
public class PotassiumAutoBlue extends LinearOpMode {
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

        robot.resetLift();

        waitForStart();



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
// for blue side
        if (color == Dye.BLUE){
            robot.backward(20); //guess again
            robot.lift(9);
            sleep(2000);
            robot.lift(-9);
            robot.forward(20);
        }
        else if (color == Dye.RED){
            robot.forward(30); //guess
            robot.lift(9);
            sleep(2000);//dump marker
            robot.lift(-9);
            robot.backward(30);
        }
        else{
            robot.forward(30); //guess
            robot.lift(9);
            sleep(2000);//dump marker
            robot.lift(-9);
            robot.backward(30);
        }
        robot.turn(100);
        robot.forward(50);

        robot.turn(25);

        robot.backward(54);
        sleep(300);

        robot.turn(-100);



    }
}
