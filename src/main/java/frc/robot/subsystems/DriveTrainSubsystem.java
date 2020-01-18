package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.driveTrain;
//import frc.robot.Robot;
//import frc.robot.commands.ArcadeDriveCommand;

public class DriveTrainSubsystem implements Subsystem {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

    //Initialize useful constants and variables
    private double leftPower, rightPower;
    private final int numberOfTicks = 2048;
    private final double wheelCircumference = (Math.PI * 6);

    // Create Motors
    private VictorSP leftMotor1 = new VictorSP(driveTrain.LEFT_MOTOR_1.getValue());
    private VictorSP leftMotor2 = new VictorSP(driveTrain.LEFT_MOTOR_1.getValue());

    private VictorSP rightMotor1 = new VictorSP(driveTrain.RIGHT_MOTOR_1.getValue());
    private VictorSP rightMotor2 = new VictorSP(driveTrain.RIGHT_MOTOR_2.getValue());

    // Create Gyro
    private ADXRS450_Gyro gyro = new ADXRS450_Gyro();

    // Create Encoders
    private Encoder leftEncoder = new Encoder(driveTrain.LEFT_ENCODER_1.getValue(),driveTrain.LEFT_ENCODER_2.getValue(), false,
            EncodingType.k4X);
    private Encoder rightEncoder = new Encoder(driveTrain.RIGHT_ENCODER_1.getValue(),driveTrain.RIGHT_ENCODER_2.getValue(), true,
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

//      setDefaultCommand(ArcadeDriveCommand);
        leftEncoder.setDistancePerPulse(wheelCircumference / numberOfTicks);
        rightEncoder.setDistancePerPulse(wheelCircumference / numberOfTicks);
        leftEncoder.setMaxPeriod(5);
        rightEncoder.setMaxPeriod(5);
        leftEncoder.setMinRate(0);
        rightEncoder.setMinRate(0);
        leftEncoder.setSamplesToAverage(1);
        rightEncoder.setSamplesToAverage(1);

        gyro.calibrate();
    }

    /**
     * SET the power of the LEFT and RIGHT motors.
     * @param power Value between 1 and -1. Negative values represent backwards motion.
     */
    public void setLeftPower(double power){
        leftPower = power;
    }

    public void setRightPower(double power){
        leftPower = power;
    }

    /**
     * Called by the CommandScheduler to update each motors power value periodically.
     */
    public void updateMotorOutputs(){
        leftMotor1.set(-leftPower);
        leftMotor2.set(-leftPower);

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

    public double getGyroAngle() {
        return gyro.getAngle();
    }

    public void resetGyro() {
        gyro.reset();
    }

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

