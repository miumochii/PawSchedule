package martinvergara_diegoboggle.pawschedule.model
import java.util.UUID
data class Appointment(
    val id: String = UUID.randomUUID().toString(),
    val petName: String,
    val ownerName: String,
    val date: String,
    val time: String,
    val symptoms: String
)