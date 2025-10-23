// settings.gradle.kts (Archivo del Proyecto Raíz)

pluginManagement {
    repositories {google()
        mavenCentral()
        gradlePluginPortal() // Asegúrate de que este repositorio está aquí
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PawSchedule"
include(":app")
