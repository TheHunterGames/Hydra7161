package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.MotorConfiguration;

/**
 * 9/12/2016 Created this so we can test teleOp and because Arib has been getting a lot of shade because I did not code last year
 */
public class teleOpHunt extends OpMode {
    DcMotor motorFL;
    DcMotor motorFR;
    DcMotor motorBL;
    DcMotor motorBR;
    Servo beaconLeftLeft;
    Servo beaconLeftRight;
    Servo beaconRightLeft;
    Servo beaconRightRight;

    @Override
    public void init(){
        motorFL = hardwareMap.dcMotor.get("FL");
        motorFR = hardwareMap.dcMotor.get("FR");
        motorBL = hardwareMap.dcMotor.get("BL");
        motorBR = hardwareMap.dcMotor.get("BR");
        beaconLeftLeft = hardwareMap.servo.get("LL");
        beaconLeftRight = hardwareMap.servo.get("LR");
        beaconRightLeft = hardwareMap.servo.get("RL");
        beaconRightRight = hardwareMap.servo.get("RR");
        beaconLeftLeft.setPosition(0);
        beaconLeftRight.setPosition(0);
        beaconRightLeft.setPosition(0);
        beaconRightRight.setPosition(0);
    }

    @Override
    public void loop(){
        if (Math.abs(gamepad1.right_stick_y) > .05 || Math.abs(gamepad1.left_stick_y) > .05){
            motorFL.setPower(gamepad1.left_stick_y);
            motorFR.setPower(gamepad1.left_stick_y);
            motorBL.setPower(gamepad1.right_stick_y);
            motorBR.setPower(gamepad1.right_stick_y);
        }else{
            motorFL.setPower(0);
            motorFR.setPower(0);
            motorBL.setPower(0);
            motorBR.setPower(0);
        }

        if (gamepad1.a){
            beaconLeftLeft.setPosition(1);
        }

        if (gamepad1.b){
            beaconLeftRight.setPosition(1);
        }

        if (gamepad1.x){
            beaconRightLeft.setPosition(1);
        }

        if (gamepad1.y){
            beaconRightRight.setPosition(1);
        }


    }

    @Override
    public void stop(){
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }
}
