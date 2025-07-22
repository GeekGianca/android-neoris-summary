package com.example.neoris.test

enum class NeorisBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    STAGING(".staging"),
    RELEASE
}