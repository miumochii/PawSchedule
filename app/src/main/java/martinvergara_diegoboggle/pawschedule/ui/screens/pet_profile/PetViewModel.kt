package martinvergara_diegoboggle.pawschedule.ui.screens.pet_profile

import androidx.lifecycle.ViewModel
import martinvergara_diegoboggle.pawschedule.data.repository.PetRepository // <-- Import clave

class PetViewModel : ViewModel() {
    val pets = PetRepository.pets

    fun deletePet(petId: String) {
        PetRepository.deletePet(petId)
    }
}