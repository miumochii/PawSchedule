package martinvergara_diegoboggle.pawschedule.data.network

// CONFIGURACIÓN DEL SISTEMA DE DE CONEXIÓN A BACKEND

import martinvergara_diegoboggle.pawschedule.model.Appointment
import martinvergara_diegoboggle.pawschedule.model.Pet
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PawApiService {

    @GET("pets/{owner_id}")
    suspend fun getPets(@Path("owner_id") ownerId: Int): List<Pet>

    @POST("pets")
    suspend fun createPet(@Body pet: Pet): Pet

    @DELETE("pets/{pet_id}/{owner_id}")
    suspend fun deletePet(
        @Path("pet_id") petId: Int,
        @Path("owner_id") ownerId: Int
    ): Response<Unit>  // ✅ Cambiado para manejar respuesta vacía

    // --- ENDPOINTS CITAS ---

    @GET("appointments/{owner_id}")
    suspend fun getAppointments(@Path("owner_id") ownerId: Int): List<Appointment>

    @POST("appointments")
    suspend fun createAppointment(@Body appointment: Appointment): Appointment

    @DELETE("appointments/{app_id}/{owner_id}")
    suspend fun deleteAppointment(
        @Path("app_id") appId: Int,
        @Path("owner_id") ownerId: Int
    ): Response<Unit>  // ✅ Cambiado para manejar respuesta vacía
}