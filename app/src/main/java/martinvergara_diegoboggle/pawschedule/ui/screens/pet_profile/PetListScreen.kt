package martinvergara_diegoboggle.pawschedule.ui.screens.pet_profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import martinvergara_diegoboggle.pawschedule.model.Pet
import martinvergara_diegoboggle.pawschedule.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetListScreen(
    navController: NavController,
    viewModel: PetViewModel = viewModel()
) {
    val pets by viewModel.pets.collectAsState()
    var showDeleteDialog by remember { mutableStateOf<Pet?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Mascotas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(AppScreens.AddPetScreen.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Mascota")
            }
        }
    ) { paddingValues ->
        if (pets.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("No tienes mascotas registradas.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = pets,
                    key = { it.id }
                ) { pet ->
                    PetCard(
                        pet = pet,
                        onDeleteClick = { showDeleteDialog = pet }
                    )
                }
            }
        }

        showDeleteDialog?.let { petToDelete ->
            AlertDialog(
                onDismissRequest = { showDeleteDialog = null },
                title = { Text("Confirmar Eliminación") },
                text = { Text("¿Estás seguro de que quieres eliminar a ${petToDelete.name}?") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deletePet(petToDelete.id)
                            showDeleteDialog = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDeleteDialog = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun PetCard(pet: Pet, onDeleteClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(pet.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(pet.breed, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar mascota", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}