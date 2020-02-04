package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.Robot;
import frc.robot.commands.ArcadeDriveCommand;


public class DriveTrainSubsystem implements Subsystem {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

    //Initialize useful constants and variables
    private double leftPower, rightPower;

    // Create Motors
    private VictorSP leftMotor1 = new VictorSP(Constants.LEFT_MOTOR_1);
    private VictorSP leftMotor2 = new VictorSP(Constants.LEFT_MOTOR_2);

    private VictorSP rightMotor1 = new VictorSP(Constants.RIGHT_MOTOR_1);
    private VictorSP rightMotor2 = new VictorSP(Constants.RIGHT_MOTOR_2);

    // Create Gyro
//    private ADXRS450_Gyro gyro = new ADXRS450_Gyro();

    // Create Encoders
    private Encoder leftEncoder = new Encoder(Constants.LEFT_ENCODER_1,Constants.LEFT_ENCODER_2, false,
            EncodingType.k4X);
    private Encoder rightEncoder = new Encoder(Constants.RIGHT_ENCODER_1,Constants.RIGHT_ENCODER_2, true,
            EncodingType.k4X);

    /**
     * The Singleton instance of this DriveTrainSubsystem. External classes should
     * use the {@link #getInstance()} method to get the instance.
     */
    private final static DriveTrainSubsystem INSTANCE = new DriveTrainSubsystem();

    /**
     * Creates a new instance of this DriveTrainSubsystem.
     * This constructor is private since this class is a Singleton. External classes
     * should use the {@link #getInstance()} method to get the instance.
     */
    private DriveTrainSubsystem() {

        invertMotors();
        leftEncoder.setDistancePerPulse(Constants.wheelCircumference / Constants.numberOfTicks);
        rightEncoder.setDistancePerPulse(Constants.wheelCircumference / Constants.numberOfTicks);
        leftEncoder.setMaxPeriod(5);
        rightEncoder.setMaxPeriod(5);
        leftEncoder.setMinRate(0);
        rightEncoder.setMinRate(0);
        leftEncoder.setSamplesToAverage(1);
        rightEncoder.setSamplesToAverage(1);
//        gyro.calibrate();
    }

    @Override
    public void periodic() {
        updateMotorOutputs();
    }

    /**
     * SET the power of the LEFT and RIGHT motors.
     * @param power Value between 1 and -1. Negative values represent backwards motion.
     */
    public void setLeftPower(double power){
        leftPower = power;
    }

    public void setRightPower(double power){
        rightPower = power;
    }

    public void invertMotors() { leftMotor1.setInverted(true); leftMotor2.setInverted(true); }
    /**
     * Called by the CommandScheduler to update each motors power value periodically.
     */
    public void updateMotorOutputs(){
        leftMotor1.set(leftPower);
        leftMotor2.set(leftPower);

        rightMotor1.set(rightPower);
        rightMotor2.set(rightPower);
    }

    /**
     * Called by the CommandScheduler to update SmartDashBoard encoder values periodically.
     */
    public void putEncoderValues() {
        SmartDashboard.putNumber("Left Encoder Raw", leftEncoder.getRaw());
        SmartDashboard.putNumber("Right Encoder Raw", rightEncoder.getRaw());
        SmartDashboard.putNumber("Left Encoder Dist Per Pulse", leftEncoder.getDistancePerPulse());
        SmartDashboard.putNumber("Right Encoder Dist Per Pulse", rightEncoder.getDistancePerPulse());
    }

    public double getLeftDistanceInches() {
        return leftEncoder.getDistance();
    }

    public double getRightDistanceInches() {
        return rightEncoder.getDistance();
    }

//    public double getGyroAngle() {
//        return gyro.getAngle();
//    }

//    public void resetGyro() {
//        gyro.reset();
//    }

    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    /**
     * Returns the Singleton instance of this DriveTrainSubsystem. This static method
     * should be used -- {@code DriveTrainSubsystem.getInstance();} -- by external
     * classes, rather than the constructor to get the instance of this class.
     */
    public static DriveTrainSubsystem getInstance() {
        return INSTANCE;
    }
}

