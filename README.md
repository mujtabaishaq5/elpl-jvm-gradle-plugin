# ELPL JVM Gradle Plugin

[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fplugins.gradle.org%2Fmaven2%2Fio%2Fgithub%2Fmujtabaishaq5%2Felpl%2Fjvm%2Fio.github.mujtabaishaq5.elpl.jvm.gradle.plugin%2Fmaven-metadata.xml&label=Gradle%20Plugin)](https://plugins.gradle.org/plugin/io.github.mujtabaishaq5.elpl.jvm)
[![ELPL Website](https://img.shields.io/badge/ELPL-Official%20Website-blue)](https://elpl-d8625.web.app/)

The **ELPL JVM Gradle Plugin** integrates the [ELPL (English Like Programming Language)](https://elpl-d8625.web.app/) compiler into standard Java and Kotlin build pipelines. It allows developers to write backend, desktop, and general-purpose JVM applications using `.elpl` source files, compiling them natively as part of standard Gradle projects.

By bridging ELPL's English-like, highly readable syntax with the robust JVM ecosystem, developers can build production-ready desktop and server applications without sacrificing access to standard Java libraries, Maven dependencies, or the established Gradle toolchain.

## Table of Contents

- [ELPL Language and Documentation](#elpl-language-and-documentation)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [How It Works](#how-it-works)

## ELPL Language and Documentation

ELPL is a publicly documented programming language featuring a powerful compiler, an interactive browser-based IDE, comprehensive technical documentation, a formal EBNF grammar and a thorough Syntax guide.

Explore the language and its ecosystem:

- **[Official ELPL Website](https://elpl-d8625.web.app/)** — Explore the language and try ELPL in the browser-based IDE.
- **[Language Documentation and Grammar](https://elpl-d8625.web.app/resources.html)** — Access downloadable Markdown documentation, sample code, and EBNF grammar files.
- **[ELPL Compiler (`elplc`)](https://elpl-d8625.web.app/download.html)** — Learn about the compiler and its available commands.
- **[ELPL Ecosystem](https://elpl-d8625.web.app/ecosystem.html)** — Explore ELPL tooling, platform support, and integrations.
- **[ELPL Syntax](https://elpl-d8625.web.app/Syntax.html)** — Explore ELPL Syntax and Grammar.
- **[ELPL Android](https://elpl-d8625.web.app/android.html)** — Explore Exclusive Android Guide.

## Features

- **Standard JVM compilation:** Compile `.elpl` source files directly into standard Java `.class` files.
- **Executable JAR packaging:** Automatically wires into Gradle's build lifecycle to bundle ELPL-compiled output into standard `.jar` files.
- **Seamless Java/Kotlin interoperability:** Use ELPL alongside Java and Kotlin classes, and directly import libraries from Maven Central.
- **Simplified configuration:** Drop the plugin into any standard Gradle project with zero boilerplate.
- **Native toolchain support:** Continue using standard Gradle tasks (`build`, `run`, `jar`) to manage your ELPL backend or desktop application.

## Requirements

Before using the plugin, ensure your development environment meets these requirements:

- **Gradle:** 8.0 or higher
- **Java:** JDK 17 or higher
- *(Note: Unlike the Android plugin, the Android Gradle Plugin (AGP) is **not** required.)*

## Installation

### 1. Apply the plugin

In your standard JVM project's `build.gradle.kts` file, apply the standard Java plugin alongside the ELPL JVM plugin.

```kotlin
plugins {
    id("java")
    id("io.github.mujtabaishaq5.elpl.jvm") version "1.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    // You can pull in standard Java/Kotlin libraries here!
    // implementation("com.google.code.gson:gson:2.10.1")
}