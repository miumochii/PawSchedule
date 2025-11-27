package martinvergara_diegoboggle.pawschedule.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import martinvergara_diegoboggle.pawschedule.model.Pet

object PetRepository {
    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets = _pets.asStateFlow()

    fun addPet(pet: Pet) {
        // CORRECCIÓN: Usamos Int (0) en lugar de Long (0L) para que coincida con tu modelo
        val currentId = pet.id

        val newPet = if (currentId == 0) {
            // Buscamos el ID más alto (Int) y le sumamos 1
            val maxId = _pets.value.maxOfOrNull { it.id } ?: 0
            val nextId = maxId + 1
            pet.copy(id = nextId)
        } else {
            pet
        }
        _pets.value += newPet
    }

    // CORRECCIÓN: Recibimos un Int para borrar
    fun deletePet(petId: Int) {
        _pets.value = _pets.value.filterNot { it.id == petId }
    }
}