package com.martinvergara_diegoboggle.pawschedule.ui.cliente

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.CitaEntity
import com.martinvergara_diegoboggle.pawschedule.ui.common.TopBar
import com.martinvergara_diegoboggle.pawschedule.ui.viewmodel.CitaViewModel

@Composable
fun MisCitasScreen(
    clienteId: Int,
    viewModel: CitaViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val citas by viewModel.citasCliente.collectAsState()

    LaunchedEffect(clienteId) {
        viewModel.cargarCitasCliente(clienteId)
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Mis Citas",
                onBackClick = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Navegar a agendar */ }) {
                Icon(Icons.Default.Add, "Agendar nueva cita")
            }
        }
    ) { padding ->
        if (citas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.EventBusy,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No tienes citas programadas")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { /* TODO: Navegar a agendar */ }) {
                        Text("Agendar mi primera cita")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(citas) { cita ->
                    CitaCard(cita = cita)
                }
            }
        }
    }
}

@Composable
fun CitaCard(cita: CitaEntity) {
    // Función helper dentro del Composable
    fun formatTimestamp(timestamp: Long): String {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.format(Date(timestamp))
        } catch (e: Exception) {
            "Fecha inválida"
        }
    }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cita.motivo,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Fecha: ${formatTimestamp(cita.fecha)} - Hora: ${cita.horaInicio}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Estado: ${cita.estado}",
                    style = MaterialTheme.typography.bodySmall,
                    color = when (cita.estado) {
                        "Confirmada" -> MaterialTheme.colorScheme.primary
                        "Pendiente" -> MaterialTheme.colorScheme.tertiary
                        "Completada" -> MaterialTheme.colorScheme.secondary
                        "Cancelada" -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}