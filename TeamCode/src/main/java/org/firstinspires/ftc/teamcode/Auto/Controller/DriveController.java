package org.firstinspires.ftc.teamcode.Auto.Controller;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveController {

    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;

    private double fl, fr, bl, br;

    public DriveController(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        backLeft = hardwareMap.get(DcMotorEx.class, "bl");
        backRight = hardwareMap.get(DcMotorEx.class, "br");

        // reverse if needed
        frontRight.setDirection(DcMotorEx.Direction.REVERSE);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);
    }

    public void update() {
        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backLeft.setPower(bl);
        backRight.setPower(br);
    }

    public void drive(double LeftX, double LeftY, double RightX) {
        double forward = -LeftY;
        double strafe = LeftX;
        double rotate = RightX;

        fl = forward + strafe + rotate;
        fr = forward - strafe - rotate;
        bl = forward - strafe + rotate;
        br = forward + strafe - rotate;

        double max = Math.max(1.0, Math.max(Math.abs(fl),
                Math.max(Math.abs(fr), Math.max(Math.abs(bl), Math.abs(br)))));

        fl /= max;
        fr /= max;
        bl /= max;
        br /= max;
    }

    public void stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}
