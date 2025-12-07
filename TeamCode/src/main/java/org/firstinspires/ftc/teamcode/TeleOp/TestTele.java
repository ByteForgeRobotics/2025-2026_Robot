package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Auto.Controller.DriveController;


@TeleOp(name = "TeleOp Example")
public class TestTele extends LinearOpMode {
    private DriveController drive;

    @Override
    public void runOpMode() {

        drive = new DriveController(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

            drive.update();


            drive.drive(
                    gamepad1.left_stick_x,
                    gamepad1.left_stick_y,
                    gamepad1.right_stick_x
            );


            if (gamepad1.a) {
                drive.stop();
            }
        }
    }

}
