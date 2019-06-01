package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="ActualTeleOp", group="Iterative Opmode")
//@Disabled
    public class ActualTeleOp extends OpMode {

        private DcMotor lift = null;
        private DcMotor right = null;
        private DcMotor left = null;
        private DcMotor collect = null;



        @Override
        public void init() {
            lift = hardwareMap.get(DcMotor.class, "lift");
            left = hardwareMap.get(DcMotor.class, "left");
            right = hardwareMap.get(DcMotor.class, "right");
            collect = hardwareMap.get(DcMotor.class, "collect");

            left.setDirection(DcMotorSimple.Direction.REVERSE);

        }
        @Override
        public void loop() {

            if(gamepad2.right_trigger > 0.2)
            {
                lift.setPower(1);
            }
            else if(gamepad2.left_trigger > 0.2)
            {
                lift.setPower(-1);
            }
            else lift.setPower(0);


            if(gamepad2.left_stick_y >0.2) {
                right.setPower(-0.6);
                left.setPower(-0.7);
            }
            else if(gamepad2.left_stick_y<-0.2) {
                left.setPower(0.7);
                right.setPower(0.7);
            }
            else {
                left.setPower(0);
                right.setPower(0);
            }

            if(gamepad2.right_bumper)
            {
                collect.setPower(1);
            }
            else if(gamepad2.left_bumper)
            {
                collect.setPower(-1);
            }
            else collect.setPower(0);


            if(gamepad2.right_stick_x >0.2) {
                right.setPower(-0.7);
                left.setPower(0.7);
            }
            else if(gamepad2.right_stick_x<-0.2) {
                left.setPower(-0.7);
                right.setPower(0.7);
            }
            else {
                left.setPower(0);
                right.setPower(0);
            }

        }

    }

