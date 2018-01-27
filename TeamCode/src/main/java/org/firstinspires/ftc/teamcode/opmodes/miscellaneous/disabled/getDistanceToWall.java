package org.firstinspires.ftc.teamcode.opmodes.miscellaneous.disabled;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.resources.constants.*;
import static org.firstinspires.ftc.teamcode.resources.functions.*;
import static org.firstinspires.ftc.teamcode.resources.hardware.*;

/**
 * Created by dansm on 1/19/2018.
 */

@Disabled
@TeleOp(name="Get Distance To Wall")

public class getDistanceToWall extends LinearOpMode{
    public void runOpMode(){
        double distance;
        ElapsedTime mRuntime = new ElapsedTime();

        waitForStart();

        mRuntime.reset();
        while(opModeIsActive()){
            distance = readAndFilterRangeSensor(this);
            Log.d("JDDistance", "Time: " + Double.toString(mRuntime.milliseconds()) + " Distance: " + Double.toString(distance));
            telemetry.addData("Distance To Wall", Double.toString(distance));
            telemetry.update();
        }
    }
}