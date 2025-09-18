package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import org.firstinspires.ftc.teamcode.Contollers.AutoControllors.DriveControllerAuto;
import org.firstinspires.ftc.teamcode.Contollers.General.MotorController;
import org.firstinspires.ftc.teamcode.Contollers.General.NavXContoller;
import org.firstinspires.ftc.teamcode.Contollers.General.ServoController;

@Autonomous(name = "AutoExample")
public class AutoExample extends LinearOpMode {

    private MotorController frontLeft;
    private MotorController frontRight;
    private MotorController backLeft;
    private MotorController backRight;
    private DriveControllerAuto drive;
    private ServoController testServo;
    private NavXContoller navX;

    @Override
    public void runOpMode() {
        frontLeft = new MotorController(hardwareMap.dcMotor.get("FL"));
        frontRight = new MotorController(hardwareMap.dcMotor.get("FR"));
        backLeft = new MotorController(hardwareMap.dcMotor.get("BL"));
        backRight = new MotorController(hardwareMap.dcMotor.get("BR"));
        testServo = new ServoController(hardwareMap.servo.get("test"));
        navX = new NavXContoller(hardwareMap.get(NavxMicroNavigationSensor.class, "navx"));

        drive = new DriveControllerAuto(frontLeft, frontRight, backLeft, backRight,navX);

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
