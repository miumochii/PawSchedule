package martinvergara_diegoboggle.pawschedule.ui.screens.add_pet

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.gms.location.LocationServices
import martinvergara_diegoboggle.pawschedule.ui.BounceButton
import martinvergara_diegoboggle.pawschedule.ui.screens.auth.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(
    navController: NavController,
    authViewModel: AuthViewModel, // ✅ AGREGADO
    viewModel: AddPetViewModel = viewModel(
        factory = AddPetViewModelFactory(authViewModel.getCurrentUserId())
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            uri?.let { viewModel.onPhotoSelected(it.toString()) }
        }
    )

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                try {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            val locStr = "${location.latitude}, ${location.longitude}"
                            viewModel.onLocationFound(locStr)
                            Toast.makeText(context, "Ubicación actualizada", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "No se pudo obtener ubicación", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: SecurityException) {
                    Log.e("LOCATION_ERROR", "Error: ${e.message}")
                }
            } else {
                Toast.makeText(context, "Se requiere permiso de ubicación", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Mascota") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .clickable {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (uiState.photoUri != null) {
                    AsyncImage(
                        model = uiState.photoUri,
                        contentDescription = "Foto Mascota",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Agregar Foto",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            Text("Toca para agregar foto", style = MaterialTheme.typography.bodySmall)

            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = viewModel::onNameChange,
                    label = { Text("Nombre de la mascota") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.nameError != null,
                    singleLine = true
                )
                AnimatedVisibility(visible = uiState.nameError != null) {
                    Text(
                        text = uiState.nameError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }

            OutlinedTextField(
                value = uiState.breed,
                onValueChange = viewModel::onBreedChange,
                label = { Text("Raza (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.location,
                onValueChange = {},
                label = { Text("Ubicación (Hogar)") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                                if (location != null) {
                                    val locStr = "${location.latitude}, ${location.longitude}"
                                    viewModel.onLocationFound(locStr)
                                }
                            }
                        } else {
                            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }) {
                        Icon(
                            Icons.Default.MyLocation,
                            contentDescription = "Usar GPS",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
            Text(
                text = "Toca el icono de mira para guardar ubicación",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.weight(1f))

            BounceButton(
                text = "Guardar Mascota",
                onClick = {
                    Log.d("DEBUG_PAW", "--- CLICK EN BOTÓN GUARDAR ---")
                    if (viewModel.savePet()) {
                        Log.d("DEBUG_PAW", "ViewModel retornó TRUE -> Cerrando pantalla")
                        navController.popBackStack()
                    } else {
                        Log.d("DEBUG_PAW", "ViewModel retornó FALSE -> Hubo un error de validación")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ✅ AGREGADO: Factory para pasar userId
class AddPetViewModelFactory(private val userId: Int) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddPetViewModel(userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}