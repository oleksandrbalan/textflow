plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
}

kotlin {
    jvmToolchain(libs.versions.java.get().toInt())
}

android {
    namespace = "eu.wewox.textflow"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "eu.wewox.textflow"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":textflow"))

    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.activity.compose)
}
