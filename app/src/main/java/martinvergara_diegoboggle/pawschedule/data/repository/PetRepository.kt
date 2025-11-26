package martinvergara_diegoboggle.pawschedule.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import martinvergara_diegoboggle.pawschedule.model.Pet
import martinvergara_diegoboggle.pawschedule.remote.PetApi
import martinvergara_diegoboggle.pawschedule.remote.RetrofitClient

object PetRepository {

    private val petApi: PetApi =
        RetrofitClient.instance.create(PetApi::class.java)

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets = _pets.asStateFlow()

    // 🔵 Obtener lista desde el backend
    suspend fun loadPets() {
        withContext(Dispatchers.IO) {
            try {
                val response = petApi.getPets()
                _pets.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 🟢 Crear mascota en el backend
    suspend fun addPet(pet: Pet) {
        withContext(Dispatchers.IO) {
            try {
                val newPet = petApi.createPet(pet)
                _pets.value = _pets.value + newPet
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 🔴 Eliminar mascota del backend
    suspend fun deletePet(petId: Long) {
        withContext(Dispatchers.IO) {
            try {
                petApi.deletePet(petId)
                _pets.value = _pets.value.filterNot { it.id == petId }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
