plugins {
    alias(libs.plugins.neoris.android.application.library)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.neoris.domain"
    buildTypes {
        create("staging") {}
    }
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.javax.inject)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}