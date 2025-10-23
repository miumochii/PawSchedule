package martinvergara_diegoboggle.pawschedule.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import martinvergara_diegoboggle.pawschedule.model.Pet

object PetRepository {
    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets = _pets.asStateFlow()

    fun addPet(pet: Pet) {
        _pets.value += pet
    }

    fun deletePet(petId: String) {
        _pets.value = _pets.value.filterNot { it.id == petId }
    }
}