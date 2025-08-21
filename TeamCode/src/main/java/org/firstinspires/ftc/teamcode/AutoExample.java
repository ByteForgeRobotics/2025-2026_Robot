package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Contollers.AutoControllors.DriveControllerAuto;
import org.firstinspires.ftc.teamcode.Contollers.MotorController;

@Autonomous(name = "AutoExample", group = "Examples")
public class AutoExample extends LinearOpMode {

    private MotorController frontLeft;
    private MotorController frontRight;
    private MotorController backLeft;
    private MotorController backRight;
    private DriveControllerAuto drive;

    @Override
    public void runOpMode() {
        frontLeft = new MotorController(hardwareMap.dcMotor.get("front_left"));
        frontRight = new MotorController(hardwareMap.dcMotor.get("front_right"));
        backLeft = new MotorController(hardwareMap.dcMotor.get("back_left"));
        backRight = new MotorController(hardwareMap.dcMotor.get("back_right"));

        drive = new DriveControllerAuto(frontLeft, frontRight, backLeft, backRight);

        waitForStart();

        drive.drive(1, 0, 0, 0.5, 2);

        while (opModeIsActive()) {
            drive.update();

            int step = 0;
            switch (step) {
                case 0:
                    drive.drive(0.5, 1, 0, 0.5, 2);
                    telemetry.addLine("step 2 complete");
                    step++;
            }

        }
    }
}
