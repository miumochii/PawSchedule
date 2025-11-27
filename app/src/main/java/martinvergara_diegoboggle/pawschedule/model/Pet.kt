package martinvergara_diegoboggle.pawschedule.model

// Quitamos los imports de Room por ahora
// import androidx.room.Entity
// import androidx.room.PrimaryKey

// Quitamos la etiqueta @Entity
data class Pet(
    // Quitamos @PrimaryKey pero mantenemos el ID como Int
    val id: Int = 0,

    val name: String,
    val breed: String,

    val ownerId: String = "",
    val imageUri: String = ""
)