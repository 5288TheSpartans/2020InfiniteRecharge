package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import frc.robot.Constants;

public class PneumaticsSubsystem extends SubsystemBase {

    // Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.
    private Compressor primaryCompressor;

    // Control Panel Maniplulator
    private Solenoid controlPanelSolenoid;

    private boolean currentlyExtended = false;

    public PneumaticsSubsystem() {
        System.out.println("PneumaticsSubsystem CONSTRUCTOR initialized!");
        primaryCompressor = new Compressor(Constants.PCMID);
        primaryCompressor.start();
        controlPanelSolenoid = new Solenoid(Constants.PCMID, Constants.intakeSolenoidChannel);
    }

    public void flipSolenoid() {
        System.out.println("Solenoid activated");
        currentlyExtended = !currentlyExtended;
        controlPanelSolenoid.set(currentlyExtended);
    }

    public boolean getIsExtended() {
        return currentlyExtended;
    }
}
