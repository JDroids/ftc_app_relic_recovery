package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

import static org.firstinspires.ftc.teamcode.constants.*;
import static org.firstinspires.ftc.teamcode.functions.*;
import static org.firstinspires.ftc.teamcode.hardware.*;

/**
 * Created by dansm on 12/21/2017.
 */

@Autonomous(name="REDJudgeAuto")
public class redJudgeAuto extends LinearOpMode{

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

        int MaxValue = 255;
        double distanceToWall = sideRangeSensor.cmUltrasonic();;

        //filter bad data maximum value
        while ( !(distanceToWall < MaxValue) && opModeIsActive()) {
            distanceToWall = sideRangeSensor.cmUltrasonic();
            telemetry.addData("Distance to wall", distanceToWall);
            telemetry.update();
        }
        telemetry.addData("Distance to wall", distanceToWall);
        telemetry.update();

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
        knockJewel(jewelColor, JDColor.RED, this);
        raiseJewelArms(this);

        sleep(300);

        //grab the block
        closeGrabber(BOTH_GRABBERS);

        sleep(500);

        moveLiftForTime(GLYPH_LIFT_AUTO_SPEED, 1000, this);

        sleep(100);

        moveForTime(-0.3, 1150, this);

        turn(90, this);

        sleep(100);

        moveForTime(0.3, 500, this);

        sleep(100);

        //read the crypto wall after turning
        distanceToWall = sideRangeSensor.cmUltrasonic();
        while ( !(distanceToWall < MaxValue) && opModeIsActive()) {
            distanceToWall = sideRangeSensor.cmUltrasonic();
            telemetry.addData("Distance to wall", distanceToWall);
            telemetry.update();
        }

        Log.d("JDRange", Double.toString(distanceToWall));

        //go to cryptobox
        moveUntilCryptoWallv2(distanceToWall,vuMark, JDColor.RED, FIELD_SIDE.JUDGE_SIDE, this);

        turn(180, this);

        sleep(250);

        moveForTime(0.2, 1500, this);

        openGrabber(BOTH_GRABBERS);

        sleep(250);

        moveForTime(0.3, 3000, this);

        openGrabberWide(BOTTOM_GRABBER);

        sleep(250);

        moveLiftForTime(GLYPH_LIFT_AUTO_SPEED, 1000, this);

        moveForTime(-0.3, 500, this);

        moveLiftForTime(-GLYPH_LIFT_AUTO_SPEED, 750, this);

        moveForTime(0.3, 750, this);

        moveForTime(-0.3, 500, this);

        //time to look for the second and third glyph

    }
}
