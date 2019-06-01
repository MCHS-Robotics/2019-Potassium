package org.firstinspires.ftc.teamcode;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@TeleOp(name = "Potassium TeleOp", group = "Pushbot")
//@Disabled
public class PotassiumTeleOp extends LinearOpMode {
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor collection = null;
    private DcMotor lift = null;
    private Servo jewel = null;
    private ColorSensor sensorColor;
    private DistanceSensor sensorDistance;

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = " ATiuXRv/////AAABmX/tug8P6EteuRJz2PTAi6JMBeLa9Te+gCRaTBPeDZ77UloArIT7REsZPIosl4YG0JLDyl4+yj3lpzfzKIkpOQNRfsgfAjS6tTbwBRHJsnStRDKMwb4Fj5l3rTCxB8qHn8GW45O1BGLuAROQ+DrNs26ktJV3HTEr6N4XYXSdDD3UX+2Yj8u4CmJ6xk4kY0JdX/Kklw4Ai0Mba5vFviXXjue5UMQRZTIy45y2h8UpEcSFeqiLLKdGktA5qL5NufN0/KZXI3EQNHjmrAi52oqWiO7JBAolc9uC7B910YGiGI6E0a/KJAvxLY6zlKuXI+XkQP9WgGwfXUZhU8nTyKnEDi4HY0v/+uSmKfcyIrDWf2KW";

    //    private VuforiaLocalizer vuforia;
//    private TFObjectDetector tfod;
//
//    private int position;
//    private int angle;
//    private int distance;
//    private int objectWidth;
    private int isRight = 0;


    @Override
    public void runOpMode() {

//        initVuforia();
//        initTfod();

        Robot robot = new Robot(left, right, collection, lift, jewel, telemetry, sensorColor, hardwareMap);

//        if (tfod != null) {
//            /** Activate Tensor Flow Object Detection. */
//            tfod.activate();
//        }

        waitForStart();

        robot.turn70counterclockwise();

        robot.turnOnMotorsBackward();
        robot.getLeft().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.getLeft().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.getRight().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.getRight().setMode(DcMotor.RunMode.RUN_USING_ENCODER);

// move until sees beacon or moves more than 4 inches
        while (!robot.isBeaconThere() && robot.getLeft().getCurrentPosition() * (1 / robot.intoTicks) > -4) {
            telemetry.addData("Position in in: ", robot.getLeft().getCurrentPosition() * (1 / robot.intoTicks));
            telemetry.update();
        }
        sleep(2000);
        robot.turnOffMotors();

        robot.getLeft().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.getLeft().setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Dye color = robot.senseColorAndDistance();
        // for blue side
        if (color == Dye.RED) {
            isRight = 2;
        } else if (color == Dye.BLUE) {
            isRight = 1;
        }
        else if (color == Dye.NONE){
            isRight = 0;
        }
        telemetry.addData("isRight: ", isRight);
        sleep(2000);


        while (opModeIsActive()) {
            robot.turn(45);
            robot.forward(49);
            robot.turn(45);
            robot.forward(60);
            robot.turn(90);
            robot.forward(63);
            robot.turn(90);
            robot.forward(60);
            robot.turn(40);
            robot.forward(49);
            robot.turn(48);

            robot.turnOnMotorsBackward();

            while (!robot.isBeaconThere()) {
                telemetry.update();
            }
            robot.turnOffMotors();

        if (isRight == 0){
            color = robot.senseColorAndDistance();
        // for blue side
            // 0 - no color, 1 - true, 2 - false
            if (color == Dye.RED) {
            isRight = 2;
            } else if (color == Dye.BLUE) {
            isRight = 1;
            }
            else if (color == Dye.NONE){
            isRight = 0;
            }
        telemetry.addData("isRight: ", isRight);
        }

            sleep(3000);
            robot.dropOff();
        }



    }
}



//WEBCAM SORCERY:

//Probably useless webcam ideas:
//        detect(robot);
//        robot.turn(angle);
//        robot.forward(objectWidth / 2);


//    private void detect(Robot robot) {
//        if (tfod != null) {
//
//            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions() ;
//            while(opModeIsActive()) {
//                    updatedRecognitions = tfod.getUpdatedRecognitions();
//                    if(updatedRecognitions != null && updatedRecognitions.size() != 0) {
//                        position = (int) (updatedRecognitions.get(0).getLeft() + updatedRecognitions.get(0).getWidth() / 2);
//                        angle = (int) (updatedRecognitions.get(0).estimateAngleToObject(AngleUnit.DEGREES));
//
//                        telemetry.addData("angle: ", angle);
//                        telemetry.update();
//                        robot.turn(angle);
//                        objectWidth = (int)(updatedRecognitions.get(0).getWidth());
//                        telemetry.addData("object width: ", objectWidth);
//
//                        sleep(6000);
//                        break;
//                    }
//
//            }
//
//
//
//
////                if (updatedRecognitions != null) {
////
////                }
//        }
//    }
//
//    private void initVuforia() {
//        /*
//         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
//         */
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
//
//        parameters.vuforiaLicenseKey = VUFORIA_KEY;
//        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
//
//        //  Instantiate the Vuforia engine
//        vuforia = ClassFactory.getInstance().createVuforia(parameters);
//
//        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
//    }
//
//    /**
//     * Initialize the Tensor Flow Object Detection engine.
//     */
//    private void initTfod() {
//        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
//                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
//        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
//        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
//    }
//}