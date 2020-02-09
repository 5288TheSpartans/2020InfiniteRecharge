package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.util.Color;

import frc.robot.Constants;
import frc.robot.subsystems.ControlPanelManipulatorSubsystem;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;


public class RotationControlCommand extends CommandBase {
    private final ControlPanelManipulatorSubsystem m_controlPanelManipulatorSubsystem;
    private ColorMatch m_colourMatcher;
    private ColorMatchResult m_initialColourMatchResult;
    private ColorMatchResult m_periodicColourMatchResult;
    private Color initialDetectedColour;
//    private Color initialControlPanelColour;

    private boolean preventRepeatsFlag= false;
    private boolean notPastInitialColourFlag = true;
    private double rotationCount = 0;
    private boolean isFinishedFlag = false;



    /**
     * CONSTRUCTOR
     * @param controlPanelManipulatorSubsystem Instance of a ControlPanelManipulatorSubsystem.
     */
    public RotationControlCommand(ControlPanelManipulatorSubsystem controlPanelManipulatorSubsystem) {
        m_controlPanelManipulatorSubsystem = controlPanelManipulatorSubsystem;
        addRequirements(controlPanelManipulatorSubsystem);

        m_colourMatcher = new ColorMatch();
    }

    /**
     * The initial subroutine of a command.  Called once when the command is initially scheduled.
     */
    @Override
    public void initialize() {
        m_colourMatcher.addColorMatch(Constants.RED_TARGET);
        m_colourMatcher.addColorMatch(Constants.GREEN_TARGET);
        m_colourMatcher.addColorMatch(Constants.BLUE_TARGET);
        m_colourMatcher.addColorMatch(Constants.YELLOW_TARGET);
        initialDetectedColour = m_controlPanelManipulatorSubsystem.getColour();
    }

    /**
     * The main body of a command.  Called repeatedly while the command is scheduled.
     * (That is, it is called repeatedly until {@link #isFinished()}) returns true.)
     */
    @Override
    public void execute() {

        m_controlPanelManipulatorSubsystem.setPower(Constants.ROTATION_CONTROL_POWER);
        m_periodicColourMatchResult = m_colourMatcher.matchClosestColor(m_controlPanelManipulatorSubsystem.getColour());

        /*
           Purpose: Before completion of a 1/2 rotation of the control panel, checks whether the detected colour
           has changed to a different one.  This prevents addRotation() from being called initially.
        */
        if(notPastInitialColourFlag && m_periodicColourMatchResult.color != initialDetectedColour) {
            notPastInitialColourFlag = false;
        }

        if(m_periodicColourMatchResult.color == initialDetectedColour && !preventRepeatsFlag
        && !notPastInitialColourFlag) {
            addRotation();
        }

        /*
          Purpose: Confirm when the colour sensor detects a change in colour from the initial colour, allow
          addRotation() to be called again when the colours match once more.

          If the possibleRepeatedFlag is true but the current detected color does not equal the initial
          detected colour, set the possibleRepeatedFlag to false.
        */
        if(preventRepeatsFlag && m_periodicColourMatchResult.color != initialDetectedColour) {
            preventRepeatsFlag = false;
        }

        /*
          Once the control panel has been rotated 4 times, set the finishedFlag to false to end the command.
        */
        if(rotationCount == Constants.ROTATION_GOAL) {
            isFinishedFlag = true;
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
        return isFinishedFlag;
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
        m_controlPanelManipulatorSubsystem.setPower(0);
    }

    public void addRotation() {
        rotationCount += 0.5;
        preventRepeatsFlag = !preventRepeatsFlag;
    }

}
