package org.firstinspires.ftc.teamcode.mecanum;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name="LEFT", group="Autonomous")
public class LEFT extends LinearOpMode {
    @Override
    public void runOpMode() {

        VuforiaFTC vf = new VuforiaFTC(hardwareMap, telemetry);
        Slide3 slide = new Slide3(hardwareMap, telemetry);
        Slide4 side = new Slide4(hardwareMap, telemetry);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Servos intake = new Servos(hardwareMap, telemetry);

        intake.reverseMAX();
        sleep(2000);
        slide.runforward(2000,2300);
        VuforiaFTC.barcode_level BarcodeLevel = vf.BarcodeLevel();


        waitForStart();
        while (opModeIsActive()) {

            drive.setPoseEstimate(new Pose2d());
            Trajectory traj0 = drive.trajectoryBuilder(new Pose2d(),false)
                    .lineToSplineHeading(new Pose2d(16, -32, Math.toRadians(-90)),SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                            SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                    .build();
            //Trajectory traj1 = drive.trajectoryBuilder(new Pose2d().plus(new Pose2d(0, 0, Math.toRadians(-90))), true)
            Trajectory traj1 = drive.trajectoryBuilder(traj0.end().plus(new Pose2d(0, 0, Math.toRadians(0))), false)
                    .back(5,SampleMecanumDrive.getVelocityConstraint(7, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                            SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                    .build();

            Trajectory traj2 = drive.trajectoryBuilder(new Pose2d())
                    .lineToLinearHeading(new Pose2d(-7, -17.8, Math.toRadians(0)),SampleMecanumDrive.getVelocityConstraint(40, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                            SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                    .build();

            Trajectory traj3 = drive.trajectoryBuilder(new Pose2d())
                    .forward(26,SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                            SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                    .build();






            if (BarcodeLevel == VuforiaFTC.barcode_level.SLEEVE_1){
                drive.followTrajectory(traj0);
                drive.followTrajectory(traj1);
                intake.forwardMAX();
                sleep(500);
                intake.reverseMAX();
                drive.setPoseEstimate(new Pose2d());
                drive.followTrajectory(traj2);
                side.runforward(850,500);
                drive.turn(Math.toRadians(-85));
                side.runbackward(850,500);
                drive.setPoseEstimate(new Pose2d());
                drive.followTrajectory(traj3);

            } else if (BarcodeLevel == VuforiaFTC.barcode_level.SLEEVE_2){
                drive.followTrajectory(traj0);
                drive.followTrajectory(traj1);
                intake.forwardMAX();
                sleep(500);
                intake.reverseMAX();
                drive.setPoseEstimate(new Pose2d());
                drive.followTrajectory(traj2);
                side.runforward(850,500);
                sleep(1000);
                side.runbackward(850,500);
            } else {
                drive.followTrajectory(traj0);
                drive.followTrajectory(traj1);
                intake.forwardMAX();
                sleep(500);
                intake.reverseMAX();
                drive.setPoseEstimate(new Pose2d());
                drive.followTrajectory(traj2);
                side.runforward(850,500);
                drive.turn(Math.toRadians(85));
                side.runbackward(850,500);
                drive.setPoseEstimate(new Pose2d());
                drive.followTrajectory(traj3);

            }
            break;
        }
    }
}