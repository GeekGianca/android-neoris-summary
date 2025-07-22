plugins {
    alias(libs.plugins.neoris.android.application.library)
    alias(libs.plugins.neoris.android.application.hilt)
}

android {
    namespace = "com.example.neoris.shared"
    buildTypes {
        create("staging") {}
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}