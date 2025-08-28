package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Contollers.MotorController;
import org.firstinspires.ftc.teamcode.Contollers.TeleControllers.DriveControllerTele;


@TeleOp(name = "TeleOp Example")
public class TeleExample extends LinearOpMode {

    private MotorController frontLeft;
    private MotorController frontRight;
    private MotorController backLeft;
    private MotorController backRight;
    private DriveControllerTele drive;

    @Override
    public void runOpMode() {
        frontLeft = new MotorController(hardwareMap.dcMotor.get("FL"));
        frontRight = new MotorController(hardwareMap.dcMotor.get("FR"));
        backLeft = new MotorController(hardwareMap.dcMotor.get("BL"));
        backRight = new MotorController(hardwareMap.dcMotor.get("BR"));

        drive = new DriveControllerTele(frontLeft, frontRight, backLeft, backRight);

        waitForStart();

        while (opModeIsActive()) {
            drive.update();
            drive.drive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            if (gamepad1.a) {
                stop();
            }
        }
    }
}
