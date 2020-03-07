package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class SparkMaxDriveTrainSubsystem extends SubsystemBase {

    private CANSparkMax leftMaster = new CANSparkMax(Constants.LEFT_MASTER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
    private CANSparkMax leftSlave = new CANSparkMax(Constants.LEFT_SLAVE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

    private CANSparkMax rightMaster = new CANSparkMax(Constants.RIGHT_MASTER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
    private CANSparkMax rightSlave = new CANSparkMax(Constants.RIGHT_SLAVE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

    private AHRS gyro = new AHRS(SPI.Port.kMXP);

    public SparkMaxDriveTrainSubsystem() {
        leftSlave.follow(leftMaster);
        rightSlave.follow(rightMaster);

        leftMaster.setInverted(false);
        rightMaster.setInverted(true);
    }
}

