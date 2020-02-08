/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  // Create instances of subsystems and controllers here.
  private final DriveTrainSubsystem m_driveTrain = new DriveTrainSubsystem();
  private final PneumaticsSubsystem m_pneumatics = new PneumaticsSubsystem();
  private final XboxController m_xboxController = new XboxController(Constants.XBOX_CONTROLLER_PORT);

//  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Configure the button bindings
    configureButtonBindings();
    m_driveTrain.setDefaultCommand(new ArcadeDriveCommand(m_driveTrain, () -> m_xboxController.getY(GenericHID.Hand.kLeft),
            () -> m_xboxController.getX(GenericHID.Hand.kRight)));
  }


  private void configureButtonBindings() {
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
