package martinvergara_diegoboggle.pawschedule.model

import com.google.gson.annotations.SerializedName

data class Pet(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("breed")
    val breed: String = "",

    // ✅ CORRECCIÓN CRÍTICA: Tu backend usa camelCase, no snake_case
    @SerializedName("ownerId")  // ⚠️ Cambio: ownerId en lugar de owner_id
    val ownerId: Int = 0,

    @SerializedName("imageUri")  // ⚠️ Cambio: imageUri en lugar de image_uri
    val imageUri: String = ""
) {
    fun toCreateRequest() = PetCreateRequest(
        name = name,
        breed = breed,
        ownerId = ownerId,
        imageUri = imageUri
    )
}

// DTO para crear mascotas (sin ID)
data class PetCreateRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("breed")
    val breed: String,

    @SerializedName("ownerId")  // ✅ camelCase
    val ownerId: Int,

    @SerializedName("imageUri")  // ✅ camelCase
    val imageUri: String = ""
)