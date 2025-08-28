package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Contollers.AutoControllors.DriveControllerAuto;
import org.firstinspires.ftc.teamcode.Contollers.MotorController;

@Autonomous(name = "AutoExample")
public class AutoExample extends LinearOpMode {

    private MotorController frontLeft;
    private MotorController frontRight;
    private MotorController backLeft;
    private MotorController backRight;
    private DriveControllerAuto drive;

    @Override
    public void runOpMode() {
        frontLeft = new MotorController(hardwareMap.dcMotor.get("FL"));
        frontRight = new MotorController(hardwareMap.dcMotor.get("FR"));
        backLeft = new MotorController(hardwareMap.dcMotor.get("BL"));
        backRight = new MotorController(hardwareMap.dcMotor.get("BR"));

        drive = new DriveControllerAuto(frontLeft, frontRight, backLeft, backRight);

        waitForStart();

        drive.drive(1, 0, 0, 0.5, 20);
        int step = 0;
        while (opModeIsActive()) {
            drive.update();

            switch (step) {
                case 0:
                    drive.drive(1, 0, 0, 0.5, 10);
                    telemetry.addLine("step 2 complete");
                    step++;
                case 1:
                    stop();
            }

        }
    }
}
