import com.google.protobuf.gradle.id
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())


plugins {
    alias(deps.plugins.com.android.application)
    alias(deps.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.compose)
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    id("com.google.firebase.crashlytics")
    kotlin("kapt")
    id("com.google.protobuf") version "0.9.3"
}

kapt {
    correctErrorTypes = true
}

android {
    namespace = "com.startup.histour"
    compileSdk = deps.versions.android.compileSdk.get().toInt()

    signingConfigs {
        create("release") {
            keyAlias = properties.getProperty("MY_KEY_ALIAS")
            keyPassword = properties.getProperty("MY_KEY_PASSWORD")
            storeFile = file(properties.getProperty("MY_KEYSTORE_PATH"))
            storePassword = properties.getProperty("MY_STORE_PASSWORD")
        }
    }
    defaultConfig {
        applicationId = "com.startup.histour"
        minSdk = deps.versions.android.minSdk.get().toInt()
        targetSdk = deps.versions.android.targetSdk.get().toInt()
        versionCode = 3
        versionName = "1.0.1"

        buildConfigField("String", "NATIVE_APP_KEY", properties.getProperty("NATIVE_APP_KEY"))
        buildConfigField("String", "SERVER_DOMAIN", properties.getProperty("SERVER_DOMAIN"))
        buildConfigField("String", "SSE_SERVER_DOMAIN", properties.getProperty("SSE_SERVER_DOMAIN"))

        val nativeAppKey = properties.getProperty("NATIVE_APP_KEY")?.trim('"') ?: ""
        manifestPlaceholders["NATIVE_APP_KEY"] = nativeAppKey

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            aaptOptions.cruncherEnabled = false
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true //코드 최적화
            isShrinkResources = true //리소스 최적화
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
    implementation(deps.compose.hilt.navigation)
    implementation(deps.compose.coil)
    debugImplementation(deps.compose.ui.tooling)
    debugImplementation(deps.compose.ui.test.manifest)

    implementation(deps.datastore.preference)
    implementation(deps.datastore.proto)
    implementation(deps.proto.buf.javalite)
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
    implementation(deps.firebase.remote.config)
    implementation(deps.google.accompanist.systemuicontroller)

    implementation(deps.kakao.login)

    implementation(deps.markdown.viewer)
    implementation(deps.lottie)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.21.12"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}
