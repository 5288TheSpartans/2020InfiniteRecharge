package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.Constants;

import java.util.Set;

public class ArcadeDriveCommand extends CommandBase {
    private final DriveTrainSubsystem m_driveTrain;
    private PIDController PID;

    private double leftJoyY, rightJoyX, error, gain;

    public ArcadeDriveCommand(DriveTrainSubsystem driveTrainSubsystem, double currentLeftJoyY, double currentRightJoyX) {
        this.m_driveTrain = driveTrainSubsystem;
        addRequirements(m_driveTrain);
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

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }

    public double parabolicDrive() {
        if (leftJoyY > 0)
            return Math.pow(leftJoyY, 2);
        else
            return -Math.pow(leftJoyY, 2);
    }

}

