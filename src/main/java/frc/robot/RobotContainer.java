/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.Constants;


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
//  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final DriveTrainSubsystem m_driveTrain = DriveTrainSubsystem.getInstance();
  private final XboxController m_xboxController = new XboxController(Constants.XBOX_CONTROLLER_PORT);

//  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_driveTrain.register();
    // Configure the button bindings
    configureButtonBindings();
    m_driveTrain.setDefaultCommand(new ArcadeDriveCommand(m_driveTrain, xboxGetLeftStickY(), xboxGetRightStickX()));
  }


  private void configureButtonBindings() {
    defineButtons();

  }
  private double xboxGetLeftStickX() {
    // return xboxController.getRawAxis(0);
    return m_xboxController.getX(Hand.kLeft);
  }

  private double xboxGetLeftStickY() {
    // return xboxController.getRawAxis(1);
    return m_xboxController.getY(Hand.kLeft);
  }

  private double xboxGetRightStickX() {
    // return xboxController.getRawAxis(4);
    return m_xboxController.getX(Hand.kRight);
  }

  private double xboxGetRightStickY() {
    // return xboxController.getRawAxis(5);
    return m_xboxController.getY(Hand.kRight);
  }

  private double xboxGetLeftAnalogTrigger() {
    return m_xboxController.getTriggerAxis(Hand.kLeft);
  }

  private double xboxGetRightAnalogTrigger() {
    return m_xboxController.getTriggerAxis(Hand.kRight);
  }

  private double xboxGetDPadValue() {
    try {
      return m_xboxController.getPOV();
    } catch (NullPointerException e) {
      return -1;
    }
  }

  private boolean xboxGetRightBumperStatus() {
    return m_xboxController.getBumperPressed(Hand.kRight);
  }

  private boolean xboxGetLeftBumperStatus() {
    return m_xboxController.getBumperPressed(Hand.kLeft);
  }

  private void defineButtons() {
    JoystickButton xButton = new JoystickButton(m_xboxController, 3);
    JoystickButton yButton = new JoystickButton(m_xboxController, 4);
    JoystickButton aButton = new JoystickButton(m_xboxController, 1);
    JoystickButton bButton = new JoystickButton(m_xboxController, 2);
    JoystickButton rightBumper = new JoystickButton(m_xboxController, 6);
    JoystickButton leftBumper = new JoystickButton(m_xboxController, 5);
    JoystickButton startButton = new JoystickButton(m_xboxController, 8);
    JoystickButton selectButton = new JoystickButton(m_xboxController, 7);
    JoystickButton leftStickButton = new JoystickButton(m_xboxController, 9);
    JoystickButton rightStickButton = new JoystickButton(m_xboxController, 10);
  }
  /*
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
//  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
//    return m_autoCommand;
//  }
}
