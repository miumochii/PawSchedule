package com.martinvergara_diegoboggle.pawschedule.ui.cliente

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.martinvergara_diegoboggle.pawschedule.ui.common.CustomButton
import com.martinvergara_diegoboggle.pawschedule.ui.common.TopBar
import com.martinvergara_diegoboggle.pawschedule.ui.viewmodel.CitaViewModel
import com.martinvergara_diegoboggle.pawschedule.ui.viewmodel.CitaViewModelFactory // Necesitarás un Factory

// --- INICIO DE LA CORRECCIÓN 2 (Error 'No parameter') ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarCitaScreen(
    clienteId: Int, // <-- AHORA SÍ ACEPTA EL clienteId
    onNavigateBack: () -> Unit = {},
    // Factory para el ViewModel (asumiendo que crearás CitaViewModelFactory)
    viewModel: CitaViewModel = viewModel(
        factory = CitaViewModelFactory(LocalContext.current.applicationContext as Application)
    )
) {
    // --- FIN DE LA CORRECCIÓN 2 ---

    // Estados del ViewModel
    val veterinarioId by viewModel.veterinarioId.collectAsState()
    val mascotaId by viewModel.mascotaId.collectAsState()
    val fecha by viewModel.fecha.collectAsState()
    val horaInicio by viewModel.horaInicio.collectAsState()
    val motivo by viewModel.motivo.collectAsState()
    val observaciones by viewModel.observaciones.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()
    val citaCreada by viewModel.citaCreada.collectAsState()

    val veterinarioError by viewModel.veterinarioError.collectAsState()
    val mascotaError by viewModel.mascotaError.collectAsState()
    val fechaError by viewModel.fechaError.collectAsState()
    val horaError by viewModel.horaError.collectAsState()
    val motivoError by viewModel.motivoError.collectAsState()

    val context = LocalContext.current

    // Efecto para navegar atrás cuando la cita se crea
    LaunchedEffect(citaCreada) {
        if (citaCreada) {
            Toast.makeText(context, "Cita agendada con éxito", Toast.LENGTH_SHORT).show()
            viewModel.resetearEstados()
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Agendar Cita",
                onBackClick = onNavigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Completa los datos para tu cita", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(24.dp))

            // --- Aquí irían tus campos (Dropdowns para Mascota y Veterinario) ---
            // Este es un formulario de ejemplo, tendrás que implementarlo:

            // Campo Motivo (Ejemplo)
            OutlinedTextField(
                value = motivo,
                onValueChange = { viewModel.onMotivoChange(it) },
                label = { Text("Motivo de la consulta") },
                leadingIcon = { Icon(Icons.Default.Info, null) },
                isError = motivoError != null,
                supportingText = motivoError?.let { { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            // --- Deberás añadir aquí: ---
            // 1. Un Dropdown para seleccionar Mascota (que llame a viewModel.onMascotaChange)
            // 2. Un Dropdown para seleccionar Veterinario (que llame a viewModel.onVeterinarioChange)
            // 3. Un DatePicker para la fecha (que llame a viewModel.onFechaChange)
            // 4. Un TimePicker para la hora (que llame a viewModel.onHoraInicioChange)

            Spacer(modifier = Modifier.height(32.dp))

            CustomButton(
                text = "Confirmar Cita",
                onClick = { viewModel.crearCita(clienteId) }, // Le pasamos el ID
                isLoading = isLoading
            )
        }
    }
}