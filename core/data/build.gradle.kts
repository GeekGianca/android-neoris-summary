plugins {
    alias(libs.plugins.neoris.android.application.library)
    alias(libs.plugins.neoris.android.application.hilt)
    id("kotlinx-serialization")
}
android {
    namespace = "com.example.neoris.data"
    testOptions.unitTests.isIncludeAndroidResources = true
    testOptions.unitTests.isReturnDefaultValues = true
    buildTypes {
        create("staging") {}
    }
}

dependencies {
    api(projects.core.network)
    api(projects.core.shared)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}