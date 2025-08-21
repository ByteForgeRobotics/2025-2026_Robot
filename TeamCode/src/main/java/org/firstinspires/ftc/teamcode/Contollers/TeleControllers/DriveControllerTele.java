package org.firstinspires.ftc.teamcode.Contollers.TeleControllers;

import org.firstinspires.ftc.teamcode.Contollers.MotorController;
public class DriveControllerTele {
    private MotorController frontLeft;
    private MotorController frontRight;
    private MotorController backLeft;
    private MotorController backRight;

    public DriveControllerTele(MotorController fl, MotorController fr, MotorController bl, MotorController br) {
        this.frontLeft = fl;
        this.frontRight = fr;
        this.backLeft = bl;
        this.backRight = br;
    }

    public void update() {
        frontLeft.update();
        frontRight.update();
        backLeft.update();
        backRight.update();
    }

    public void drive(double LeftX, double LeftY, double RightX) {
        double forward = -LeftY;
        double strafe = LeftX;
        double rotate = RightX;

        double fl = forward + strafe + rotate;
        double fr = forward - strafe - rotate;
        double bl = forward - strafe + rotate;
        double br = forward + strafe - rotate;

        double max = Math.max(1.0, Math.max(Math.abs(fl),
                Math.max(Math.abs(fr), Math.max(Math.abs(bl), Math.abs(br)))));

        fl /= max;
        fr /= max;
        bl /= max;
        br /= max;

        fl *= 1;
        fr *= 1;
        bl *= 1;
        br *= 1;

        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backLeft.setPower(bl);
        backRight.setPower(br);
    }


    public void stop() {
        frontLeft.stop();
        frontRight.stop();
        backLeft.stop();
        backRight.stop();
    }
}
