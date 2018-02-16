package org.firstinspires.ftc.teamcode.opmodes.competition.autonomous.red;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

import static org.firstinspires.ftc.teamcode.resources.constants.*;
import static org.firstinspires.ftc.teamcode.resources.constants.GRABBERS.BOTH_GRABBERS;
import static org.firstinspires.ftc.teamcode.resources.functions.*;
import static org.firstinspires.ftc.teamcode.resources.hardware.*;

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


        double distanceToWall = readAndFilterRangeSensorValues(sideRangeSensor, this);

        while(!isStarted()) {
            distanceToWall = readAndFilterRangeSensorValues(sideRangeSensor, this);
            telemetry.addData("Distance to wall", distanceToWall);
            telemetry.addData("Rear Range: ", rearRangeSensor.cmUltrasonic());
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
        knockJewel(jewelColor, JDColor.RED, this);
        raiseJewelArms(this);

        sleep(300);

        //grab the block
        closeGrabber(BOTH_GRABBERS);

        sleep(500);

        moveFirstLiftForTime(GLYPH_LIFT_AUTO_SPEED, 1500, this);

        sleep(100);

        //moveEncoders(-36, -0.7, this); //To get off the balancing stone; inaccurate should be changed

        moveToDistanceUltrasonic(rearRangeSensor,38,-0.25,this);//this is in place of moveEncoders

        sleep(100);//you can also stop the robot to make everything a bit cleaner...

        move(0,0,0,0);//stops to make the turn better

        sleep(200);//Matt added this sleep to allow robot to stop and make the position better

        turn(90, this);

        sleep(100);

        //go to cryptobox
        moveToCryptoColumnEncoders(vuMark, JDColor.RED, FIELD_SIDE.JUDGE_SIDE, this);

        turn(180, this);

        depositGlyph(this);


        //time to look for the second and third glyph


    }
}
