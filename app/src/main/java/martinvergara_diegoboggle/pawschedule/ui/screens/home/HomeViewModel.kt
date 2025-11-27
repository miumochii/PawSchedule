package martinvergara_diegoboggle.pawschedule.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import martinvergara_diegoboggle.pawschedule.data.repository.AppointmentRepository
import martinvergara_diegoboggle.pawschedule.model.Appointment

class HomeViewModel : ViewModel() {
    private val repository = AppointmentRepository

    val appointments: StateFlow<List<Appointment>> = repository.appointments
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // ✅ CORRECCIÓN: Ahora recibe userId
    fun deleteAppointment(appointmentId: Int, userId: Int) {
        repository.deleteAppointment(appointmentId, userId)
    }
}