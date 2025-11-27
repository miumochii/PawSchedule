package martinvergara_diegoboggle.pawschedule.model

import com.google.gson.annotations.SerializedName

data class Appointment(
    @SerializedName("id")
    val id: Int = 0,

    // ✅ CORRECCIÓN CRÍTICA: Tu backend usa camelCase, no snake_case
    @SerializedName("petName")  // ⚠️ Cambio: petName en lugar de pet_name
    val petName: String = "",

    @SerializedName("ownerName")  // ⚠️ Cambio: ownerName en lugar de owner_name
    val ownerName: String = "",

    @SerializedName("date")
    val date: String = "",

    @SerializedName("time")
    val time: String = "",

    @SerializedName("symptoms")
    val symptoms: String = "",

    @SerializedName("ownerId")  // ⚠️ Cambio: ownerId en lugar de owner_id
    val ownerId: Int = 0
) {
    fun toCreateRequest() = AppointmentCreateRequest(
        petName = petName,
        ownerName = ownerName,
        date = date,
        time = time,
        symptoms = symptoms,
        ownerId = ownerId
    )
}

// DTO para crear citas (sin ID)
data class AppointmentCreateRequest(
    @SerializedName("petName")  // ✅ camelCase
    val petName: String,

    @SerializedName("ownerName")  // ✅ camelCase
    val ownerName: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("time")
    val time: String,

    @SerializedName("symptoms")
    val symptoms: String,

    @SerializedName("ownerId")  // ✅ camelCase
    val ownerId: Int
)