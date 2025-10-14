package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Contollers.AutoControllors.DriveControllerAuto;
import org.firstinspires.ftc.teamcode.Contollers.General.MotorController;
import org.firstinspires.ftc.teamcode.Contollers.General.NavXContoller;

@Autonomous(name = "AutoExample")
public class AutoExample extends LinearOpMode {

    @Override
    public void runOpMode() {
        float xPoz = 0;
        float yPoz = 0;
        MotorController frontLeft = new MotorController(hardwareMap.dcMotor.get("FL"));
        MotorController frontRight = new MotorController(hardwareMap.dcMotor.get("FR"));
        MotorController backLeft = new MotorController(hardwareMap.dcMotor.get("BL"));
        MotorController backRight = new MotorController(hardwareMap.dcMotor.get("BR"));
        NavXContoller navX = new NavXContoller(hardwareMap.get(NavxMicroNavigationSensor.class, "navx"));
        DriveControllerAuto drive = new DriveControllerAuto(frontLeft, frontRight, backLeft, backRight, navX);
        telemetry.addLine("Starting");
        waitForStart();

        int step = 0;
        while (opModeIsActive()) {
            drive.update();
            switch (step) {
                case 0:
                    drive.MoveToPosition(10, 0, 90, xPoz, yPoz);
                    xPoz = 10;
                    yPoz = 10;
                    if (!drive.isRunning()) {
                        step++;
                        break;
                    }

            }

        }
    }
}
