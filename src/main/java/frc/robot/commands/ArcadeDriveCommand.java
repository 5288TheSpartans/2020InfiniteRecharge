package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.DoubleSupplier;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.Constants;

public class ArcadeDriveCommand extends CommandBase {
    private final DriveTrainSubsystem m_driveTrain;
    private PIDController PID;

    private DoubleSupplier m_leftJoyY, m_rightJoyX;
    private double currentLeftJoyY, currentRightJoyX, error, gain;

    /*
    *  Add command to require the DriveTrainSubsystem and retrieve joystick values when needed using DoubleSuppliers.
    */
    public ArcadeDriveCommand(DriveTrainSubsystem driveTrainSubsystem, DoubleSupplier leftJoyY, DoubleSupplier rightJoyX) {
        m_driveTrain = driveTrainSubsystem;
        addRequirements(m_driveTrain);
        m_leftJoyY = leftJoyY;
        m_rightJoyX = rightJoyX;
    }

    /**
     * Called once initially with the start of an ArcadeDrive command.
     */
    @Override
    public void initialize() {

        // Create a new PID controller for driving straight.
        PID = new PIDController(Constants.DRIVE_STRAIGHT_P, Constants.DRIVE_STRAIGHT_I,
                Constants.DRIVE_STRAIGHT_D);

        // Set the target value to 0 where theoretically, both motor values are equal in power output.
        PID.setSetpoint(Constants.DRIVE_STRAIGHT_TARGET);

        // Reset encoders to a reading of 0 for PID control.
        m_driveTrain.resetEncoders();
    }

    /*
    *  Call this method repeatedly to calculate motor input values.
    */
    @Override
    public void execute() {

        // Current values of X and Y-axis joysticks.
        currentLeftJoyY = m_leftJoyY.getAsDouble();
        currentRightJoyX = m_rightJoyX.getAsDouble();

        // Calculate how much one side is spinning more than the other
        error = m_driveTrain.getLeftDistanceInches() - m_driveTrain.getRightDistanceInches();

        /*
        *  If X-axis joystick value is greater than joystick deadzone, use ArcadeDrive normally.
        */
        if (rightJoyXWithinDeadzone()) {
            m_driveTrain.setLeftPower(parabolicDrive() - currentRightJoyX);
            m_driveTrain.setRightPower(parabolicDrive() + currentRightJoyX);
        }

        /*
         *  If X-axis joystick value is 0 (robot not turning) and Y-axis joystick value is greater than deadzone,
         *  apply a PID controller to straighten robot drive.
         */
        else if (rightJoyXWithinDeadzone() && !leftJoyYWithinDeadzone()) {
            gain = PID.calculate(error);
            gain = 0;
            m_driveTrain.setLeftPower(parabolicDrive() - gain);
            m_driveTrain.setRightPower(parabolicDrive() + gain);
        }

        /*
        *  Both joysticks are within deadzone, therefore robot not moving.
        */
        else {
            m_driveTrain.setLeftPower(0.0);
            m_driveTrain.setRightPower(0.0);
        }
    }

    /*
    *  ArcadeDrive runs throughout the game, the robot always has the need to move. Therefore isFinished is never true.
    */
    @Override
    public boolean isFinished() {
        return false;
    }

    /*
    *  ArcadeDrive never ends.
    */
    @Override
    public void end(boolean interrupted) {
    }

    /*
    *  Check is the Y-axis joystick value exists in the deadzone interval -0.05 <-> 0.05.
    *  @RETURN true if within the deadzone, else false.
    */
    public boolean leftJoyYWithinDeadzone() {
        return(currentLeftJoyY > -Constants.JOYSTICK_DEADZONE
                && currentLeftJoyY < Constants.JOYSTICK_DEADZONE);
    }

    /*
     *  Check is the X-axis joystick value exists in the deadzone interval -0.05 <-> 0.05.
     *  @RETURN true if within the deadzone, else false.
     */
    public boolean rightJoyXWithinDeadzone() {
        return(currentRightJoyX > -Constants.JOYSTICK_DEADZONE
                && currentRightJoyX < Constants.JOYSTICK_DEADZONE);
    }

    /*
     *  Squares Y-axis value to ensure a smooth, parabolic acceleration of the robot rather than linear.
     *  @RETURN m_leftJoyY squared.
    */
    public double parabolicDrive() {
        if (currentLeftJoyY > 0)
            return Math.pow(currentLeftJoyY, 2);
        else
            return -Math.pow(currentLeftJoyY, 2);
    }
}