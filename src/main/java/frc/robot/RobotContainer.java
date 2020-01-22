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

  private final static RobotContainer INSTANCE = new RobotContainer();


//  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {


    
    // Configure the button bindings
    configureButtonBindings();
  }


  private void configureButtonBindings() {
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
  public double xboxGetLeftStickX() {
    // return xboxController.getRawAxis(0);
    return m_xboxController.getX(Hand.kLeft);
  }

  public double xboxGetLeftStickY() {
    // return xboxController.getRawAxis(1);
    return m_xboxController.getY(Hand.kLeft);
  }

  public double xboxGetRightStickX() {
    // return xboxController.getRawAxis(4);
    return m_xboxController.getX(Hand.kRight);
  }

  public double xboxGetRightStickY() {
    // return xboxController.getRawAxis(5);
    return m_xboxController.getY(Hand.kRight);
  }

  public double xboxGetLeftAnalogTrigger() {
    return m_xboxController.getTriggerAxis(Hand.kLeft);
  }

  public double xboxGetRightAnalogTrigger() {
    return m_xboxController.getTriggerAxis(Hand.kRight);
  }

  public double xboxGetDPadValue() {
    try {
      return m_xboxController.getPOV();
    } catch (NullPointerException e) {
      return -1;
    }
  }

  public boolean xboxGetRightBumperStatus() {
    return m_xboxController.getBumperPressed(Hand.kRight);
  }

  public boolean xboxGetLeftBumperStatus() {
    return m_xboxController.getBumperPressed(Hand.kLeft);
  }

  public static RobotContainer getInstance() {return INSTANCE;}
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
