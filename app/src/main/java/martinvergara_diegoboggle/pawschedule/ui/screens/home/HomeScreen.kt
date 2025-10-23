package martinvergara_diegoboggle.pawschedule.ui.screens.home
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import martinvergara_diegoboggle.pawschedule.model.Appointment
import martinvergara_diegoboggle.pawschedule.navigation.AppScreens
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val appointments by viewModel.appointments.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Citas Agendadas") },
                actions = {
                    IconButton(onClick = { navController.navigate(AppScreens.PetListScreen.route) }) {
                        Icon(Icons.Filled.Pets, contentDescription = "Mis Mascotas", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(AppScreens.AddAppointmentScreen.route) }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agendar nueva cita")
            }
        }
    ) { paddingValues ->
        if (appointments.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay citas agendadas. ¡Agenda una nueva!")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(appointments) { appointment ->
                    AppointmentCard(appointment)
                }
            }
        }
    }
}
@Composable
fun AppointmentCard(appointment: Appointment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Mascota: ${appointment.petName}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Dueño/a: ${appointment.ownerName}")
            Text("Fecha: ${appointment.date} a las ${appointment.time}")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Motivo: ${appointment.symptoms}", style = MaterialTheme.typography.bodySmall)
        }
    }
}