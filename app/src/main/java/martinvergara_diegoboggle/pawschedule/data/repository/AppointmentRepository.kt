package martinvergara_diegoboggle.pawschedule.data.repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import martinvergara_diegoboggle.pawschedule.model.Appointment
object AppointmentRepository {
    private val initialAppointments = listOf(
        Appointment(petName = "Rocky", ownerName = "Juan Pérez", date = "25/12/2023", time = "10:00", symptoms = "Vacunación anual"),
        Appointment(petName = "Luna", ownerName = "Ana Gómez", date = "28/12/2023", time = "15:30", symptoms = "Control general")
    )
    private val _appointments = MutableStateFlow<List<Appointment>>(initialAppointments)
    val appointments: StateFlow<List<Appointment>> = _appointments
    fun addAppointment(appointment: Appointment) {
        val currentList = _appointments.value.toMutableList()
        currentList.add(appointment)
        _appointments.value = currentList
    }
}