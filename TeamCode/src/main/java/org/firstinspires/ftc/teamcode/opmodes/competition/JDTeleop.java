package org.firstinspires.ftc.teamcode.opmodes.competition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.resources.functions.*;
import static org.firstinspires.ftc.teamcode.resources.hardware.*;
import static org.firstinspires.ftc.teamcode.resources.constants.*;


/**
 * Created by dansm on 12/7/2017.
 */

@TeleOp(name="JDTeleOp")

public class JDTeleop extends LinearOpMode{

    @Override

    public void runOpMode() throws InterruptedException{
        //Code to run after init is pressed
        boolean hardwareMapState;

        initHardwareMap(hardwareMap);

        waitForStart();
        //Code to run after play is pressed

        int controlledGrabber = BOTH_GRABBERS;

        initServos(TELEOP);

        moveLiftForTime(0.7, 500, this);
        moveSecondLiftForTime(0.7, 500, this);

        while(opModeIsActive()) {
            moveArcade(gamepad1);

            if(gamepad2.dpad_up){
                controlledGrabber = TOP_GRABBER;
            }
            else if(gamepad2.dpad_down){
                controlledGrabber = BOTTOM_GRABBER;
            }
            else if(gamepad2.dpad_left || gamepad2.dpad_right){
                controlledGrabber = BOTH_GRABBERS;
            }

            if (gamepad2.a) {
                closeGrabber(controlledGrabber);
            }
            else if (gamepad2.b){
                openGrabber(controlledGrabber);
            }
            else if (gamepad2.y) {
                openGrabberWide(controlledGrabber);
            }

            if(gamepad2.right_bumper){
                relicExtender.setPower(0.7);
            }
            else if(gamepad2.left_bumper){
                relicExtender.setPower(-0.7);
            }
            else{
                relicExtender.setPower(0);
            }

            firstLift(gamepad2, this);
            secondLift(gamepad2, this);
        }
    }
}
