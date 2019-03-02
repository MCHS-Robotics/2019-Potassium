package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor collection = null;
    private DcMotor lift = null;
    private Servo jewel = null;

    public Robot(DcMotor left2, DcMotor right2, DcMotor collection2, DcMotor lift2, Servo jewel2){
        left = left2;
        right = right2;
        collection = collection2;
        lift = lift2;
        jewel = jewel2;

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void forward(int in){
        int position = (int)(1120/4*Math.PI*in);

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setTargetPosition(position);
        right.setTargetPosition(position);
        left.setPower(.75);
        right.setPower(.75);
        while(left.isBusy()){

        }
    }
    public void backward(int in){
        forward(-in);
    }
}
