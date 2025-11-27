package martinvergara_diegoboggle.pawschedule.data.repository
//AQUI SE GUARDA INFO TAMBIEN.
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import martinvergara_diegoboggle.pawschedule.data.network.RetrofitClient
import martinvergara_diegoboggle.pawschedule.model.Pet

object PetRepository {

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets = _pets.asStateFlow()

    //CARGAR DATOS
    fun fetchPets(userId: Int) {
        if (userId == 0) {
            Log.w("PET_REPO", "fetchPets: userId es 0, operación cancelada")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val remotePets = RetrofitClient.pawApiService.getPets(userId)
                _pets.value = remotePets
                Log.d("PET_REPO", "✅ Mascotas cargadas: ${remotePets.size} para UserID: $userId")
            } catch (e: Exception) {
                Log.e("PET_REPO", "❌ Error al cargar mascotas: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }

    //GUARDAR DATOS
    fun addPet(pet: Pet) {
        if (pet.ownerId == 0) {
            Log.e("PET_REPO", "❌ addPet: ownerId es 0, operación cancelada")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val createdPet = RetrofitClient.pawApiService.createPet(pet)
                Log.d("PET_REPO", "✅ Mascota creada en servidor: $createdPet")

                // Sincronizamos la lista
                fetchPets(pet.ownerId)
            } catch (e: Exception) {
                Log.e("PET_REPO", "❌ Error al guardar mascota: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }

    //BORRAR DATOS
    fun deletePet(petId: Int, userId: Int) {
        if (userId == 0) {
            Log.w("PET_REPO", "deletePet: userId es 0, operación cancelada")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.pawApiService.deletePet(petId, userId)

                if (response.isSuccessful) {
                    Log.d("PET_REPO", "✅ Mascota eliminada: petId=$petId")
                    // Sincronizamos la lista
                    fetchPets(userId)
                } else {
                    Log.e("PET_REPO", "❌ Error del servidor: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("PET_REPO", "❌ Error al borrar mascota: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }
}