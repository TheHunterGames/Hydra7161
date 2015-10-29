/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc
All rights reserved.
Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:
Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.
Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.
NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.LightSensor;
//import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Autonomous mode
 * enable movement based on sensors and preset code
 */`
public class hydraAutonomousBlue extends LinearOpMode implements hydraDriveBase{
    //creates motors
    DcMotor motorBL;
    DcMotor motorBR;
    DcMotor motorFL;
    DcMotor motorFR;
    AdafruitIMU gyro;
    volatile double[] rollAngle = new double[2], pitchAngle = new double[2], yawAngle = new double[2];
    private static final String LOG_TAG = hydraAutonomousBlue.class.getSimpleName();
    DeviceInterfaceModule dim;
//    ColorSensor color;
    //  OpticalDistanceSensor distance
    ElapsedTime elapsedTime;
    public void startMotors(double power1, double power2, double power3, double power4) {
        motorBR.setPower(power1);
        motorBL.setPower(power2);
        motorFL.setPower(power3);
        motorFR.setPower(power4);
    }
    public int getEncoderAvg() {
        return (Math.abs(motorBL.getCurrentPosition()) + Math.abs(motorBR.getCurrentPosition()) + Math.abs(motorFL.getCurrentPosition()) + Math.abs(motorFR.getCurrentPosition())) / 4;
    }
    public void stopMotors() {
        motorBR.setPower(0);
        motorBL.setPower(0);
        motorFL.setPower(0);
        motorFR.setPower(0);
    }
    public void resetEncoders() {
        motorBL.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorBR.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorFR.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorFL.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
    }
    public void getEncoderValues() {
        telemetry.addData("motorBL", motorBL.getCurrentPosition());

        telemetry.addData("motorFR", motorFR.getCurrentPosition());

        telemetry.addData("motorBR", motorBR.getCurrentPosition());

        telemetry.addData("motorFL", motorFL.getCurrentPosition());
    }
    public void getTime() {
        telemetry.addData("time", elapsedTime.time());
    }
    @Override
    public void runOpMode() {
        elapsedTime = new ElapsedTime();
        dim = hardwareMap.deviceInterfaceModule.get("dim");
        try {
            gyro = new AdafruitIMU(hardwareMap, "gyro", (byte) (AdafruitIMU.BNO055_ADDRESS_A), (byte) AdafruitIMU.OPERATION_MODE_IMU);
        } catch (RobotCoreException e) {
            Log.e("SEVERE ERROR: ", "ROBOT CORE EXCEPTION", e);
        }
        gyro.startIMU();
        motorBL = hardwareMap.dcMotor.get("motorBL");
        motorBR = hardwareMap.dcMotor.get("motorBR");
        motorFL = hardwareMap.dcMotor.get("motorFL");
        elapsedTime.startTime();
        motorFR = hardwareMap.dcMotor.get("motorFR");
//        color = hardwareMap.colorSensor.get("color");
        int distance1 = 5550;
       // int distance2 = 7075; Dont need this if we are using gyros but im leaving this for future reference
        int distance3 = 12750;
        double currentAngle = yawAngle[0];
        while (motorBL.getCurrentPosition() < distance1) {
            startMotors(-1, 1, 1, -1);
            getEncoderValues();
        }
        gyro.getIMUGyroAngles(rollAngle, pitchAngle, yawAngle);
        currentAngle = yawAngle[0];
        while (currentAngle > -90.0) {
            startMotors(1, 1, 1, 1);
            gyro.getIMUGyroAngles(rollAngle, pitchAngle, yawAngle);
            currentAngle = yawAngle[0];
            telemetry.addData("yaw", currentAngle);
            getEncoderValues();
        }
        resetEncoders();
        while (motorBL.getCurrentPosition() < distance3) {
            startMotors(-1, 1, 1, -1);
            getEncoderValues();
        }
        stopMotors();
        elapsedTime.reset();
        double currentTime = 0.0;
        while (currentTime < 5.0) {
            stopMotors();
            getEncoderValues();
            currentTime = elapsedTime.time();
        }
        while (motorBL.getCurrentPosition() > 9000) { //9034
            startMotors(1, -1, -1, 1);
            getEncoderValues();
        }
        while (currentAngle > -135.0) {
            startMotors(1, 1, 1, 1);
            gyro.getIMUGyroAngles(rollAngle, pitchAngle, yawAngle);
            currentAngle = yawAngle[0];
            telemetry.addData("yaw", currentAngle);
            getEncoderValues();
        }
        elapsedTime.reset();
        currentTime = 0.0;
        while (currentTime < 5.0) {
            startMotors(-1, 1 ,1, -1);
            getEncoderValues();
            getTime();
            currentTime = elapsedTime.time();
        }
        stopMotors();
        motorBL.close();
        motorFL.close();
        motorBR.close();
        motorFR.close();
    }
}