package martinvergara_diegoboggle.pawschedule.ui.screens.pet_profile

import androidx.lifecycle.ViewModel
import martinvergara_diegoboggle.pawschedule.data.repository.PetRepository

class PetViewModel : ViewModel() {
    val pets = PetRepository.pets

    // CORRECCIÓN: Cambiamos 'String' por 'Int' para que coincida con el Repositorio y el Modelo
    fun deletePet(petId: Int) {
        PetRepository.deletePet(petId)
    }
}