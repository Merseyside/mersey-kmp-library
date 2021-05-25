plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.5.0"
    kotlin("plugin.serialization") version "1.5.0"
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
    gradlePluginPortal()

    maven { url = uri("https://dl.bintray.com/icerockdev/plugins") }
}

val multiplatform = "0.9.2"
val kotlin = "1.5.0"
val gradle = "4.2.1"
val mavenVersion = "2.1"
val resources = "0.15.0"
val sqldelight = "1.5.0"

dependencies {
    implementation("dev.icerock:mobile-multiplatform:$multiplatform")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
    implementation("com.android.tools.build:gradle:$gradle")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlin")
    implementation("dev.icerock.moko:resources-generator:$resources")
    implementation("com.squareup.sqldelight:gradle-plugin:$sqldelight")
}