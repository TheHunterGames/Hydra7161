package com.qualcomm.ftcrobotcontroller.opmodes.SecondQualifier;

import com.qualcomm.ftcrobotcontroller.opmodes.AutoMode;

/**
 * Created by Arib on 1/4/2016.
 */
public class Autonomous extends AutoMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitOneFullHardwareCycle();

        waitForStart();

        first();

        waitOneFullHardwareCycle();

        moveForward(.5);

        raiseLifts(.5, 1);

        dumpClimbers();

        sleep(1000);

        resetClimbers();

        sleep(1000);

        dumpClimbers();

        sleep(1000);

        resetClimbers();

        sleep(1000);
        //STRYKE

    }
}
