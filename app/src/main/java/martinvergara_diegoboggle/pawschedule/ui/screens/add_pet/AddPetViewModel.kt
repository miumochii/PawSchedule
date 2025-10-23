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

    fun savePet(): Boolean {
        if (uiState.value.name.isBlank()) {
            _uiState.update { it.copy(nameError = "El nombre es obligatorio") }
            return false
        }
        val newPet = Pet(
            name = uiState.value.name,
            breed = uiState.value.breed.ifBlank { "Raza desconocida" },
            ownerId = "1" // Simulado
        )
        PetRepository.addPet(newPet)
        return true
    }
}

data class AddPetUiState(
    val name: String = "",
    val breed: String = "",
    val nameError: String? = null
)