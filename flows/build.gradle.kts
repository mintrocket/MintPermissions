plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("maven-publish")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.libraryminSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        consumerProguardFiles("consumer-rules.pro")
    }

    resourcePrefix = "mint_"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-Xexplicit-api=strict"
    }

    namespace = "ru.mintrocket.lib.mintpermissions.flows"
}

dependencies {
    // Common
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)

    // App common
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(project(":tools"))
    implementation(project(":library"))

}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.mintrocket"
                artifactId = "mintpermissions-flows"
                version = libs.versions.libraryVersionName.get()
            }
        }
    }
}