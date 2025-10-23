package org.firstinspires.ftc.teamcode.Contollers.AutoControllors;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;

import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TranslationalVelocityConstraint;

import com.acmerobotics.roadrunner.control.PIDCoefficients;

import com.acmerobotics.roadrunner.followers.TrajectoryFollower;
import com.acmerobotics.roadrunner.followers.HolonomicPIDVAFollower;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import java.util.Arrays;

public class DriveControllerAuto {

    // Motors and sensor
    private final DcMotorEx leftFront, leftBack, rightBack, rightFront;
    private final VoltageSensor voltageSensor;

    // Dashboard
    private final FtcDashboard dashboard = FtcDashboard.getInstance();

    // Pose and localizer
    private Pose2d currentPose = new Pose2d();
    private final ThreeTrackingWheelLocalizer localizer;

    // Trajectory follower
    private final TrajectoryFollower follower;

    // Robot specific parameters (units in inches)
    private final double trackWidth;
    private final double wheelBase;

    // Wheel positions relative to robot center
    private final Vector2d frontLeftPos;
    private final Vector2d frontRightPos;
    private final Vector2d backLeftPos;
    private final Vector2d backRightPos;

    // Trajectory constraints
    private static final double MAX_WHEEL_VEL = 50.0;      // inches/sec
    private static final double MAX_ACCEL = 30.0;          // inches/sec^2
    private static final double MAX_ANG_VEL = Math.PI;     // rad/sec

    // PID coefficients for follower
    private static final PIDCoefficients TRANSLATIONAL_PID = new PIDCoefficients(5, 0, 0);
    private static final PIDCoefficients HEADING_PID = new PIDCoefficients(5, 0, 0);

    // Constructor
    public DriveControllerAuto(HardwareMap hardwareMap, ThreeTrackingWheelLocalizer localizer,
                               double trackWidth, double wheelBase) {
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        // Typical mecanum drive reverses right side
        rightFront.setDirection(DcMotorEx.Direction.REVERSE);
        rightBack.setDirection(DcMotorEx.Direction.REVERSE);

        voltageSensor = hardwareMap.voltageSensor.iterator().hasNext()
                ? hardwareMap.voltageSensor.iterator().next()
                : null;

        this.trackWidth = trackWidth;
        this.wheelBase = wheelBase;

        this.frontLeftPos = new Vector2d(wheelBase / 2, trackWidth / 2);
        this.frontRightPos = new Vector2d(wheelBase / 2, -trackWidth / 2);
        this.backLeftPos = new Vector2d(-wheelBase / 2, trackWidth / 2);
        this.backRightPos = new Vector2d(-wheelBase / 2, -trackWidth / 2);

        this.localizer = localizer;
        this.follower = new HolonomicPIDVAFollower(TRANSLATIONAL_PID, TRANSLATIONAL_PID, HEADING_PID);
    }

    // Custom inverse kinematics: chassis velocities -> wheel velocities
    public double[] toWheelSpeeds(double vx, double vy, double omega) {
        // omega sign and scaling based on mecanum geometry can vary; here sum positions symmetrically
        double fl = vx - vy - omega * (frontLeftPos.getX() + frontLeftPos.getY());
        double fr = vx + vy + omega * (frontRightPos.getX() + frontRightPos.getY());
        double bl = vx + vy - omega * (backLeftPos.getX() + backLeftPos.getY());
        double br = vx - vy + omega * (backRightPos.getX() + backRightPos.getY());
        return new double[]{fl, fr, bl, br};
    }

    // Trajectory builder
    public TrajectoryBuilder trajectoryBuilder(Pose2d startPose) {
        return new TrajectoryBuilder(
                startPose,
                new MinVelocityConstraint(Arrays.asList(
                        new TranslationalVelocityConstraint(MAX_WHEEL_VEL),
                        new AngularVelocityConstraint(MAX_ANG_VEL))
                ),
                new ProfileAccelerationConstraint(MAX_ACCEL)
        );
    }

    // Trajectory helper methods
    public Trajectory lineTo(Vector2d target) {
        return trajectoryBuilder(currentPose).lineTo(target).build();
    }

    public Trajectory lineToLinearHeading(Pose2d targetPose) {
        return trajectoryBuilder(currentPose).lineToLinearHeading(targetPose).build();
    }

    public void followTrajectory(Trajectory trajectory) {
        follower.followTrajectory(trajectory);
        while (follower.isFollowing()) {
            updatePoseEstimate();
            follower.update(currentPose);
            Thread.yield();
        }
        stop();
    }

    public void updatePoseEstimate() {
        localizer.update();
        currentPose = localizer.getPoseEstimate();
    }

    // Holonomic drive control
    public void drive(double forward, double strafe, double rotate, double power) {
        double fl = forward + strafe + rotate;
        double fr = forward - strafe - rotate;
        double bl = forward - strafe + rotate;
        double br = forward + strafe - rotate;

        double max = Math.max(1.0, Math.max(Math.max(Math.abs(fl), Math.abs(fr)),
                Math.max(Math.abs(bl), Math.abs(br))));

        fl = (fl / max) * power;
        fr = (fr / max) * power;
        bl = (bl / max) * power;
        br = (br / max) * power;

        setMotorPowers(fl, fr, bl, br);
    }

    public void setMotorPowers(double fl, double fr, double bl, double br) {
        leftFront.setPower(fl);
        rightFront.setPower(fr);
        leftBack.setPower(bl);
        rightBack.setPower(br);
    }

    public void stop() {
        setMotorPowers(0, 0, 0, 0);
    }

    public void updateDashboard() {
        TelemetryPacket packet = new TelemetryPacket();
        Canvas field = packet.fieldOverlay();
        field.setStrokeWidth(1);
        field.setStroke("blue");
        field.fillCircle(currentPose.getX(), currentPose.getY(), 2);
        dashboard.sendTelemetryPacket(packet);
    }
}
