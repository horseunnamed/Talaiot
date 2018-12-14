package com.cdsap.talaiot


import com.cdsap.talaiot.entities.Clock
import com.cdsap.talaiot.entities.TaskLength
import com.cdsap.talaiot.entities.TaskMessageState
import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState


class TalaiotListener(
    private val buildTimeTrackerPluginCustom: TalaiotPlugin,
    private val talaiotExtension: TalaiotExtension
) : BuildListener, TaskExecutionListener {
    private val timing = mutableListOf<TaskLength>()
    private val clock: Clock = Clock()

    override fun settingsEvaluated(settings: Settings) {
    }

    override fun buildFinished(result: BuildResult) {
        buildTimeTrackerPluginCustom.onFinished(result, timing, talaiotExtension)
    }

    override fun projectsLoaded(gradle: Gradle) {
    }

    override fun buildStarted(gradle: Gradle) {
    }

    override fun projectsEvaluated(gradle: Gradle) {
    }

    override fun beforeExecute(task: Task) {
    }

    override fun afterExecute(task: Task, state: TaskState) {
        timing.add(
            TaskLength(
                ms = clock.getTimeInMs(),
                path = task.path,
                state = when (state.skipMessage) {
                    "UP-TO-DATE" -> TaskMessageState.UP_TO_DATE
                    "FROM-CACHE" -> TaskMessageState.FROM_CACHE
                    "NO-SOURCE" -> TaskMessageState.NO_SOURCE
                    else -> TaskMessageState.NO_MESSAGE_STATE
                }
            )
        )

    }
}
