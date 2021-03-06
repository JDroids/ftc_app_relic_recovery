package org.firstinspires.ftc.teamcode.roboutils.relicrecovery.opmodes.unittests;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.roboutils.customclasses.Waypoint;
import org.firstinspires.ftc.teamcode.roboutils.relicrecovery.commands.AutonomousMovement;
import org.firstinspires.ftc.teamcode.roboutils.relicrecovery.subsystems.RelicRecoveryRobot;
import org.firstinspires.ftc.teamcode.roboutils.templates.CustomOpMode;

/**
 * Created by dansm on 3/23/2018.
 */


@Autonomous(name="TurnTest", group="Tests")

public class TurnTest extends CustomOpMode {
    public void runOpMode() {
        robot = new RelicRecoveryRobot(this, new Waypoint(0, 0));
        robot.initHardware(this);

        Log.d("JDLog", "Passed hardware init");

        waitForStart();

        robot.initServosForTeleop();

        robot.update();

        sleep(1000);

        AutonomousMovement autonomousMovement = new AutonomousMovement();

        boolean gettingCoefficientsWirelessely = true;

        autonomousMovement.turn.run(this, 22.5, gettingCoefficientsWirelessely);

        autonomousMovement.turn.run(this, 45.0, gettingCoefficientsWirelessely);

        autonomousMovement.turn.run(this, 90.0, gettingCoefficientsWirelessely);

        autonomousMovement.turn.run(this, 180.0, gettingCoefficientsWirelessely);

        autonomousMovement.turn.run(this, 0.0, gettingCoefficientsWirelessely);
    }
}
