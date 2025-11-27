package martinvergara_diegoboggle.pawschedule.ui.screens.add_pet

import android.util.Log
import androidx.lifecycle.ViewModel
import martinvergara_diegoboggle.pawschedule.data.repository.PetRepository
import martinvergara_diegoboggle.pawschedule.model.Pet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddPetViewModel(
    // ✅ CORRECCIÓN: Recibimos el userId directamente
    private val userId: Int = 0
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddPetUiState())
    val uiState: StateFlow<AddPetUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, nameError = null) }
    }

    fun onBreedChange(breed: String) {
        _uiState.update { it.copy(breed = breed) }
    }

    fun onPhotoSelected(uri: String) {
        _uiState.update { it.copy(photoUri = uri) }
    }

    fun onLocationFound(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun savePet(): Boolean {
        Log.d("DEBUG_PAW", "1. Entrando a la función savePet()")

        if (_uiState.value.name.isBlank()) {
            Log.d("DEBUG_PAW", "2. Error: El nombre estaba vacío")
            _uiState.update { it.copy(nameError = "El nombre es obligatorio") }
            return false
        }

        if (userId == 0) {
            Log.e("DEBUG_PAW", "ERROR: No hay usuario logueado o el ID es 0.")
            return false
        }

        Log.d("DEBUG_PAW", "3. Validaciones pasadas. Creando objeto Pet...")

        try {
            val newPet = Pet(
                id = 0,
                name = _uiState.value.name,
                breed = _uiState.value.breed.ifBlank { "Mestizo" },
                ownerId = userId, // ✅ CORRECCIÓN: Usamos el userId recibido
                imageUri = _uiState.value.photoUri ?: ""
            )

            Log.d("DEBUG_PAW", "4. Objeto creado: $newPet")
            Log.d("DEBUG_PAW", "5. Llamando al Repositorio...")

            PetRepository.addPet(newPet)

            Log.d("DEBUG_PAW", "6. ¡Enviado al repositorio con éxito!")
            return true

        } catch (e: Exception) {
            Log.e("DEBUG_PAW", "ERROR FATAL creando la mascota: ${e.message}")
            return false
        }
    }
}

data class AddPetUiState(
    val name: String = "",
    val breed: String = "",
    val nameError: String? = null,
    val photoUri: String? = null,
    val location: String = ""
)