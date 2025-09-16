package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Contollers.AutoControllors.DriveControllerAuto;
import org.firstinspires.ftc.teamcode.Contollers.General.MotorController;
import org.firstinspires.ftc.teamcode.Contollers.General.ServoController;

@Autonomous(name = "AutoExample")
public class AutoExample extends LinearOpMode {

    private MotorController frontLeft;
    private MotorController frontRight;
    private MotorController backLeft;
    private MotorController backRight;
    private DriveControllerAuto drive;
    private ServoController testServo;

    @Override
    public void runOpMode() {
        frontLeft = new MotorController(hardwareMap.dcMotor.get("FL"));
        frontRight = new MotorController(hardwareMap.dcMotor.get("FR"));
        backLeft = new MotorController(hardwareMap.dcMotor.get("BL"));
        backRight = new MotorController(hardwareMap.dcMotor.get("BR"));
        testServo = new ServoController(hardwareMap.servo.get("test"));

        drive = new DriveControllerAuto(frontLeft, frontRight, backLeft, backRight);

        waitForStart();

        int step = 0;
        while (opModeIsActive()) {
            testServo.update();

            switch (step) {
                case 0:
                    testServo.setPosition(1);
                    telemetry.addLine("step 2 complete");
                    step++;
            }

        }
    }
}
