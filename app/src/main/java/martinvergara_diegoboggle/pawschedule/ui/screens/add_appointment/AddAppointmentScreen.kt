package martinvergara_diegoboggle.pawschedule.ui.screens.add_appointment
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddAppointmentScreen(
    navController: NavController,
    viewModel: AddAppointmentViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Agendar Nueva Cita") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ValidatedTextField(
                value = uiState.petName,
                onValueChange = viewModel::onPetNameChange,
                label = "Nombre de la mascota",
                errorMessage = uiState.errors.petNameError
            )
            ValidatedTextField(
                value = uiState.ownerName,
                onValueChange = viewModel::onOwnerNameChange,
                label = "Nombre del dueño/a",
                errorMessage = uiState.errors.ownerNameError
            )
            ValidatedTextField(
                value = uiState.date,
                onValueChange = viewModel::onDateChange,
                label = "Fecha (DD/MM/AAAA)",
                errorMessage = uiState.errors.dateError
            )
            ValidatedTextField(
                value = uiState.time,
                onValueChange = viewModel::onTimeChange,
                label = "Hora (HH:MM)",
                errorMessage = uiState.errors.timeError
            )
            ValidatedTextField(
                value = uiState.symptoms,
                onValueChange = viewModel::onSymptomsChange,
                label = "Síntomas o motivo de la consulta",
                errorMessage = uiState.errors.symptomsError,
                singleLine = false,
                maxLines = 4
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.hasAgreedToTerms,
                    onCheckedChange = viewModel::onTermsChange
                )
                Text("Acepto los términos y condiciones")
            }
            uiState.errors.termsError?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    if (viewModel.validateAndSave()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Cita guardada correctamente")
                        }
                        navController.popBackStack()
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Por favor, corrige los errores.")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cita")
            }
        }
    }
}
@Composable
fun ValidatedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String?,
    singleLine: Boolean = true,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = errorMessage != null,
        supportingText = {
            if (errorMessage != null) {
                Text(errorMessage, color = MaterialTheme.colorScheme.error)
            }
        },
        singleLine = singleLine,
        maxLines = maxLines,
        modifier = Modifier.fillMaxWidth()
    )
}