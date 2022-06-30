buildscript {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    `nexus-config`
}

allprojects {
    group = "io.github.merseyside"
    version = "1.5.1"
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.buildDir)
}
