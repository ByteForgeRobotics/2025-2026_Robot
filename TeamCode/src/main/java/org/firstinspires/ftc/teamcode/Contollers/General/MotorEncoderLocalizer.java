package org.firstinspires.ftc.teamcode.Contollers.General;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Arrays;
import java.util.List;

public class MotorEncoderLocalizer extends ThreeTrackingWheelLocalizer {
    private final DcMotorEx leftFront, rightFront, leftBack, rightBack;

    // Encoder ticks to inches conversion parameters, adjust these to your hardware setup
    public static double TICKS_PER_REV = 537.6;  // Example for GoBilda 5202 motors with encoders
    public static double WHEEL_RADIUS = 2;       // Wheel radius in inches; adjust accordingly
    public static double GEAR_RATIO = 1;         // Gear ratio if any between motor and wheels

    private static double encoderTicksToInches(int ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    // In motor encoder localization, the "three wheels" are the four drive motors themselves:
    // so we define three "virtual" wheels for forward, lateral, and heading calculations:
    // Here we provide placeholder positions just to satisfy the superclass constructor
    public MotorEncoderLocalizer(HardwareMap hardwareMap) {
        // Since we don't have physical odometry wheels, these poses are dummy
        super(Arrays.asList(
                new Pose2d(0, 0, 0),
                new Pose2d(0, 0, 0),
                new Pose2d(0, 0, 0)
        ));

        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
    }

    @Override
    public List<Double> getWheelPositions() {
        // Calculate average left and right distances from respective motor encoders
        double leftDistance = (encoderTicksToInches(leftFront.getCurrentPosition()) +
                encoderTicksToInches(leftBack.getCurrentPosition())) / 2.0;
        double rightDistance = (encoderTicksToInches(rightFront.getCurrentPosition()) +
                encoderTicksToInches(rightBack.getCurrentPosition())) / 2.0;

        // For a mecanum or holonomic drivetrain without external lateral encoder,
        // estimate lateral movement is zero or derive from other means if possible
        double lateralDistance = 0;

        // Return list simulating [left, right, lateral] encoder distances
        return Arrays.asList(leftDistance, rightDistance, lateralDistance);
    }
}
