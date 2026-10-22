import groovy.lang.Closure

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "2.2.1"
}

group = "io.github.mujtabaishaq5"
version = "1.0.0" // Reset to 1.0.0 for this new plugin

repositories {
    google()
    mavenCentral()
}

gradlePlugin {
    // Required metadata for the Portal pointing to the new repo
    website.set("https://github.com/mujtabaishaq5/elpl-jvm-gradle-plugin")
    vcsUrl.set("https://github.com/mujtabaishaq5/elpl-jvm-gradle-plugin.git")

    plugins {
        create("elplJvmPlugin") {
            // 🔥 CRITICAL: Must be a unique ID, distinct from your Android plugin
            id = "io.github.mujtabaishaq5.elpl.jvm"
            implementationClass = "com.syedm.elpl.gradle.ElplJvmPlugin"
            displayName = "ELPL JVM Plugin"
            description = "Compiles ELPL source code into standard JVM .class and .jar files"
            tags.set(listOf("elpl", "compiler", "jvm", "language"))
        }
    }
}

dependencies {
    // No Android Gradle Plugin dependency needed!
    // The `kotlin-dsl` plugin automatically provides the standard Gradle APIs.
}