package org.firstinspires.ftc.teamcode.OpModes.Tests;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous(name = "Motor RPM Test")
public class MotorRpmTest extends LinearOpMode {
    // Motor Constants
    private static final double TICKS_PER_REV = 537.7; // Example (GoBilda Yellow Jacket)
    private DcMotorEx motor;

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotorEx.class, "FR");
        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        waitForStart();

        if (opModeIsActive()) {
            motor.setPower(1.0);
            while (opModeIsActive()) {
                double ticksPerSecond = motor.getVelocity();
                double rpm = (ticksPerSecond / TICKS_PER_REV) * 60.0;

                telemetry.addData("Ticks Per Second", ticksPerSecond);
                telemetry.addData("Calculated RPM", rpm);
                telemetry.update();

                // Optional: Send to dashboard if using FTC Dashboard
                // TelemetryPacket packet = new TelemetryPacket();
                // packet.put("Ticks Per Second", ticksPerSecond);
                // packet.put("Calculated RPM", rpm);
                // FtcDashboard.getInstance().sendTelemetryPacket(packet);

                sleep(50); // 20 Hz update rate. Adjust as needed.
            }
            motor.setPower(0.0);
        }
    }
}
