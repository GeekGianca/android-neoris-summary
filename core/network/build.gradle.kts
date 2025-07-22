plugins {
    alias(libs.plugins.neoris.android.application.library)
    alias(libs.plugins.neoris.android.application.hilt)
}

android {
    namespace = "com.example.neoris.network"
    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://api.jsonbin.io/\"")
        buildConfigField("String", "VERSION", "\"v3/\"")
    }
    buildTypes {
        release {
            buildConfigField("String", "BASE_URL", "\"https://api.jsonbin.io/\"")
        }
        create("staging") {
            buildConfigField("String", "BASE_URL", "\"https://api.jsonbin.io/\"")
        }
    }
    android.buildFeatures.buildConfig = true
}

dependencies {
    api(projects.core.model)
    api(projects.core.shared)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}