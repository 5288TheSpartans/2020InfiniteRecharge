package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.Constants;

public class ArcadeDriveCommand extends CommandBase {
    private final DriveTrainSubsystem m_driveTrain;
    private PIDController PID;


    private DoubleSupplier m_leftJoyY, m_rightJoyX;
    private double currentLeftJoyY, currentRightJoyX, error, gain;

    /**
     * CONSTRUCTOR
     * @param driveTrainSubsystem Instance of a DriveTrainSubsystem.
     * @param leftJoyY DoubleSupplier containing a method to retrieve the leftJoyY value.
     * @param rightJoyX DoubleSupplier containing a method to retrieve the rightJoyX value.
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

    /**
    *  Call this method repeatedly to calculate motor input values.
    */
    @Override
    public void execute() {

        // Current values of X and Y-axis joysticks.
        currentLeftJoyY = m_leftJoyY.getAsDouble();
        currentRightJoyX = m_rightJoyX.getAsDouble();

        // Calculate how much one side is spinning more than the other
        error = m_driveTrain.getLeftDistanceInches() - m_driveTrain.getRightDistanceInches();


        if(leftJoyYWithinDeadzone()) {
            currentLeftJoyY = 0;
        }

        if(rightJoyXWithinDeadzone()) {
            currentRightJoyX = 0;
        }

        /*
          If X-axis joystick value is greater than joystick deadzone, use ArcadeDrive normally.
        */
        if (!rightJoyXWithinDeadzone()) {
            m_driveTrain.setLeftPower(linearDrive() - currentRightJoyX);
            m_driveTrain.setRightPower(linearDrive() + currentRightJoyX);
        }

         /*
           If X-axis joystick value is 0 (robot not turning) and Y-axis joystick value is greater than deadzone,
           apply a PID controller to straighten robot drive.
         */
        else if (rightJoyXWithinDeadzone() && !leftJoyYWithinDeadzone()) {
            gain = PID.calculate(error);
            gain = 0;
            m_driveTrain.setLeftPower(linearDrive() - gain);
            m_driveTrain.setRightPower(linearDrive() + gain);
        }

        /*
          Both joysticks are within deadzone, therefore robot not moving.
        */
        else {
            m_driveTrain.setLeftPower(0.0);
            m_driveTrain.setRightPower(0.0);
        }
    }

    /**
     * ArcadeDrive runs throughout the game, the robot always has the need to move. Therefore isFinished is never true.
     * @return False if the command is not finished, else true if it is.
     */
    @Override
    public boolean isFinished() {
        return false;
    }

    /**
    *  ArcadeDrive never ends except when it is manually cancelled.
    */
    @Override
    public void end(boolean interrupted) {
    }

    /**
    *  Check is the Y-axis joystick value exists in the deadzone interval -0.05 <-> 0.05.
    *  @return true if within the deadzone, else false.
    */
    public boolean leftJoyYWithinDeadzone() {
        return(currentLeftJoyY > -Constants.JOYSTICK_DEADZONE
                && currentLeftJoyY < Constants.JOYSTICK_DEADZONE);
    }

    /**
     *  Check is the X-axis joystick value exists in the deadzone interval -0.05 <-> 0.05.
     *  @return true if within the deadzone, else false.
     */
    public boolean rightJoyXWithinDeadzone() {
        return(currentRightJoyX > -Constants.JOYSTICK_DEADZONE
                && currentRightJoyX < Constants.JOYSTICK_DEADZONE);
    }

    /**
     *  Squares Y-axis value to ensure a smooth, linear acceleration of the robot.
     *  @return m_leftJoyY translated to compensate for deadzone.
    */
    public double linearDrive() {
        if (currentLeftJoyY > 0) {
            return (currentLeftJoyY-0.15)*1.15;
        }
        else {
            return (currentLeftJoyY+0.15)*1.15;
        }
    }
}