package martinvergara_diegoboggle.pawschedule.model

// El modelo de datos, limpio sin anotaciones de Room
data class Pet(
    // ID: Debe ser Int para autoincremento en el backend y para Retrofit
    val id: Int = 0,

    val name: String,
    val breed: String,

    // CORRECCIÓN CLAVE: El ID del dueño debe ser Int, no String
    val ownerId: Int = 0,

    // Valor por defecto para la URI de la foto
    val imageUri: String = ""
)