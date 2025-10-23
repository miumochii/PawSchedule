package martinvergara_diegoboggle.pawschedule.ui.screens.pet_profile
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import martinvergara_diegoboggle.pawschedule.model.Pet
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PetListScreen(
    navController: NavController,
    viewModel: PetViewModel = viewModel()
) {
    val pets by viewModel.pets.collectAsState()
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
            FloatingActionButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Mascota")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(pets) { pet ->
                PetCard(pet = pet)
            }
        }
    }
}
@Composable
fun PetCard(pet: Pet) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column {
                Text(pet.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(pet.breed, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}