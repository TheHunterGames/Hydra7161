package org.firstinspires.ftc.teamcode.Lernaean;



import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class LernaeanTeleOp extends LernaeanOpMode {
    boolean isrightRightOut = false;
    boolean isrightLeftOut = false;
    boolean isleftRightOut = false;
    boolean isleftLeftOut = false;

    @Override
    public void loop(){

        if(gamepad1.y){
            if (!isrightRightOut) {
                rightRightOut();
            } else {
                rightRightIn();
            }
        }

        if(gamepad1.x){
            if(!isrightLeftOut){
                rightLeftOut();
            } else {
                rightRightIn();
            }
        }

        if (gamepad1.b){
            if(!isleftRightOut){
                leftRightOut();
            } else {
                leftRightIn();
            }
        }

        if(gamepad1.a){
            if(!isleftLeftOut){
                leftLeftOut();
            } else {
                leftRightIn();
            }
        }

        if (Math.abs(gamepad1.right_stick_y) > .05 || (Math.abs(gamepad1.left_stick_x) > .05)){
            startMotors(gamepad1.right_stick_y,gamepad1.left_stick_y );
        } else {
            stopMotors();
        }

    }
}