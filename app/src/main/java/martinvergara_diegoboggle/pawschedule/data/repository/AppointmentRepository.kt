package martinvergara_diegoboggle.pawschedule.data.repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import martinvergara_diegoboggle.pawschedule.model.Appointment
object AppointmentRepository {
    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments
    fun addAppointment(appointment: Appointment) {
        val currentList = _appointments.value.toMutableList()
        currentList.add(appointment)
        _appointments.value = currentList
    }

    fun deleteAppointment(appointmentId: String) {
        _appointments.value = _appointments.value.filterNot { it.id == appointmentId }
    }
}
