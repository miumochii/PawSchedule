package martinvergara_diegoboggle.pawschedule.model

import com.google.gson.annotations.SerializedName

data class Appointment(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("petName")
    val petName: String = "",

    @SerializedName("ownerName")
    val ownerName: String = "",

    @SerializedName("date")
    val date: String = "",

    @SerializedName("time")
    val time: String = "",

    @SerializedName("symptoms")
    val symptoms: String = "",

    @SerializedName("ownerId")
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


data class AppointmentCreateRequest( //ESTE CREA LA CITA, POR ESO NO TIENE ID, SE LA ASIGNA LA BASE DE DATOS.
    @SerializedName("petName")
    val petName: String,

    @SerializedName("ownerName")
    val ownerName: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("time")
    val time: String,

    @SerializedName("symptoms")
    val symptoms: String,

    @SerializedName("ownerId")
    val ownerId: Int
)