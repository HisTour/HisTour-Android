// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(deps.plugins.com.android.application) apply false
    alias(deps.plugins.org.jetbrains.kotlin.android) apply false
    alias(deps.plugins.org.jetbrains.kotlin.compose) apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(deps.gradle.hilt)
        classpath(deps.gradle.android)
        classpath(deps.gradle.goolge.service)
        classpath(deps.gradle.firebase.crashlytics)
    }
}

true // Needed to make the Suppress annotation work for the plugins block