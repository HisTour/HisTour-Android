import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())


plugins {
    alias(deps.plugins.com.android.application)
    alias(deps.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.compose)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.startup.histour"
    compileSdk = deps.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.startup.histour"
        minSdk = deps.versions.android.minSdk.get().toInt()
        targetSdk = deps.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "NATIVE_APP_KEY", properties.getProperty("NATIVE_APP_KEY"))

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            aaptOptions.cruncherEnabled = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
        compose = true
    }

    composeCompiler {
        enableStrongSkippingMode = true
        includeSourceInformation = true
    }
    android {
        kotlin {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(deps.androidx.annotation)

    implementation(deps.lifecycle.ktx)
    implementation(deps.lifecycle.testing)
    implementation(deps.lifecycle.viewmodel.ktx)
    implementation(deps.lifecycle.viewmodel.compose)

    implementation(deps.kotlinx.collections.immutable)

    implementation(deps.coroutine.core)
    implementation(deps.coroutine.android)
    implementation(deps.coroutine.test)
    compileOnly(deps.compose.compiler.extension)
    testImplementation(deps.junit)
    androidTestImplementation(deps.androidx.test.ext.junit)
    androidTestImplementation(deps.espresso.core)

    implementation(platform(deps.compose.bom))
    implementation(deps.bundles.compose)
    implementation(deps.compose.ui)
    implementation(deps.compose.ui.graphics)
    implementation(deps.compose.ui.preview)
    implementation(deps.compose.material3)
    implementation(deps.compose.activity)
    implementation(deps.compose.animation)
    implementation(deps.compose.foundation)
    implementation(deps.compose.constraintlayout)
    implementation(deps.compose.navigation)
    implementation(deps.compose.coil)
    debugImplementation(deps.compose.ui.tooling)
    debugImplementation(deps.compose.ui.test.manifest)

    implementation(deps.retrofit)
    implementation(deps.retrofit.converter.gson)
    implementation(deps.okhttp)
    implementation(deps.okhttp.sse)
    implementation(deps.okhttp.logging.interceptor)
    implementation(deps.gson)

    implementation(deps.room)
    implementation(deps.room.ktx)
    kapt(deps.room.compiler)

    implementation(deps.hilt)
    kapt(deps.hilt.compiler)

    implementation(platform(deps.firebase.bom))
    implementation(deps.firebase.analytics)
    implementation(deps.firebase.crashlytics)
    implementation(deps.google.accompanist.systemuicontroller)

    implementation(deps.kakao.login)
}