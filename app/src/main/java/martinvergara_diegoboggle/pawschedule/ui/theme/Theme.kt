package martinvergara_diegoboggle.pawschedule.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Definimos el esquema de colores Claros (Light Mode)
private val LightColorScheme = lightColorScheme(
    primary = PawPrimary,
    onPrimary = PawOnPrimary,
    primaryContainer = PawPrimaryContainer,
    secondary = PawSecondary,
    onSecondary = PawOnSecondary,
    tertiary = PawTertiary,
    background = PawBackground,
    surface = PawSurface,
    onBackground = PawOnSurface,
    onSurface = PawOnSurface,
    onSurfaceVariant = PawOnSurfaceVariant,
    outline = PawOutline,
    error = PawSecondary // Usamos el naranja para errores en vez de rojo sangre
)

// (Opcional) Esquema Oscuro básico
private val DarkColorScheme = darkColorScheme(
    primary = PawPrimary,
    secondary = PawSecondary,
    tertiary = PawTertiary
)

@Composable
fun PawScheduleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // IMPORTANTE: Ponemos esto en false para que respete TUS colores y no los de Android
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Pintamos la barra de estado del color de fondo para que se vea "Full Screen" limpio
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Asegúrate de tener Typography.kt (o usa el default)
        // shapes = Shapes, // Si tienes un archivo Shapes.kt, úsalo aquí
        content = content
    )
}