/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.util.Color;
/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {


    // Wheel Circumference and Encoder Ticks
    public static final int numberOfTicks = 2048;
    public static final double wheelCircumference = (Math.PI * 6);

    // (VictorSP) Motor Port Values
    public static final int LEFT_MOTOR_1 = 7;
    public static final int LEFT_MOTOR_2 = 8;

    public static final int RIGHT_MOTOR_1 = 0;
    public static final int RIGHT_MOTOR_2 = 1;

    // Encoder Port Values
    public static final int LEFT_ENCODER_1 = 0;
    public static final int LEFT_ENCODER_2 = 1;

    public static final int RIGHT_ENCODER_1 = 2;
    public static final int RIGHT_ENCODER_2 = 3;

    // Drive Straight PID Values
    public static final double DRIVE_STRAIGHT_P = 0;
    public static final double DRIVE_STRAIGHT_I = 0;
    public static final double DRIVE_STRAIGHT_D = 0;

    public static final double DRIVE_STRAIGHT_TARGET = 0;

    // Xbox Controller Values

    public static final int XBOX_CONTROLLER_PORT = 0;
    public static final double JOYSTICK_DEADZONE = 0.05;
    public static final double TRIGGER_DEADZONE = 0.05;

    // Control Panel Manipulator Values
    public static final int PCMID = 0;
    public static final int CPM_SOLENOID = 7;

    public static final int CPM_MOTOR = 4;

    // Control Panel Color Values
    public static final Color BLUE_TARGET = new Color(0.143, 0.427, 0.429);
    public static final Color GREEN_TARGET = new Color(0.197, 0.561, 0.240);
    public static final Color RED_TARGET = new Color(0.561, 0.232, 0.114);
    public static final Color YELLOW_TARGET = new Color(0.361, 0.524, 0.113);

    // TODO: Test the optimal spinning speed.
    public static final double ROTATION_CONTROL_POWER = 0.4;
    public static final double POSITION_CONTROL_POWER = 0.3;
    public static final double ROTATION_GOAL = 4.0;


}
