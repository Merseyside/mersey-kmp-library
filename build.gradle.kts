plugins {
    `nexus-config`
}

allprojects {
    group = "io.github.merseyside"
    version = "1.5.3"
}

buildscript { // disable pod install tasks until find a solution
    repositories {
        gradlePluginPortal()
    }

    if (!isBuildIos()) {
        with(project.gradle.startParameter.excludedTaskNames) {
            add("podImport")
            add("podInstall")
            add("podGenIOS")
//            add("podSetupBuildReachabilityIphoneos")
//            add("podSetupBuildReachabilityIphonesimulator")
//            add("podBuildReachabilityIphoneos")
//            add("podBuildReachabilityIphonesimulator")
//            add("cinteropReachabilityIosX64")
//            add("cinteropReachabilityIosSimulatorArm64")
//            add("cinteropReachabilityIosArm64")
        }
    }
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.buildDir)
}