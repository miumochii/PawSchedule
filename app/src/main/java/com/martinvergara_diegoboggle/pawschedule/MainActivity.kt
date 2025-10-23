package com.martinvergara_diegoboggle.pawschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.martinvergara_diegoboggle.pawschedule.ui.PawScheduleNavigation

// ------------------- INICIO DE LA CORRECCIÓN -------------------
// 1. Añadimos el import para tu tema
import com.martinvergara_diegoboggle.pawschedule.ui.theme.PawScheduleTheme
// ------------------- FIN DE LA CORRECCIÓN -------------------

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Ahora el IDE sí encontrará tu tema
            PawScheduleTheme {
                Surface(
                    // 2. Corregimos la llamada al Modifier
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PawScheduleNavigation()
                }
            }
        }
    }
}