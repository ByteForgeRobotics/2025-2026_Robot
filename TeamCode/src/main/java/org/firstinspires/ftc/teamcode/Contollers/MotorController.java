package org.firstinspires.ftc.teamcode.Contollers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MotorController {
    private final DcMotor motor;
    private final ElapsedTime timer;

    private boolean isRunning = false;
    private double runTime = 0;
    private double power = 0;

    public MotorController(DcMotor motor) {
        this.motor = motor;
        this.timer = new ElapsedTime();
    }

    public void runMotor(double timeSeconds, double speed) {
        this.runTime = timeSeconds;
        this.power = speed;
        this.timer.reset();
        this.isRunning = true;
        this.motor.setPower(speed);
    }

    public void setPower(double speed) {
        this.power = speed;
        this.isRunning = true;
        this.runTime = -1;
        this.motor.setPower(speed);

    }

    public void update() {
        if (isRunning) {
            if (runTime == -1) {
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

/*
HOW TO USE MotorController:

1.  Instantiate the Controller:
    In your OpMode, create an instance of `MotorController`, passing the `DcMotor`
    object you want to control*/