package org.firstinspires.ftc.teamcode.Contollers.AutoOnly;

import org.firstinspires.ftc.teamcode.Contollers.MotorController;

public class DriveControllerAuto {
    private MotorController frontLeft;
    private MotorController frontRight;
    private MotorController backLeft;
    private MotorController backRight;

    public DriveControllerAuto(MotorController fl, MotorController fr, MotorController bl, MotorController br) {
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

    public boolean isRunning() {
        return frontLeft.isRunning() || frontRight.isRunning()
                || backLeft.isRunning() || backRight.isRunning();
    }

    public void drive(double forward, double strafe, double rotate, double power, double timeSeconds) {
        double fl = forward + strafe + rotate;
        double fr = forward - strafe - rotate;
        double bl = forward - strafe + rotate;
        double br = forward + strafe - rotate;

        double max = Math.max(1.0, Math.abs(fl));
        max = Math.max(max, Math.abs(fr));
        max = Math.max(max, Math.abs(bl));
        max = Math.max(max, Math.abs(br));

        fl /= max;
        fr /= max;
        bl /= max;
        br /= max;

        fl *= power;
        fr *= power;
        bl *= power;
        br *= power;

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
