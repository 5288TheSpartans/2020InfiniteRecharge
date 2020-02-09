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

    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private Compressor m_primaryCompressor;
    private Solenoid m_controlPanelSolenoid;
    private VictorSP m_controlPanelMotor;
    private ColorSensorV3 m_colourSensor;

    private boolean currentlyExtended = false;

    public ControlPanelManipulatorSubsystem() {
        System.out.println("PneumaticsSubsystem CONSTRUCTOR initialized!");

        m_primaryCompressor = new Compressor(Constants.PCMID);
        m_primaryCompressor.start();
        m_controlPanelSolenoid = new Solenoid(Constants.PCMID, Constants.CPM_SOLENOID);
        m_controlPanelMotor = new VictorSP(Constants.CPM_MOTOR);

        m_colourSensor = new ColorSensorV3(i2cPort);
    }

    public void flipSolenoid() {
        System.out.println("Solenoid activated");
        currentlyExtended = !currentlyExtended;
        m_controlPanelSolenoid.set(currentlyExtended);
    }

    public Color getColour() {
        return(m_colourSensor.getColor());
    }
    public boolean getIsExtended() {
        return currentlyExtended;
    }
}
