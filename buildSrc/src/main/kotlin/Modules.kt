object Modules {
    object Android {
        object MerseyLibs {
            const val archy = ":archy"
            const val adapters = ":adapters"
            const val animators = ":animators"
            const val utils = ":utils"

            const val archyAndroid = ":archy-android"
        }
    }

    object MultiPlatform {

        object MerseyLibs {
            val archy = ":archy-core"
            val utils = ":utils-core"
        }

        val mppLibrary = ":mpp-library"
        val domain = "$mppLibrary:domain"
        val core = "$mppLibrary:core"

        val newsApi = "$mppLibrary:library:newsApi"

        object Feature {
            val config ="$mppLibrary:feature:config"
            val news = "$mppLibrary:feature:news"
        }
    }
}