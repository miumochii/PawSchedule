package martinvergara_diegoboggle.pawschedule.ui.screens.add_pet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(
    navController: NavController,
    viewModel: AddPetViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Mascota") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Nombre de la mascota") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.nameError != null,
                singleLine = true
            )
            AnimatedVisibility(visible = uiState.nameError != null) {
                Text(uiState.nameError ?: "", color = MaterialTheme.colorScheme.error)
            }

            OutlinedTextField(
                value = uiState.breed,
                onValueChange = viewModel::onBreedChange,
                label = { Text("Raza (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (viewModel.savePet()) {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Mascota")
            }
        }
    }
}