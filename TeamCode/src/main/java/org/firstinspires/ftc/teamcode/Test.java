package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Contollers.AutoOnly.DriveControllerAuto;
import org.firstinspires.ftc.teamcode.Contollers.MotorController;

@Autonomous(name = "Test", group = "Test")
public class Test extends LinearOpMode {

    private MotorController frontLeft;
    private MotorController frontRight;
    private MotorController backLeft;
    private MotorController backRight;
    private DriveControllerAuto drive;

    @Override
    public void runOpMode() {
        // Initialize motors from config
        frontLeft = new MotorController(hardwareMap.dcMotor.get("front_left"));
        frontRight = new MotorController(hardwareMap.dcMotor.get("front_right"));
        backLeft = new MotorController(hardwareMap.dcMotor.get("back_left"));
        backRight = new MotorController(hardwareMap.dcMotor.get("back_right"));

        // Pass into your drive controller
        drive = new DriveControllerAuto(frontLeft, frontRight, backLeft, backRight);

        // Wait for start button
        waitForStart();

        // Example: at start, schedule a forward motion
        drive.drive(1, 0, 0, 0.5, 2);

        // MAIN LOOP
        while (opModeIsActive()) {
            drive.update();

            if (!drive.isRunning()) {
                telemetry.addLine("done");
                telemetry.update();

                drive.drive(1, 0.5, 0, 0.5, 2);;
            }
        }
    }
}
