package com.syedm.elpl.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.*

class ElplJvmPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // 1. Apply standard Java behaviors
        project.plugins.apply(JavaPlugin::class.java)

        // 2. Register the ELPL compilation task using native Kotlin DSL syntax
        val compileElpl = project.tasks.register<CompileElplJvmTask>("compileElpl") {
            // Default source directory: src/main/elpl
            sourceDir.set(project.layout.projectDirectory.dir("src/main/elpl"))
            // Output directory for standard classes
            outputDir.set(project.layout.buildDirectory.dir("classes/elpl/main"))

            // Hook into standard Java compile classpath
            dependencyClasspath.from(project.configurations.getByName("compileClasspath"))
        }

        // 3. Make the standard 'classes' task wait for ELPL compilation
        project.tasks.named("classes") {
            dependsOn(compileElpl)
        }

        // 4. Inject the compiled ELPL classes into the final executable JAR
        project.tasks.named<Jar>("jar") {
            from(compileElpl.flatMap { it.outputDir })
        }
    }
}