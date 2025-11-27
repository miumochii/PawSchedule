package martinvergara_diegoboggle.pawschedule.model

import com.google.gson.annotations.SerializedName

data class Pet(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("breed")
    val breed: String = "",

    @SerializedName("ownerId")
    val ownerId: Int = 0,

    @SerializedName("imageUri")
    val imageUri: String = ""
) {
    fun toCreateRequest() = PetCreateRequest(
        name = name,
        breed = breed,
        ownerId = ownerId,
        imageUri = imageUri
    )
}

data class PetCreateRequest( //ESTE CREA LA MASCOTA, POR ESO NO TIENE ID, SE LA ASIGNA LA BASE DE DATOS.
    @SerializedName("name")
    val name: String,

    @SerializedName("breed")
    val breed: String,

    @SerializedName("ownerId")
    val ownerId: Int,

    @SerializedName("imageUri")
    val imageUri: String = ""
)