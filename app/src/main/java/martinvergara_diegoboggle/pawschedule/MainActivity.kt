package martinvergara_diegoboggle.pawschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import martinvergara_diegoboggle.pawschedule.navigation.AppNavigation
import martinvergara_diegoboggle.pawschedule.ui.theme.PawScheduleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ CORRECCIÓN: Ya NO cargamos datos aquí
        // Los datos se cargan automáticamente después del login/register
        // en AuthViewModel cuando el usuario se autentica

        setContent {
            PawScheduleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}