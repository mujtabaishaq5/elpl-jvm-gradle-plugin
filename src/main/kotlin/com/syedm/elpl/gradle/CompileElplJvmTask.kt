package com.syedm.elpl.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.*
import org.gradle.process.ExecOperations
import java.io.File
import java.net.URL
import javax.inject.Inject

@CacheableTask
abstract class CompileElplJvmTask @Inject constructor(
    private val execOperations: ExecOperations
) : DefaultTask() {

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val sourceDir: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @get:InputFiles
    @get:Classpath
    abstract val dependencyClasspath: ConfigurableFileCollection

    @TaskAction
    fun execute() {
        val outDir = outputDir.get().asFile
        outDir.deleteRecursively()
        outDir.mkdirs()

        // Resolve the compiler JAR (Local -> Cache -> Download)
        val localDevJar = File(System.getProperty("user.home"), "Projects/ELPL 2/elpl-compiler.jar")
        val compilerJarFile: File

        if (localDevJar.exists()) {
            compilerJarFile = localDevJar
            println("✅ ELPL: Using LOCAL development compiler jar from -> ${localDevJar.absolutePath}")
        } else {
            val globalCacheDir = File(System.getProperty("user.home"), ".elpl/cache")
            if (!globalCacheDir.exists()) globalCacheDir.mkdirs()

            compilerJarFile = File(globalCacheDir, "elpl-compiler.jar")

            if (!compilerJarFile.exists()) {
                println("=== ELPL: Downloading compiler binary from GitHub Releases ===")
                // Make sure to update this URL to your latest stable JVM compiler release when ready
                val downloadUrl = URL("https://github.com/mujtabaishaq5/elpl-gradle-plugin/releases/download/v1.0.16/elpl-compiler.jar")
                downloadUrl.openStream().use { input ->
                    compilerJarFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }

        // Build the standard JVM classpath
        val fullClasspath = dependencyClasspath.files
            .joinToString(File.pathSeparator) { it.absolutePath }

        val jvmClassPath = "${compilerJarFile.absolutePath}${File.pathSeparator}$fullClasspath"

        // Execute the compiler
        execOperations.exec {
            val args = mutableListOf(
                "java", "-cp", jvmClassPath, "syed.Main",
                "build",
                "--classpath", fullClasspath,
                "-o", outDir.absolutePath,
                sourceDir.get().asFile.absolutePath
            )

            commandLine(args)
            standardOutput = System.out
            errorOutput = System.err
        }
    }
}