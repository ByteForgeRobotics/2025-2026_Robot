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

    public void drive(double LeftX, double LeftY, double RightX, double timeSeconds) {
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

        fl *= 0.1;
        fr *= 0.1;
        bl *= 0.1;
        br *= 0.1;

        frontLeft.runMotor(timeSeconds, fl);
        frontRight.runMotor(timeSeconds, fr);
        backLeft.runMotor(timeSeconds, bl);
        backRight.runMotor(timeSeconds, br);
    }


    public void stop() {
        frontLeft.stop();
        frontRight.stop();
        backLeft.stop();
        backRight.stop();
    }
}
