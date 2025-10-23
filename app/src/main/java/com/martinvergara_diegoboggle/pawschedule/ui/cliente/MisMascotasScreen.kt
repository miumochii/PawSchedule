package com.martinvergara_diegoboggle.pawschedule.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.MascotaEntity
// ------------------- INICIO DE LA CORRECCIÓN 1 -------------------
import com.martinvergara_diegoboggle.pawschedule.ui.common.TopBar // <-- RUTA CORREGIDA
import com.martinvergara_diegoboggle.pawschedule.ui.viewmodel.MascotaViewModel
// Asegúrate de tener este import para la anotación:
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class) // <-- CORRECCIÓN 2: AÑADIR ANOTACIÓN
@Composable
// ------------------- FIN DE LA CORRECCIÓN -------------------
fun MisMascotasScreen(
    clienteId: Int,
    viewModel: MascotaViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToRegistrarMascota: () -> Unit = {}
) {
    val mascotas by viewModel.mascotas.collectAsState()

    LaunchedEffect(clienteId) {
        viewModel.cargarMascotasDelCliente(clienteId)
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Mis Mascotas",
                onBackClick = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToRegistrarMascota) {
                Icon(Icons.Default.Add, "Agregar mascota")
            }
        }
    ) { padding ->
        if (mascotas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Pets,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No tienes mascotas registradas")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onNavigateToRegistrarMascota) {
                        Text("Registrar mi primera mascota")
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
                items(mascotas) { mascota ->
                    MascotaCard(mascota)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) // <-- CORRECCIÓN 2: AÑADIR ANOTACIÓN
@Composable
fun MascotaCard(mascota: MascotaEntity) {
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
                imageVector = Icons.Default.Pets,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = mascota.nombre,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "${mascota.especie} - ${mascota.raza}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${mascota.edad} años · ${mascota.peso} kg · ${mascota.sexo}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}