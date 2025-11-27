package martinvergara_diegoboggle.pawschedule.data.network

import martinvergara_diegoboggle.pawschedule.model.Appointment
import martinvergara_diegoboggle.pawschedule.model.Pet
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PawApiService {

    // --- ENDPOINTS MASCOTAS ---

    @GET("pets/{owner_id}") // El frontend pide las mascotas filtrando por el ID en la URL
    suspend fun getPets(@Path("owner_id") ownerId: Int): List<Pet>

    @POST("pets")
    suspend fun createPet(@Body pet: Pet): Pet // Envía el objeto completo (con el ownerId en el body)

    @DELETE("pets/{pet_id}/{owner_id}") // Borrado seguro: requiere ID de mascota y ID de dueño
    suspend fun deletePet(@Path("pet_id") petId: Int, @Path("owner_id") ownerId: Int)

    // --- ENDPOINTS CITAS ---

    @GET("appointments/{owner_id}") // El frontend pide las citas filtrando por el ID en la URL
    suspend fun getAppointments(@Path("owner_id") ownerId: Int): List<Appointment>

    @POST("appointments")
    suspend fun createAppointment(@Body appointment: Appointment): Appointment

    @DELETE("appointments/{app_id}/{owner_id}") // Borrado seguro: requiere ID de cita y ID de dueño
    suspend fun deleteAppointment(@Path("app_id") appId: Int, @Path("owner_id") ownerId: Int)
}