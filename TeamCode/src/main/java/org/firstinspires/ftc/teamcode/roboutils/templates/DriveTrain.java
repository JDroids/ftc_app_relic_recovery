package org.firstinspires.ftc.teamcode.roboutils.templates;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.ArrayList;

/**
 * Created by dansm on 3/22/2018.
 */

public abstract class DriveTrain extends Subsystem {
    public ArrayList<DcMotorEx> motors;

    public ArrayList<Double> motorSpeeds;

    public void update() {
        for (DcMotorEx motor : motors) {
            motor.setPower(motorSpeeds.get(motors.indexOf(motor)));
        }
    }
}
