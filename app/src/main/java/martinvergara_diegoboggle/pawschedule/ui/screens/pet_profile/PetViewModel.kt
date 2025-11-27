package martinvergara_diegoboggle.pawschedule.ui.screens.pet_profile

import androidx.lifecycle.ViewModel
import martinvergara_diegoboggle.pawschedule.data.repository.PetRepository

class PetViewModel : ViewModel() {
    val pets = PetRepository.pets

    // ✅ CORRECCIÓN: Ahora recibe userId
    fun deletePet(petId: Int, userId: Int) {
        PetRepository.deletePet(petId, userId)
    }
}