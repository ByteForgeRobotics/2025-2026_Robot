package org.firstinspires.ftc.teamcode.TeleOp.Controller;

import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveController {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private double fl, fr, bl, br;

    public DriveController(HardwareMap hardwareMap) {
        // TODO: reconfig names in diver hub to, frontLeft, frontRight, backLeft, backRight for road runner to work
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        frontRight = hardwareMap.get(DcMotor.class, "FR");
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");
    }

    public void update() {
        frontLeft.setPower(-fl);
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
