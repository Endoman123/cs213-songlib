/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/6.8.1/userguide/building_java_projects.html
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    java
    application
    id("org.openjfx.javafxplugin") version "0.0.9"
}

repositories {
    // Use JCenter for resolving dependencies.
    jcenter()
    mavenCentral()
}

dependencies {
    // Use JUnit test framework.
    testImplementation("junit:junit:4.13")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:29.0-jre")
}

javafx {
    version = "11"
    modules("javafx.base", "javafx.controls", "javafx.fxml", "javafx.graphics")
}

tasks.compileJava {
    options.release.set(11)
}

application {
    // Define the main class for the application.
    mainClass.set("edu.rutgers.main.SongLib")
}
