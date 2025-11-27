package martinvergara_diegoboggle.pawschedule.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import martinvergara_diegoboggle.pawschedule.data.network.RetrofitClient
import martinvergara_diegoboggle.pawschedule.model.Appointment

// El repositorio de citas debe ser un 'object' para ser accesible
object AppointmentRepository {

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments = _appointments.asStateFlow()

    // 1. CARGAR DATOS (READ): Ahora requiere el userId
    fun fetchAppointments(userId: Int) {
        if (userId == 0) return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Llama a getAppointments(userId)
                val remoteAppointments = RetrofitClient.pawApiService.getAppointments(userId)
                _appointments.value = remoteAppointments
                Log.d("API_SUCCESS", "Citas cargadas: ${remoteAppointments.size} para UserID: $userId")
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al cargar citas: ${e.message}")
            }
        }
    }

    // 2. GUARDAR DATOS (CREATE)
    fun addAppointment(appointment: Appointment) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                RetrofitClient.pawApiService.createAppointment(appointment)

                // Sincronizamos usando el ownerId de la cita
                fetchAppointments(appointment.ownerId)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al guardar cita: ${e.message}")
            }
        }
    }

    // 3. BORRAR DATOS (DELETE): Ahora requiere el userId
    fun deleteAppointment(appId: Int, userId: Int) {
        if (userId == 0) return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Llama a deleteAppointment(appId, userId)
                RetrofitClient.pawApiService.deleteAppointment(appId, userId)

                // Sincronizamos de nuevo usando el userId
                fetchAppointments(userId)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al borrar cita: ${e.message}")
            }
        }
    }
}