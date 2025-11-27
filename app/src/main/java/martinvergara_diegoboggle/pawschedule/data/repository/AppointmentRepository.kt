package martinvergara_diegoboggle.pawschedule.data.repository
//AQUI SE GUARDA LA INFO.
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import martinvergara_diegoboggle.pawschedule.data.network.RetrofitClient
import martinvergara_diegoboggle.pawschedule.model.Appointment

object AppointmentRepository {

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments = _appointments.asStateFlow()

    //CARGAR DATOS
    fun fetchAppointments(userId: Int) {
        if (userId == 0) {
            Log.w("APPOINTMENT_REPO", "fetchAppointments: userId es 0, operación cancelada")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val remoteAppointments = RetrofitClient.pawApiService.getAppointments(userId)
                _appointments.value = remoteAppointments
                Log.d("APPOINTMENT_REPO", "✅ Citas cargadas: ${remoteAppointments.size} para UserID: $userId")
            } catch (e: Exception) {
                Log.e("APPOINTMENT_REPO", "❌ Error al cargar citas: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }

    //GUARDAR DATOS
    fun addAppointment(appointment: Appointment) {
        if (appointment.ownerId == 0) {
            Log.e("APPOINTMENT_REPO", "❌ addAppointment: ownerId es 0, operación cancelada")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val createdAppointment = RetrofitClient.pawApiService.createAppointment(appointment)
                Log.d("APPOINTMENT_REPO", "✅ Cita creada en servidor: $createdAppointment")
                fetchAppointments(appointment.ownerId)
            } catch (e: Exception) {
                Log.e("APPOINTMENT_REPO", "❌ Error al guardar cita: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }

    //BORRAR DATOS
    fun deleteAppointment(appId: Int, userId: Int) {
        if (userId == 0) {
            Log.w("APPOINTMENT_REPO", "deleteAppointment: userId es 0, operación cancelada")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.pawApiService.deleteAppointment(appId, userId)

                if (response.isSuccessful) {
                    Log.d("APPOINTMENT_REPO", "✅ Cita eliminada: appId=$appId")
                    fetchAppointments(userId)
                } else {
                    Log.e("APPOINTMENT_REPO", "❌ Error del servidor: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("APPOINTMENT_REPO", "❌ Error al borrar cita: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }
}