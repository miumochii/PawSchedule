// build.gradle.kts (Project root)

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    // Definición de plugins y sus versiones principales
    // 'apply false' indica que solo se declaran aquí (no se aplican directamente)
    id("com.android.application") version "8.13.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
}

// ⚠️ IMPORTANTE:
// No debe haber bloques 'android { ... }' aquí.
// La configuración del proyecto principal solo define plugins y repositorios.
