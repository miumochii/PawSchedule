package com.martinvergara_diegoboggle.pawschedule.ui.cliente

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// ------------------- INICIO DE LA CORRECCIÓN 1 -------------------
// Imports corregidos (apuntan a 'ui.common' en lugar de 'ui.cliente')
import com.martinvergara_diegoboggle.pawschedule.ui.common.CustomButton
import com.martinvergara_diegoboggle.pawschedule.ui.common.CustomTextField
import com.martinvergara_diegoboggle.pawschedule.ui.common.TopBar
import com.martinvergara_diegoboggle.pawschedule.ui.viewmodel.MascotaViewModel
// Import para la anotación de API Experimental
import androidx.compose.material3.ExperimentalMaterial3Api
// ------------------- FIN DE LA CORRECCIÓN 1 -------------------

// ------------------- INICIO DE LA CORRECCIÓN 2 -------------------
@OptIn(ExperimentalMaterial3Api::class) // <-- Anotación añadida
@Composable
// ------------------- FIN DE LA CORRECCIÓN 2 -------------------
fun RegistrarMascotaScreen(
    clienteId: Int,
    viewModel: MascotaViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val nombre by viewModel.nombre.collectAsState()
    val especie by viewModel.especie.collectAsState()
    val raza by viewModel.raza.collectAsState()
    val edad by viewModel.edad.collectAsState()
    val peso by viewModel.peso.collectAsState()
    val sexo by viewModel.sexo.collectAsState()
    val color by viewModel.color.collectAsState()
    val observaciones by viewModel.observaciones.collectAsState()

    val nombreError by viewModel.nombreError.collectAsState()
    val especieError by viewModel.especieError.collectAsState()
    val razaError by viewModel.razaError.collectAsState()
    val edadError by viewModel.edadError.collectAsState()
    val pesoError by viewModel.pesoError.collectAsState()
    val sexoError by viewModel.sexoError.collectAsState()
    val colorError by viewModel.colorError.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()
    val registroExitoso by viewModel.registroExitoso.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(registroExitoso) {
        if (registroExitoso) {
            Toast.makeText(context, "¡Mascota registrada!", Toast.LENGTH_SHORT).show()
            onNavigateBack()
            viewModel.resetearEstados()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Registrar Mascota",
            onBackClick = onNavigateBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Pets,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(
                value = nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = "Nombre de la mascota",
                leadingIcon = Icons.Default.Pets,
                isError = nombreError != null,
                errorMessage = nombreError
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown Especie
            var expandedEspecie by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedEspecie,
                onExpandedChange = { expandedEspecie = it }
            ) {
                OutlinedTextField(
                    value = especie,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Especie") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEspecie) },
                    isError = especieError != null,
                    supportingText = especieError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedEspecie,
                    onDismissRequest = { expandedEspecie = false }
                ) {
                    listOf("Perro", "Gato", "Ave", "Conejo", "Otro").forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                viewModel.onEspecieChange(item)
                                expandedEspecie = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = raza,
                onValueChange = { viewModel.onRazaChange(it) },
                label = "Raza",
                isError = razaError != null,
                errorMessage = razaError
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomTextField(
                    value = edad,
                    onValueChange = { viewModel.onEdadChange(it) },
                    label = "Edad (años)",
                    keyboardType = KeyboardType.Number,
                    isError = edadError != null,
                    errorMessage = edadError,
                    modifier = Modifier.weight(1f)
                )

                CustomTextField(
                    value = peso,
                    onValueChange = { viewModel.onPesoChange(it) },
                    label = "Peso (kg)",
                    keyboardType = KeyboardType.Decimal,
                    isError = pesoError != null,
                    errorMessage = pesoError,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown Sexo
            var expandedSexo by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedSexo,
                onExpandedChange = { expandedSexo = it }
            ) {
                OutlinedTextField(
                    value = sexo,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Sexo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSexo) },
                    isError = sexoError != null,
                    supportingText = sexoError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedSexo,
                    onDismissRequest = { expandedSexo = false }
                ) {
                    listOf("Macho", "Hembra").forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                viewModel.onSexoChange(item)
                                expandedSexo = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = color,
                onValueChange = { viewModel.onColorChange(it) },
                label = "Color",
                isError = colorError != null,
                errorMessage = colorError
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = observaciones,
                onValueChange = { viewModel.onObservacionesChange(it) },
                label = "Observaciones (opcional)",
                singleLine = false,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomButton(
                text = "Registrar Mascota",
                onClick = { viewModel.registrarMascota(clienteId) },
                isLoading = isLoading
            )
        }
    }
}