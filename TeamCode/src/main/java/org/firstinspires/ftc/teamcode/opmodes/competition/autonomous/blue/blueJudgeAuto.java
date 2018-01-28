package org.firstinspires.ftc.teamcode.opmodes.competition.autonomous.blue;

import android.util.Log;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

import static org.firstinspires.ftc.teamcode.resources.constants.*;
import static org.firstinspires.ftc.teamcode.resources.functions.*;
import static org.firstinspires.ftc.teamcode.resources.hardware.*;

/**
 * Created by dansm on 12/21/2017.
 */

@Autonomous(name="BLUEJudgeAuto")
public class blueJudgeAuto extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException{

        //Code to run after init is pressed

        initHardwareMap(hardwareMap);

        initServos(AUTONOMOUS);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";

        imuSensor.initialize(parameters);

        double distanceToWall = readAndFilterRangeSensor(this);


        while(!isStarted()) {
            distanceToWall = readAndFilterRangeSensor(this);
            telemetry.addData("Distance to wall", distanceToWall);
            telemetry.update();
        }



        waitForStart();
        //Code to run after play is pressed

        //detect the VuMark
        telemetry.addData("Vumark:", "Initializing");
        telemetry.update();
        RelicRecoveryVuMark vuMark = getVumark(this, hardwareMap);
        telemetry.addData("Vumark:", vuMark.toString());
        telemetry.update();

        //get the jewel
        lowerJewelArms(this);
        JDColor jewelColor = detectJewelColor(this );
        knockJewel(jewelColor, JDColor.BLUE, this);
        raiseJewelArms(this);

        sleep(300);

        //grab the block
        closeGrabber(BOTH_GRABBERS);

        sleep(500);

        moveLiftForTime(GLYPH_LIFT_AUTO_SPEED, 1500, this);

        sleep(100);

        moveForTime(0.3, 1150, this);

        turn(90, this);

        sleep(100);

        moveForTime(0.3, 500, this);

        sleep(100);

        distanceToWall = readAndFilterRangeSensor(this);

        Log.d("JDRange", Double.toString(distanceToWall));

        //go to cryptobox
        moveUntilCryptoWallv2(distanceToWall,vuMark, JDColor.BLUE, FIELD_SIDE.JUDGE_SIDE, this);

        turn(0, this);

        depositGlyph(this);

        //time to look for the second and third glyph

    }
}
