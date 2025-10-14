package org.firstinspires.ftc.teamcode.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

@Autonomous(name = "Motor RPM Test Dashboard")
public class GryoTest extends LinearOpMode {
    // Motor Constants
    private static final double TICKS_PER_REV = 537.7; // Example (GoBilda Yellow Jacket encoder)
    private DcMotorEx motor;

    @Override
    public void runOpMode() {
        // Initialize motor and dashboard
        motor = hardwareMap.get(DcMotorEx.class, "FR");
        FtcDashboard dashboard = FtcDashboard.getInstance();

        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        waitForStart();

        if (opModeIsActive()) {
            motor.setPower(1.0);

            while (opModeIsActive()) {
                double ticksPerSecond = motor.getVelocity();
                double rpm = (ticksPerSecond / TICKS_PER_REV) * 60.0;

                // Driver Station telemetry
                telemetry.addData("Ticks Per Second", ticksPerSecond);
                telemetry.addData("Calculated RPM", rpm);
                telemetry.update();

                // Dashboard telemetry
                TelemetryPacket packet = new TelemetryPacket();
                packet.put("Ticks Per Second", ticksPerSecond);
                packet.put("Calculated RPM", rpm);
                dashboard.sendTelemetryPacket(packet);

                sleep(50);
            }
            motor.setPower(0.0);
        }
    }
}
