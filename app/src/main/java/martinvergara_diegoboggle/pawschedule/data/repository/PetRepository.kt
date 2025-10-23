package martinvergara_diegoboggle.pawschedule.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import martinvergara_diegoboggle.pawschedule.model.Pet

object PetRepository {
    private val _pets = MutableStateFlow<List<Pet>>(
        listOf(
            Pet(id = "1", name = "Rocky", breed = "Mestizo", ownerId = "1"),
            Pet(id = "2", name = "Luna", breed = "Siberiano", ownerId = "1")
        )
    )
    val pets = _pets.asStateFlow()

    fun addPet(pet: Pet) {
        _pets.value += pet
    }

    fun deletePet(petId: String) {
        _pets.value = _pets.value.filterNot { it.id == petId }
    }
}