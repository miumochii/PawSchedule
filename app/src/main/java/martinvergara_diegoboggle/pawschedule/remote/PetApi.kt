package martinvergara_diegoboggle.pawschedule.remote

import martinvergara_diegoboggle.pawschedule.model.Pet
import retrofit2.http.*

interface PetApi {

    @GET("pets")
    suspend fun getPets(): List<Pet>

    @POST("pets")
    suspend fun createPet(@Body pet: Pet): Pet

    @PUT("pets/{id}")
    suspend fun updatePet(
        @Path("id") id: Long,
        @Body pet: Pet
    ): Pet

    @DELETE("pets/{id}")
    suspend fun deletePet(@Path("id") id: Long)
}
