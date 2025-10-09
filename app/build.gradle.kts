plugins {
    id("com.android.application") // Indica que es una app de Android.
    id("org.jetbrains.kotlin.android") // Permite usar Kotlin.
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"  // Autoescribe código.
    id("kotlin-parcelize") // Permite pasar datos entre pantallas.
}

android {
    namespace = "com.martinvergara_diegoboggle.pawschedule" // Nombre del proyecto.
    compileSdk = 36 // Versión de Android.

    defaultConfig {
        applicationId = "com.martinvergara_diegoboggle.pawschedule" // Identificador de la app.
        minSdk = 24 // La versión más antigua de Android que puede usarla.
        targetSdk = 36// La versión para la que está optimizada.
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx) // Utilidades básicas de Kotlin para Android.
    implementation(libs.androidx.appcompat) // Para que la app sea compatible con versiones antiguas de Android.
    implementation(libs.material) // Componentes visuales: botones, menús, etc.
    implementation(libs.androidx.constraintlayout) // Para organizar los elementos en la pantalla.
    implementation(libs.androidx.navigation.fragment.ktx) // Gestiona la navegación.
    implementation(libs.androidx.navigation.ui.ktx) // Conecta la navegación con la interfaz.
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // El cerebro de cada pantalla, donde va la lógica.
    implementation(libs.androidx.lifecycle.livedata.ktx) // Permite que la pantalla reaccione a cambios en los datos.
    implementation(libs.androidx.room.runtime) // La base de datos.
    implementation(libs.androidx.room.ktx) // Ayudas para usar la base de datos con Kotlin.
    ksp(libs.androidx.room.compiler.v261) // Genera el código necesario para que Room funcione.
    implementation(libs.kotlinx.coroutines.android) // Evita que la app se congele.
    testImplementation(libs.junit) // Framework de pruebas.
    androidTestImplementation(libs.androidx.junit)
}