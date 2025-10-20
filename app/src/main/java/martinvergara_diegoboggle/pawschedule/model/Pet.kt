package martinvergara_diegoboggle.pawschedule.model
import java.util.UUID
data class Pet(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val breed: String,
    val ownerId: String
)