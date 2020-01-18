/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public enum driveTrain {

        // (VictorSP) Motor Port Values
        LEFT_MOTOR_1 (7), LEFT_MOTOR_2 (8),
        RIGHT_MOTOR_1 (0), RIGHT_MOTOR_2 (1),

        // Encoder Port Values
        LEFT_ENCODER_1(0), LEFT_ENCODER_2(1),
        RIGHT_ENCODER_1(2), RIGHT_ENCODER_2(3);

        private final int value;

        driveTrain(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}