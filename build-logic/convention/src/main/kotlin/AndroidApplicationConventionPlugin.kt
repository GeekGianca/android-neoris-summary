import com.android.build.api.dsl.ApplicationExtension
import com.example.neoris.test.Config
import com.example.neoris.test.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = Config.TARGET_SDK
                defaultConfig.versionCode = Config.VERSION_CODE
                defaultConfig.versionName = Config.VERSION_NAME
            }
        }
    }
}