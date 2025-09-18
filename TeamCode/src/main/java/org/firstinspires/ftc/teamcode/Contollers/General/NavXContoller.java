package org.firstinspires.ftc.teamcode.Contollers.General;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import java.util.ArrayList;
import java.util.List;

public class NavXContoller {
    private final NavxMicroNavigationSensor navX;
    private final IntegratingGyroscope gyro;
    private final ElapsedTime timer = new ElapsedTime();

    public NavXContoller(NavxMicroNavigationSensor navX) {
        this.navX = navX;
        this.gyro = (IntegratingGyroscope) navX;
    }

    public List<Float> getGyroData() {
        List<Float> data = new ArrayList<>();
        AngularVelocity rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        data.add(rates.xRotationRate);
        data.add(rates.yRotationRate);
        data.add(rates.zRotationRate);
        data.add(angles.firstAngle);
        data.add(angles.secondAngle);
        data.add(angles.thirdAngle);
        return data;
    }

    public float getYaw() {
        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle;
    }
}
