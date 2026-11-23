package org.plantuml.idea

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

/**
 * Makes sure [PlantUmlApplicationService] exists once a project is opened,
 * which registers the global editor listeners. The service itself is only created once per application.
 */
class PlantUmlStartupActivity : ProjectActivity {
    override suspend fun execute(project: Project) {
        PlantUmlApplicationService.getInstance()
    }
}
