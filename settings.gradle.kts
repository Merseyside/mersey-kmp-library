enableFeaturePreview("GRADLE_METADATA")

include(":app")

include(":kmp-clean-mvvm-arch")
include(":kmp-utils")

private val isLocalDependencies = true

if (isLocalDependencies) {

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

rootProject.name = "merseykmplibrary"