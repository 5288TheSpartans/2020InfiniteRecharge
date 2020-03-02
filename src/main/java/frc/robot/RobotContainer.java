package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ControlPanelManipulatorSubsystem;


public class RobotContainer {

  // Create instances of subsystems and controllers here.
  public final DriveTrainSubsystem m_driveTrain = new DriveTrainSubsystem();
  public static final ControlPanelManipulatorSubsystem m_controlPanelManipulator = new ControlPanelManipulatorSubsystem();
  public static final XboxController m_xboxController = new XboxController(Constants.XBOX_CONTROLLER_PORT);

//  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Configure the button bindings
    configureButtonBindings();
  }


  private void configureButtonBindings() {
    m_driveTrain.setDefaultCommand(new ArcadeDriveCommand(m_driveTrain, () -> m_xboxController.getY(GenericHID.Hand.kLeft),
            () -> m_xboxController.getX(GenericHID.Hand.kRight)));
    new JoystickButton(m_xboxController, Button.kBumperRight.value).whenPressed(m_controlPanelManipulator::flipSolenoid,
            m_controlPanelManipulator);
//    Define button with their respective command
//    JoystickButton xButton = new JoystickButton(m_xboxController, 3);
//    JoystickButton yButton = new JoystickButton(m_xboxController, 4);
//    JoystickButton aButton = new JoystickButton(m_xboxController, 1);
//    JoystickButton bButton = new JoystickButton(m_xboxController, 2);
//    JoystickButton rightBumper = new JoystickButton(m_xboxController, 6);
//    JoystickButton leftBumper = new JoystickButton(m_xboxController, 5);
//    JoystickButton startButton = new JoystickButton(m_xboxController, 8);
//    JoystickButton selectButton = new JoystickButton(m_xboxController, 7);
//    JoystickButton leftStickButton = new JoystickButton(m_xboxController, 9);
//    JoystickButton rightStickButton = new JoystickButton(m_xboxController, 10);
  }
//  private double xboxGetLeftStickX() {
//     return m_xboxController.getRawAxis(0);
//  }
//
//  private double xboxGetLeftStickY() {
//     return m_xboxController.getRawAxis(1);
//  }
//
//  private double xboxGetRightStickX() {
//     return m_xboxController.getRawAxis(4);
//  }
//
//  private double xboxGetRightStickY() {
//    return m_xboxController.getRawAxis(5);
//  }

  private double xboxGetLeftAnalogTrigger() {
    return m_xboxController.getTriggerAxis(GenericHID.Hand.kLeft);
  }

  private double xboxGetRightAnalogTrigger() {
    return m_xboxController.getTriggerAxis(GenericHID.Hand.kRight);
  }

  private double xboxGetDPadValue() {
    try {
      return m_xboxController.getPOV();
    } catch (NullPointerException e) {
      return -1;
    }
  }

  private boolean xboxGetRightBumperStatus() {
    return m_xboxController.getBumperPressed(GenericHID.Hand.kRight);
  }

  private boolean xboxGetLeftBumperStatus() {
    return m_xboxController.getBumperPressed(GenericHID.Hand.kLeft);
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
