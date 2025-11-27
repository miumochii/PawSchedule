package martinvergara_diegoboggle.pawschedule.ui.screens.pet_profile
//LOGICA DE LA PETLIST
import androidx.lifecycle.ViewModel
import martinvergara_diegoboggle.pawschedule.data.repository.PetRepository

class PetViewModel : ViewModel() {
    val pets = PetRepository.pets

    fun deletePet(petId: Int, userId: Int) {
        PetRepository.deletePet(petId, userId)
    }
}