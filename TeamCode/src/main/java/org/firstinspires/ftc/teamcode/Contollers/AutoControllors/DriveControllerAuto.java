package org.firstinspires.ftc.teamcode.Contollers.AutoControllors;

import org.firstinspires.ftc.teamcode.Contollers.General.MotorController;
import org.firstinspires.ftc.teamcode.Contollers.General.NavXContoller;

public class DriveControllerAuto {
    private static final double ANGLE_THRESHOLD = 1.0;
    private static final double POSITION_THRESHOLD = 0.5;
    private final MotorController frontLeft;
    private final MotorController frontRight;
    private final MotorController backLeft;
    private final MotorController backRight;
    private final NavXContoller gyro;

    public DriveControllerAuto(MotorController fl, MotorController fr, MotorController bl, MotorController br, NavXContoller gyroController) {
        frontLeft = fl;
        frontRight = fr;
        backLeft = bl;
        backRight = br;
        gyro = gyroController;
    }

    public void update() {
        frontLeft.update();
        frontRight.update();
        backLeft.update();
        backRight.update();
    }

    public boolean isRunning() {
        return frontLeft.isRunning() || frontRight.isRunning() || backLeft.isRunning() || backRight.isRunning();
    }

    public void drive(double forward, double strafe, double rotate, double power) {
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

        fl *= -power;
        fr *= -power;
        bl *= -power;
        br *= -power;

        frontLeft.setPower(fl);
        frontRight.setPower(-fr);
        backLeft.setPower(bl);
        backRight.setPower(-br);
    }

    public void MoveToPosition(float newDx, float newDy, float newAngle, double currentX, double currentY) {
        float currentHeading = gyro.getYaw();
        double distanceX = newDx - currentX;
        double distanceY = newDy - currentY;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        double angleDifference = currentHeading - newAngle;

        while (Math.abs(angleDifference) > ANGLE_THRESHOLD) {
            currentHeading = gyro.getYaw();
            angleDifference = newAngle - currentHeading;
            double rotatePower = (angleDifference / 180.0);
            drive(0, 0, rotatePower, 1);
            if (Math.abs(angleDifference) > ANGLE_THRESHOLD) {
                stop();
            }
        }
        while (Math.abs(distance) > POSITION_THRESHOLD) {
            distanceX = newDx - currentX;
            distanceY = newDy - currentY;
            distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            double forward = distanceY / distance;
            double strafe = distanceX / distance;
            drive(forward, strafe, 0, 1);
            if (distance > POSITION_THRESHOLD) {
                stop();
            }
        }
        stop();
    }

    public void stop() {
        frontLeft.stop();
        frontRight.stop();
        backLeft.stop();
        backRight.stop();
    }
}
