package org.firstinspires.ftc.teamcode.Contollers.General;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class NavXContoller implements Runnable {
    private final NavxMicroNavigationSensor navX;
    private final IntegratingGyroscope gyro;
    private volatile float latestYaw;
    private volatile float latestPitch;
    private volatile float latestRoll;
    private Thread sensorThread;
    private volatile boolean running;

    public NavXContoller(NavxMicroNavigationSensor navX) {
        this.navX = navX;
        this.gyro = navX;
        startThread();
    }

    private void startThread() {
        running = true;
        sensorThread = new Thread(this);
        sensorThread.setDaemon(true);
        sensorThread.start();
    }

    public void stopThread() {
        running = false;
        try {
            sensorThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        while (running) {
            Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            latestYaw = angles.firstAngle;
            latestPitch = angles.secondAngle;
            latestRoll = angles.thirdAngle;
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public float getYaw() {
        return latestYaw;
    }

    public float getPitch() {
        return latestPitch;
    }

    public float getRoll() {
        return latestRoll;
    }
}
