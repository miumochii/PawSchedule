package martinvergara_diegoboggle.pawschedule.ui.screens.add_appointment

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import martinvergara_diegoboggle.pawschedule.ui.BounceButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddAppointmentScreen(
    navController: NavController,
    viewModel: AddAppointmentViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    // IMPORTANTE: Recibimos la lista de mascotas del ViewModel
    val availablePets by viewModel.availablePets.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    // Estado para controlar si el menú de mascotas está abierto o cerrado
    var expandedDropdown by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    // --- DIÁLOGO FECHA ---
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        formatter.timeZone = TimeZone.getTimeZone("UTC")
                        val formattedDate = formatter.format(Date(millis))
                        viewModel.onDateChange(formattedDate)
                    }
                    showDatePicker = false
                }) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancelar") }
            }
        ) { DatePicker(state = datePickerState) }
    }

    // --- DIÁLOGO HORA ---
    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val formattedTime = String.format("%02d:%02d", timePickerState.hour, timePickerState.minute)
                    viewModel.onTimeChange(formattedTime)
                    showTimePicker = false
                }) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Cancelar") }
            }
        ) { TimePicker(state = timePickerState) }
    }

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
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // --- AQUÍ ESTÁ LA MEJORA: SELECTOR DE MASCOTAS ---
            if (availablePets.isNotEmpty()) {
                ExposedDropdownMenuBox(
                    expanded = expandedDropdown,
                    onExpandedChange = { expandedDropdown = !expandedDropdown },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = uiState.petName,
                        onValueChange = {}, // ReadOnly porque seleccionamos de la lista
                        label = { Text("Seleccionar Mascota") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(), // Necesario para anclar el menú
                        isError = uiState.errors.petNameError != null
                    )
                    ExposedDropdownMenu(
                        expanded = expandedDropdown,
                        onDismissRequest = { expandedDropdown = false }
                    ) {
                        availablePets.forEach { pet ->
                            DropdownMenuItem(
                                text = { Text(text = pet.name) },
                                onClick = {
                                    viewModel.onPetNameChange(pet.name)
                                    expandedDropdown = false
                                }
                            )
                        }
                    }
                }
                // Error del selector
                AnimatedVisibility(visible = uiState.errors.petNameError != null) {
                    Text(
                        text = uiState.errors.petNameError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            } else {
                // Si no hay mascotas guardadas, mostramos el campo normal para escribir
                ValidatedTextField(
                    value = uiState.petName,
                    onValueChange = viewModel::onPetNameChange,
                    label = "Nombre de la mascota",
                    errorMessage = uiState.errors.petNameError
                )
            }

            ValidatedTextField(
                value = uiState.ownerName,
                onValueChange = viewModel::onOwnerNameChange,
                label = "Nombre del dueño/a",
                errorMessage = uiState.errors.ownerNameError
            )

            ReadOnlyClickableTextField(
                value = uiState.date,
                label = "Fecha",
                errorMessage = uiState.errors.dateError,
                onClick = { showDatePicker = true },
                trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) }
            )

            ReadOnlyClickableTextField(
                value = uiState.time,
                label = "Hora",
                errorMessage = uiState.errors.timeError,
                onClick = { showTimePicker = true },
                trailingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) }
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

            AnimatedVisibility(visible = uiState.errors.termsError != null) {
                Text(
                    text = uiState.errors.termsError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // TU BOUNCE BUTTON (Aquí está, funcionando perfecto)
            BounceButton(
                text = "Guardar Cita",
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
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// COMPONENTES AUXILIARES

@Composable
fun ValidatedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String?,
    singleLine: Boolean = true,
    maxLines: Int = 1
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = errorMessage != null,
            singleLine = singleLine,
            maxLines = maxLines,
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(visible = errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun ReadOnlyClickableTextField(
    value: String,
    label: String,
    errorMessage: String?,
    onClick: () -> Unit,
    trailingIcon: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                label = { Text(label) },
                isError = errorMessage != null,
                readOnly = true,
                trailingIcon = trailingIcon,
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledContainerColor = Color.Transparent
                )
            )
            Box(
                modifier = Modifier.matchParentSize().clickable { onClick() }
            )
        }

        AnimatedVisibility(visible = errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        text = content
    )
}