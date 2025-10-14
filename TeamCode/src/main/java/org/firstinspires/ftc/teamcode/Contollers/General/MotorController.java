package org.firstinspires.ftc.teamcode.Contollers.General;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MotorController {
    private final DcMotor motor;
    private final ElapsedTime timer;

    private boolean isRunning = false;
    private double runTime = 0;
    private double power = 0;

    private boolean isEncoderMode = false;
    private int targetPosition = 0;

    public MotorController(DcMotor motor) {
        this.motor = motor;
        this.timer = new ElapsedTime();
    }

    public void runMotor(double timeSeconds, double speed) {
        this.runTime = timeSeconds;
        this.power = speed;
        this.timer.reset();
        this.isRunning = true;
        this.isEncoderMode = false;
        this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.motor.setPower(speed);
    }

    public void setPower(double speed) {
        this.power = speed;
        this.isRunning = true;
        this.runTime = -1;
        this.isEncoderMode = false;
        this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.motor.setPower(speed);
    }

    public void runToPosition(int ticks, double speed) {
        this.isRunning = true;
        this.isEncoderMode = true;
        this.targetPosition = ticks;
        // Configure
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setTargetPosition(ticks);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(speed);
    }

    public void resetEncoder() {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    }

    public void update() {
        if (isRunning) {
            if (isEncoderMode) {
                if (!motor.isBusy()) {
                    motor.setPower(0);
                    isRunning = false;
                }
            } else if (runTime == -1) {
                //pass
            } else if (timer.seconds() >= runTime) {
                motor.setPower(0);
                isRunning = false;
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stop() {
        motor.setPower(0);
        isRunning = false;
    }
}
