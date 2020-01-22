package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import frc.robot.Constants;

public class PneumaticsSubsystem implements Subsystem {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.
    private Compressor primaryCompressor;
// Control Panel Maniplulator
    private Solenoid cpm;

    private boolean currentlyExtended = false;
    /**
     * The Singleton instance of this PneumaticsSubsystem. External classes should
     * use the {@link #getInstance()} method to get the instance.
     */
    private final static PneumaticsSubsystem INSTANCE = new PneumaticsSubsystem();

    /**
     * Creates a new instance of this PneumaticsSubsystem.
     * This constructor is private since this class is a Singleton. External classes
     * should use the {@link #getInstance()} method to get the instance.
     */
    private PneumaticsSubsystem() {
        primaryCompressor = new Compressor(Constants.PCMID);
        primaryCompressor.start();
        cpm = new Solenoid(Constants.PCMID, Constants.intakeSolenoidChannel);



    }

    public void flipSolenoid() {
        currentlyExtended = !currentlyExtended;
        cpm.set(currentlyExtended);
    }

    public boolean getIsExtended() {
        return currentlyExtended;
    }
    /**
     * Returns the Singleton instance of this PneumaticsSubsystem. This static method
     * should be used -- {@code PneumaticsSubsystem.getInstance();} -- by external
     * classes, rather than the constructor to get the instance of this class.
     */
    public static PneumaticsSubsystem getInstance() {
        return INSTANCE;
    }

}
