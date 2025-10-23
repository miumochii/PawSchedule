package com.martinvergara_diegoboggle.pawschedule.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// ------------------- INICIO DE LA CORRECCIÓN -------------------

// Asegúrate de tener este import para la anotación:
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class) // <-- ESTA ES LA LÍNEA QUE ARREGLA LA ADVERTENCIA
@Composable
fun RegisterScreen(
// ------------------- FIN DE LA CORRECCIÓN -------------------
    onNavigateToClienteRegister: () -> Unit = {},
    onNavigateToVeterinarioRegister: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¿Cómo deseas registrarte?",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selecciona el tipo de cuenta",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Botón Cliente
        Card(
            onClick = onNavigateToClienteRegister,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Soy Cliente",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Quiero agendar citas para mis mascotas",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Veterinario
        Card(
            onClick = onNavigateToVeterinarioRegister,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite, // Te sugiero cambiar este ícono por uno más de "salud", ej: Icons.Default.MedicalServices
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Soy Veterinario",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Quiero atender consultas veterinarias",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(onClick = onNavigateBack) {
            Text("Ya tengo cuenta - Iniciar sesión")
        }
    }
}