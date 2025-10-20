package martinvergara_diegoboggle.pawschedule.model
import java.util.UUID
data class User(
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    val passwordHash: String
)