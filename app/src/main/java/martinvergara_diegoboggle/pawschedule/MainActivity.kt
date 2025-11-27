package martinvergara_diegoboggle.pawschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import martinvergara_diegoboggle.pawschedule.data.repository.AppointmentRepository
import martinvergara_diegoboggle.pawschedule.data.repository.PetRepository
import martinvergara_diegoboggle.pawschedule.navigation.AppNavigation
import martinvergara_diegoboggle.pawschedule.ui.theme.PawScheduleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- SINCRONIZACIÓN INICIAL CON LA NUBE ---
        // Apenas abre la app, pedimos los datos al servidor para que las listas no estén vacías.

        // 1. Cargar Mascotas
        PetRepository.fetchPets()

        // 2. Cargar Citas (Si ya actualizaste AppointmentRepository a modo nube, esto funcionará)
        // Si te da error en esta línea, coméntala con // al principio hasta que actualices ese repo.
        AppointmentRepository.fetchAppointments()

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