package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import frc.robot.Constants;

public class ControlPanelManipulatorSubsystem extends SubsystemBase {

    // Define variables used in the subsystems.
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private Compressor m_primaryCompressor;
    private Solenoid m_controlPanelSolenoid;
    private VictorSP m_controlPanelMotor;
    private ColorSensorV3 m_colourSensor;

    private boolean currentlyExtended = false;

    /**
     * CONSTRUCTOR
     */
    public ControlPanelManipulatorSubsystem() {
        System.out.println("PneumaticsSubsystem CONSTRUCTOR initialized!");

        m_primaryCompressor = new Compressor(Constants.PCMID);
        m_primaryCompressor.start();
        m_controlPanelSolenoid = new Solenoid(Constants.PCMID, Constants.CPM_SOLENOID);
        m_controlPanelMotor = new VictorSP(Constants.CPM_MOTOR);

        m_colourSensor = new ColorSensorV3(i2cPort);
    }

    /**
     * Flips the control panel manipulator's position and sets a boolean value for whether it is currently extended.
     */
    public void flipSolenoid() {
        System.out.println("Solenoid activated");
        currentlyExtended = !currentlyExtended;
        m_controlPanelSolenoid.set(currentlyExtended);
    }

    /**
     * Get the current colour detected by the colourSensor.
     * @return Detected colour.
     */
    public Color getColour() {
        return m_colourSensor.getColor();
    }

    /**
     * Set control panel motor speed;
     * @param power Value between 1 and -1. Negative values represent backwards motion.
     */
    public void setPower(double power) {
        m_controlPanelMotor.set(power);
    }

    /**
     * Gets whether the control panel manipulator is extended or not.
     * @return True or false.
     */
    public boolean getIsExtended() { return currentlyExtended; }
}