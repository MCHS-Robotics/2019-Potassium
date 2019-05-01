package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

    @Autonomous(name="Potassium TeleOp", group="Pushbot")
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

        private VuforiaLocalizer vuforia;
        private TFObjectDetector tfod;

        private int position;
        private int angle;


        @Override
        public void runOpMode() {


            Robot robot = new Robot(left, right, collection, lift, jewel, telemetry, sensorColor, hardwareMap);

            if (tfod != null) {
                /** Activate Tensor Flow Object Detection. */
                tfod.activate();
            }

            waitForStart();

            detect();
            robot.turn(angle);



        }

        private void detect() {
            if (tfod != null) {
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

                position = (int)(updatedRecognitions.get(0).getLeft() + updatedRecognitions.get(0).getWidth() / 2);
                angle = (int)(updatedRecognitions.get(0).estimateAngleToObject(AngleUnit.DEGREES));


//                if (updatedRecognitions != null) {
//
//                }
            }
        }
        private void initVuforia() {
            /*
             * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
             */
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

            parameters.vuforiaLicenseKey = VUFORIA_KEY;
            parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

            //  Instantiate the Vuforia engine
            vuforia = ClassFactory.getInstance().createVuforia(parameters);

            // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
        }

        /**
         * Initialize the Tensor Flow Object Detection engine.
         */
        private void initTfod() {
            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
        }
    }
}
