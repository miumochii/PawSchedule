package martinvergara_diegoboggle.pawschedule.ui.screens.add_pet

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import martinvergara_diegoboggle.pawschedule.data.repository.PetRepository
import martinvergara_diegoboggle.pawschedule.model.Pet

class AddPetViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddPetUiState())
    val uiState = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, nameError = null) }
    }

    fun onBreedChange(breed: String) {
        _uiState.update { it.copy(breed = breed) }
    }

    // --- NUEVO: Función para recibir la foto de la galería ---
    fun onPhotoSelected(uri: String) {
        _uiState.update { it.copy(photoUri = uri) }
    }

    // --- NUEVO: Función para recibir la ubicación del GPS ---
    fun onLocationFound(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun savePet(): Boolean {
        if (uiState.value.name.isBlank()) {
            _uiState.update { it.copy(nameError = "El nombre es obligatorio") }
            return false
        }

        // Creamos la mascota
        // Nota: Por ahora guardamos nombre y raza.
        // Cuando configures la Base de Datos completa, podrás agregar photoUri y location al modelo Pet.
        val newPet = Pet(
            name = uiState.value.name,
            breed = uiState.value.breed.ifBlank { "Raza desconocida" },
            // Si tu modelo Pet requiere ownerId, mantenlo; si usas Room con autogenerate, esto cambiará después.
            // Por ahora lo dejo como lo tenías para que no falle:
            ownerId = "1"
        )
        PetRepository.addPet(newPet)
        return true
    }
}

// --- ACTUALIZADO: Agregamos photoUri y location al estado ---
data class AddPetUiState(
    val name: String = "",
    val breed: String = "",
    val nameError: String? = null,
    val photoUri: String? = null, // Para guardar la ruta de la imagen
    val location: String = ""     // Para guardar las coordenadas
)