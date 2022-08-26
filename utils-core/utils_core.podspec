Pod::Spec.new do |spec|
    spec.name                     = 'utils_core'
    spec.version                  = '1.5.2'
    spec.homepage                 = 'https://github.com/Merseyside/mersey-kmp-library/tree/master/utils-core'
    spec.source                   = { :http=> ''}
    spec.authors                  = ''
    spec.license                  = ''
    spec.summary                  = 'A Kotlin multiplatform mobile library with useful utils'
    spec.vendored_frameworks      = 'build/cocoapods/framework/utils_core.framework'
    spec.libraries                = 'c++'
                
    spec.dependency 'Reachability', '3.2'
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':utils-core',
        'PRODUCT_MODULE_NAME' => 'utils_core',
    }
                
    spec.script_phases = [
        {
            :name => 'Build utils_core',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$COCOAPODS_SKIP_KOTLIN_BUILD" ]; then
                  echo "Skipping Gradle build task invocation due to COCOAPODS_SKIP_KOTLIN_BUILD environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]
                
end