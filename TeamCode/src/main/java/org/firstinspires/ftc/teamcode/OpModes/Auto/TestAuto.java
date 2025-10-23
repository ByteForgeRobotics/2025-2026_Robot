package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.Contollers.AutoControllors.DriveControllerAuto;
// Use your concrete localizer class here:
import org.firstinspires.ftc.teamcode.Contollers.General.MotorEncoderLocalizer;

@Autonomous(name = "TestAuto", group = "Auto")
public class TestAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Replace with your concrete localizer implementation
        MotorEncoderLocalizer localizer = new MotorEncoderLocalizer(hardwareMap);
        DriveControllerAuto drive = new DriveControllerAuto(hardwareMap, localizer, 14.0, 14.0);

        Pose2d startPose = new Pose2d(0, 0, 0);

        drive.updatePoseEstimate();
        telemetry.addLine("Building trajectory...");
        telemetry.update();

        Trajectory testTrajectory = drive.trajectoryBuilder(startPose)
                .lineTo(new Vector2d(24, 0))      // Move forward 24 inches
                .build();

        Trajectory strafeTrajectory = drive.trajectoryBuilder(testTrajectory.end())
                .lineTo(new Vector2d(24, 24))     // Strafe right 24 inches
                .build();

        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectory(testTrajectory);
        drive.followTrajectory(strafeTrajectory);

        drive.stop();

        while (opModeIsActive()) {
            drive.updatePoseEstimate();
            drive.updateDashboard();

            Pose2d pose = localizer.getPoseEstimate();
            telemetry.addData("Pose X", pose.getX());
            telemetry.addData("Pose Y", pose.getY());
            telemetry.addData("Heading (rad)", pose.getHeading());
            telemetry.update();

            idle();
        }
    }
}
