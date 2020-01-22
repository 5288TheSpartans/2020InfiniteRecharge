package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSubsystem;
import java.util.Set;

public class ArcadeDriveCommand implements Command {
    private final Set<Subsystem> subsystems;
    DriveTrainSubsystem m_driveTrain;
    PIDController PID;


    double rightJoyX = 0, leftJoyY = 0, error = 0, gain = 0;

    public ArcadeDriveCommand() {
        this.m_driveTrain = DriveTrainSubsystem.getInstance();
        this.subsystems = Set.of(m_driveTrain);
    }

    @Override
    public void initialize() {
        PID = new PIDController(Constants.DRIVE_STRAIGHT_P, Constants.DRIVE_STRAIGHT_I,
                Constants.DRIVE_STRAIGHT_D);
        PID.setSetpoint(Constants.DRIVE_STRAIGHT_TARGET);
        m_driveTrain.resetEncoders();

    }

    @Override
    public void execute() {

        // Calculate how much one side is spinning more than the other
        error = m_driveTrain.getLeftDistanceInches() - m_driveTrain.getRightDistanceInches();

        leftJoyY = RobotContainer.getInstance().xboxGetLeftStickY();
        rightJoyX = RobotContainer.getInstance().xboxGetRightStickX();

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
            m_driveTrain.setLeftPower(m_driveTrain.parabolicDrive() - rightJoyX);
            m_driveTrain.setRightPower(m_driveTrain.parabolicDrive() + rightJoyX);
        }
        // right joystick is in the deadzone, left joystick is not; apply PID to keep
        // the robot going straight
        else if (rightJoyX == 0 && leftJoyY != 0) {

            gain = PID.calculate(error);
            // REMOVE ONCE PID IS TUNED
            gain = 0;
            m_driveTrain.setLeftPower(m_driveTrain.parabolicDrive() - gain);
            m_driveTrain.setRightPower(m_driveTrain.parabolicDrive() + gain);
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

    @Override
    public Set<Subsystem> getRequirements() {
        return this.subsystems;
    }
}
