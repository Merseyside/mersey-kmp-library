enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    repositories {
        mavenCentral()
        google()

        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }

    val catalogVersions = "1.4.8"
    val group = "io.github.merseyside"
    versionCatalogs {

        val catalogGradle by creating {
            from("$group:catalog-version-gradle:$catalogVersions")
        }
    }
}