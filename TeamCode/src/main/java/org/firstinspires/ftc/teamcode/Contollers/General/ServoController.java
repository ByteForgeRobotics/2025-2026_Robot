package org.firstinspires.ftc.teamcode.Contollers.General;

import com.qualcomm.robotcore.hardware.Servo;

public class ServoController {
    private final Servo servo;
    private final double position;
    private boolean isRunning = false;
    private double targetPosition;

    public ServoController(Servo servo) {
        this.servo = servo;
        this.position = servo.getPosition();
    }

    public void setPosition(double targetPosition) {
        this.isRunning = true;
        this.targetPosition = targetPosition;
    }

    public void update() {
        if (!this.isRunning) {
            servo.setPosition(this.targetPosition);
        } else {
            double delta = Math.abs(this.targetPosition - this.position);
            if (delta < 0.01) {
                this.isRunning = false;
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
