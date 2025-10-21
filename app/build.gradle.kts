plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "martinvergara_diegoboggle.pawschedule"
    compileSdk = 34 // Versión estable (Android 14)

    defaultConfig {
        applicationId = "martinvergara_diegoboggle.pawschedule"
        minSdk = 24 // Compatible con la mayoría de dispositivos
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

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
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3" // Versión estable del compilador de Compose
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Versiones estables de las librerías
    val coreKtxVersion = "1.12.0"
    val lifecycleVersion = "2.6.2"
    val activityComposeVersion = "1.8.2"
    val composeBomVersion = "2023.10.01"
    val navigationVersion = "2.7.6"
    val junitVersion = "4.13.2"
    val androidJunitVersion = "1.1.5"
    val espressoVersion = "3.5.1"

    // Dependencias de Android y Jetpack Compose
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // --- DEPENDENCIAS CLAVE PARA TU PROBLEMA ---
    // 1. Para NavController, navigate(), etc.
    implementation("androidx.navigation:navigation-compose:$navigationVersion")
    // 2. Para poder usar viewModel()
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    // 3. Para poder usar .collectAsState()
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")
    // --- FIN DE DEPENDENCIAS CLAVE ---
    implementation("androidx.compose.material:material-icons-extended-android:1.6.5")

    // Dependencias de Testing
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$androidJunitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}