package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.Constants;

import java.util.Set;

public class ArcadeDriveCommand implements Command {
    private final DriveTrainSubsystem m_driveTrain;
    private final Set<Subsystem> subsystems;
    private PIDController PID;

    private double leftJoyY, rightJoyX, error, gain;

    public ArcadeDriveCommand(DriveTrainSubsystem driveTrainSubsystem, double currentLeftJoyY, double currentRightJoyX) {
        this.m_driveTrain = driveTrainSubsystem;
        this.subsystems = Set.of(driveTrainSubsystem);
        leftJoyY = currentLeftJoyY;
        rightJoyX = currentRightJoyX;
    }

    /**
     * The initial subroutine of a command.  Called once when the command is initially scheduled.
     */
    @Override
    public void initialize() {
        PID = new PIDController(Constants.DRIVE_STRAIGHT_P, Constants.DRIVE_STRAIGHT_I,
                Constants.DRIVE_STRAIGHT_D);
        PID.setSetpoint(Constants.DRIVE_STRAIGHT_TARGET);
        m_driveTrain.resetEncoders();
    }

    /**
     * The main body of a command.  Called repeatedly while the command is scheduled.
     * (That is, it is called repeatedly until {@link #isFinished()}) returns true.)
     */
    @Override
    public void execute() {

        // Calculate how much one side is spinning more than the other
        error = m_driveTrain.getLeftDistanceInches() - m_driveTrain.getRightDistanceInches();

        // If it IS within the deadzone
        if (leftJoyY > -Constants.JOYSTICK_DEADZONE && leftJoyY < Constants.JOYSTICK_DEADZONE) {
            leftJoyY = 0;
        }
        if (rightJoyX > -Constants.JOYSTICK_DEADZONE && rightJoyX < Constants.JOYSTICK_DEADZONE) {
            rightJoyX = 0;
        }

        // If the right joystick (i.e: the turning axis) is not 0, then use Arcade drive
        // normally.
        // Otherwise, the robot should be going straight. Therefore, use
        // DriveStraightPID to go straight.
        // But if both joystick values are within the deadzones, they will both be 0.
        // Apply no power in that case.
        if (rightJoyX != 0) {
            m_driveTrain.setLeftPower(parabolicDrive() - rightJoyX);
            m_driveTrain.setRightPower(parabolicDrive() + rightJoyX);
        }
        // right joystick is in the deadzone, left joystick is not; apply PID to keep
        // the robot going straight
        else if (rightJoyX == 0 && leftJoyY != 0) {

            gain = PID.calculate(error);
            // REMOVE ONCE PID IS TUNED
            gain = 0;
            m_driveTrain.setLeftPower(parabolicDrive() - gain);
            m_driveTrain.setRightPower(parabolicDrive() + gain);
        }
        // both joystick values are within the deadzone
        else {
            m_driveTrain.setLeftPower(0.0);
            m_driveTrain.setRightPower(0.0);
        }

    }

    /**
     * <p>
     * Returns whether this command has finished. Once a command finishes -- indicated by
     * this method returning true -- the scheduler will call its {@link #end(boolean)} method.
     * </p><p>
     * Returning false will result in the command never ending automatically. It may still be
     * cancelled manually or interrupted by another command. Hard coding this command to always
     * return true will result in the command executing once and finishing immediately. It is
     * recommended to use * {@link edu.wpi.first.wpilibj2.command.InstantCommand InstantCommand}
     * for such an operation.
     * </p>
     *
     * @return whether this command has finished.
     */
    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    /**
     * The action to take when the command ends. Called when either the command
     * finishes normally -- that is it is called when {@link #isFinished()} returns
     * true -- or when  it is interrupted/canceled. This is where you may want to
     * wrap up loose ends, like shutting off a motor that was being used in the command.
     *
     * @param interrupted whether the command was interrupted/canceled
     */
    @Override
    public void end(boolean interrupted) {

    }

    public double parabolicDrive() {
        if (leftJoyY > 0)
            return Math.pow(leftJoyY, 2);
        else
            return -Math.pow(leftJoyY, 2);
    }
    /**
     * <p>
     * Specifies the set of subsystems used by this command.  Two commands cannot use the same
     * subsystem at the same time.  If the command is scheduled as interruptible and another
     * command is scheduled that shares a requirement, the command will be interrupted.  Else,
     * the command will not be scheduled. If no subsystems are required, return an empty set.
     * </p><p>
     * Note: it is recommended that user implementations contain the requirements as a field,
     * and return that field here, rather than allocating a new set every time this is called.
     * </p>
     *
     * @return the set of subsystems that are required
     */
    @Override
    public Set<Subsystem> getRequirements() {
        return this.subsystems;
    }
}
