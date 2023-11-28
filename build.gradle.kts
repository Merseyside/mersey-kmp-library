allprojects {
    plugins.withId("org.gradle.maven-publish") {
        group = "io.github.merseyside"
        version = multiplatformLibs.versions.mersey.kmm.get()
    }

    task("testClasses").doLast {
        println("This is a dummy testClasses task")
    }
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.layout.buildDirectory)
}