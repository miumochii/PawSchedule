package martinvergara_diegoboggle.pawschedule.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import martinvergara_diegoboggle.pawschedule.data.network.RetrofitClient
import martinvergara_diegoboggle.pawschedule.model.Pet

object PetRepository {

    // La lista que ve la UI (se actualiza automáticamente al recibir datos de la nube)
    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets = _pets.asStateFlow()

    // 1. CARGAR DATOS (READ)
    // ⚠️ CORRECCIÓN 1: Ahora recibe el ID del usuario para filtrar
    fun fetchPets(userId: Int) {
        if (userId == 0) return // Seguridad: no cargamos si el ID es 0

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // ⚠️ CORRECCIÓN 2: Enviamos el ID en la solicitud (e.g. GET /pets/123)
                val remotePets = RetrofitClient.pawApiService.getPets(userId)

                // Actualizamos la lista local
                _pets.value = remotePets
                Log.d("API_SUCCESS", "Mascotas cargadas: ${remotePets.size} para UserID: $userId")
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al cargar mascotas: ${e.message}")
            }
        }
    }

    // 2. GUARDAR DATOS (CREATE)
    // El objeto 'pet' ya tiene el 'ownerId' asignado por el ViewModel
    fun addPet(pet: Pet) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Enviamos el perro a la nube.
                RetrofitClient.pawApiService.createPet(pet)

                // ⚠️ CORRECCIÓN 3: Sincronizamos usando el ID del dueño de la mascota
                fetchPets(pet.ownerId)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al guardar mascota: ${e.message}")
            }
        }
    }

    // 3. BORRAR DATOS (DELETE)
    // ⚠️ CORRECCIÓN 4: Ahora requiere el userId para asegurar que el usuario puede borrar
    fun deletePet(petId: Int, userId: Int) {
        if (userId == 0) return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // ⚠️ CORRECCIÓN 5: Le decimos a la nube que borre usando petId y userId
                // (e.g. DELETE /pets/45/123)
                RetrofitClient.pawApiService.deletePet(petId, userId)

                // ⚠️ CORRECCIÓN 6: Sincronizamos de nuevo usando el userId
                fetchPets(userId)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al borrar mascota: ${e.message}")
            }
        }
    }
}