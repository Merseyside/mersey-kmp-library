object LibraryModules {

    object MultiPlatform {

        val archyCore = MultiPlatformModule(
            name = ":archy-core",
            exported = true
        )

        val utilsCore = MultiPlatformModule(
            name = ":utils-core",
            exported = true
        )
    }

    const val archyAndroid = ":archy-android"

    const val archy = ":archy"
    const val adapters = ":adapters"
    const val animators = ":animators"
    const val utils = ":utils"
}