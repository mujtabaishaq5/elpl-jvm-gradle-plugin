package com.syedm.elpl.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.*

class ElplJvmPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // 1. Apply standard Java behaviors
        project.plugins.apply(JavaPlugin::class.java)

        // 2. Register the ELPL compilation task
        val compileElpl = project.tasks.register<CompileElplJvmTask>("compileElpl") {
            sourceDir.set(project.layout.projectDirectory.dir("src/main/elpl"))
            outputDir.set(project.layout.buildDirectory.dir("classes/elpl/main"))
            dependencyClasspath.from(project.configurations.getByName("compileClasspath"))
        }

        // 3. Register the ELPL output directory with Gradle's standard SourceSet
        val sourceSets = project.extensions.getByType<SourceSetContainer>()
        sourceSets.named("main").configure {
            // Cast the FileCollection to ConfigurableFileCollection to unlock the .from() method
            val classesDirs = output.classesDirs as ConfigurableFileCollection
            classesDirs.from(compileElpl.flatMap { it.outputDir })
        }

        // 4. Ensure standard lifecycle tasks trigger ELPL compilation
        project.tasks.named("classes") {
            dependsOn(compileElpl)
        }
    }
}