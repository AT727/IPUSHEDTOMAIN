/* (C) Robolancers 2024 */
package org.robolancers321.commands;

import org.robolancers321.subsystems.intake.Retractor;
import org.robolancers321.subsystems.intake.Sucker;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class PathAndIntake extends SequentialCommandGroup {

    Retractor retractor;
    Sucker sucker;

  public PathAndIntake(String pathName) {

    this.retractor = Retractor.getInstance();
    this.sucker = Sucker.getInstance();

    this.addCommands(
        new ParallelCommandGroup(
            AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory(pathName)),
            new ParallelDeadlineGroup(
                new WaitUntilCommand(this.sucker::noteDetected),
                this.retractor.moveToIntake(),
                this.sucker.in()
        )
    ));
  }
}
