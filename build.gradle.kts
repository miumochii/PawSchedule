// build.gradle.kts (Proyecto: PawSchedule)

plugins {
    // Declara los plugins que los módulos (como :app) podrán usar
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false // <--- AÑADE ESTA LÍNEA
}
    