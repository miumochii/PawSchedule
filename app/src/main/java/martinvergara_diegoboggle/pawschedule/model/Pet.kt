package martinvergara_diegoboggle.pawschedule.model

data class Pet(
    val id: Long? = null,       // lo genera el backend
    val name: String,
    val breed: String,
    val ownerId: Long,
    val imageUri: String
)
