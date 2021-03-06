package org.firstinspires.ftc.teamcode.opmodes.unittests;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.resources.JewelDetectionOpenCV;
import org.firstinspires.ftc.teamcode.resources.external.ClosableVuforiaLocalizer;

import static org.firstinspires.ftc.teamcode.resources.functions.getVumark;
import static org.firstinspires.ftc.teamcode.resources.functions.initVuforia;

/**
 * Created by dansm on 2/10/2018.
 */

@Autonomous(name = "VisionTest")
public class VisionTest extends LinearOpMode {
    @Override

    public void runOpMode() {
        JewelDetectionOpenCV jewelVision = new JewelDetectionOpenCV();
        // can replace with ActivityViewDisplay.getInstance() for fullscreen
        jewelVision.init(hardwareMap.appContext, CameraViewDisplay.getInstance(), 1);

        ElapsedTime mRuntime = new ElapsedTime();


        mRuntime.reset();

        jewelVision.enable();
        while (!isStarted()) {
            telemetry.addData("Time Elapsed", mRuntime.milliseconds());
            telemetry.addData("Jewel On Left", jewelVision.jewelOnLeft);
            telemetry.update();
        }

        jewelVision.disable();

        waitForStart();

        ClosableVuforiaLocalizer vuforia = initVuforia(hardwareMap);

        getVumark(vuforia, this);

    }

}
