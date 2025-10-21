package martinvergara_diegoboggle.pawschedule.ui.screens.add_appointment
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import martinvergara_diegoboggle.pawschedule.data.repository.AppointmentRepository
import martinvergara_diegoboggle.pawschedule.model.Appointment
class AddAppointmentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppointmentFormUiState())
    val uiState: StateFlow<AppointmentFormUiState> = _uiState.asStateFlow()
    fun onPetNameChange(name: String) {
        _uiState.update { it.copy(petName = name, errors = it.errors.copy(petNameError = null)) }
    }
    fun onOwnerNameChange(name: String) {
        _uiState.update { it.copy(ownerName = name, errors = it.errors.copy(ownerNameError = null)) }
    }
    fun onDateChange(date: String) {
        _uiState.update { it.copy(date = date, errors = it.errors.copy(dateError = null)) }
    }
    fun onTimeChange(time: String) {
        _uiState.update { it.copy(time = time, errors = it.errors.copy(timeError = null)) }
    }
    fun onSymptomsChange(symptoms: String) {
        _uiState.update { it.copy(symptoms = symptoms, errors = it.errors.copy(symptomsError = null)) }
    }
    fun onTermsChange(agreed: Boolean) {
        _uiState.update { it.copy(hasAgreedToTerms = agreed, errors = it.errors.copy(termsError = null)) }
    }
    fun validateAndSave(): Boolean {
        val state = _uiState.value
        var hasErrors = false
        var currentErrors = AppointmentFormErrors()
        if (state.petName.isBlank()) {
            currentErrors = currentErrors.copy(petNameError = "El nombre de la mascota es obligatorio")
            hasErrors = true
        }
        if (state.ownerName.isBlank()) {
            currentErrors = currentErrors.copy(ownerNameError = "El nombre del dueño es obligatorio")
            hasErrors = true
        }
        if (state.date.isBlank()) {
            currentErrors = currentErrors.copy(dateError = "La fecha es obligatoria (DD/MM/AAAA)")
            hasErrors = true
        }
        if (state.time.isBlank()) {
            currentErrors = currentErrors.copy(timeError = "La hora es obligatoria (HH:MM)")
            hasErrors = true
        }
        if (state.symptoms.length < 10) {
            currentErrors = currentErrors.copy(symptomsError = "Describa los síntomas (mín. 10 caracteres)")
            hasErrors = true
        }
        if (!state.hasAgreedToTerms) {
            currentErrors = currentErrors.copy(termsError = "Debe aceptar los términos")
            hasErrors = true
        }
        _uiState.update { it.copy(errors = currentErrors) }
        if (!hasErrors) {
            val newAppointment = Appointment(
                petName = state.petName,
                ownerName = state.ownerName,
                date = state.date,
                time = state.time,
                symptoms = state.symptoms
            )
            AppointmentRepository.addAppointment(newAppointment)
            return true
        }
        return false
    }
}