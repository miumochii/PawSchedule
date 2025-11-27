package martinvergara_diegoboggle.pawschedule.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import martinvergara_diegoboggle.pawschedule.model.Appointment
import martinvergara_diegoboggle.pawschedule.navigation.AppScreens // <--- CORREGIDO
import martinvergara_diegoboggle.pawschedule.ui.BounceButton

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val appointments by viewModel.appointments.collectAsState()
    var showDeleteDialog by remember { mutableStateOf<Appointment?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Citas Agendadas") },
                actions = {
                    // CORREGIDO: Sin comillas ni .kt
                    IconButton(onClick = { navController.navigate(AppScreens.PetListScreen.route) }) {
                        Icon(Icons.Filled.Pets, contentDescription = "Mis Mascotas", tint = MaterialTheme.colorScheme.onPrimary)
                    }

                    // CORREGIDO: Sin comillas ni .kt
                    IconButton(onClick = {
                        navController.navigate(AppScreens.LoginScreen.route) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Filled.Logout, contentDescription = "Cerrar Sesión", tint = MaterialTheme.colorScheme.onPrimary)
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
                // CORREGIDO: Sin comillas ni .kt
                onClick = { navController.navigate(AppScreens.AddAppointmentScreen.route) },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agendar nueva cita")
            }
        }
    ) { paddingValues ->

        // Animación 1: Estado Vacío (Si no hay citas)
        AnimatedVisibility(
            visible = appointments.isEmpty(),
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.padding(paddingValues)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.EventBusy,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No tienes citas agendadas.\n¡Toca el + para agregar una!",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }

        // Animación 2: Lista de Citas (Aparece suavemente)
        AnimatedVisibility(
            visible = appointments.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = appointments,
                    key = { it.id }
                ) { appointment ->
                    AppointmentCard(
                        appointment = appointment,
                        onDeleteClick = { showDeleteDialog = appointment }
                    )
                }
            }
        }

        // Diálogo de confirmación para eliminar
        showDeleteDialog?.let { appointmentToDelete ->
            AlertDialog(
                onDismissRequest = { showDeleteDialog = null },
                title = { Text("Confirmar Eliminación") },
                text = { Text("¿Estás seguro de que quieres eliminar la cita para ${appointmentToDelete.petName}?") },
                confirmButton = {
                    BounceButton(
                        onClick = {
                            viewModel.deleteAppointment(appointmentToDelete.id)
                            showDeleteDialog = null
                        },
                        text = "Eliminar",
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    )
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun AppointmentCard(appointment: Appointment, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = appointment.petName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Dueño: ${appointment.ownerName}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${appointment.date} - ${appointment.time}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Motivo: ${appointment.symptoms}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar cita", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}