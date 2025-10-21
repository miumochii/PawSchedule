package martinvergara_diegoboggle.pawschedule.ui.screens.pet_profile
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import martinvergara_diegoboggle.pawschedule.model.Pet
class PetViewModel : ViewModel() {
    private val _pets = MutableStateFlow<List<Pet>>(
        listOf(
            Pet(name = "Rocky", breed = "Mestizo", ownerId = "1"),
            Pet(name = "Luna", breed = "Siberiano", ownerId = "1")
        )
    )
    val pets = _pets.asStateFlow()
}