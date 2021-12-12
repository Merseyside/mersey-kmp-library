enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
    }

    val catalogVersions = "1.2.1"
    val group = "io.github.merseyside"
    versionCatalogs {
        val multiplatformLibs by creating {
            from("$group:catalog-version-multiplatform:$catalogVersions")
        }

        val androidLibs by creating {
            from("$group:catalog-version-android:$catalogVersions")
            version("merseyLib", "1.7.1")
        }

        val common by creating {
            from("$group:catalog-version-common:$catalogVersions")
        }
    }
}

include(":archy-core")
include(":utils-core")
include(":archy-android")

private val isLocalAndroidDependencies = false

if (isLocalAndroidDependencies) {

    include(":utils")
    project(":utils").projectDir =
        File(rootDir.parent, "mersey-android-library/utils")

    include(":archy")
    project(":archy").projectDir =
        File(rootDir.parent, "mersey-android-library/archy")

    include(":adapters")
    project(":adapters").projectDir =
        File(rootDir.parent, "mersey-android-library/adapters")

    include(":animators")
    project(":animators").projectDir =
        File(rootDir.parent, "mersey-android-library/animators")

}

rootProject.name = "kmm-support-library"
