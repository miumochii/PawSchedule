package martinvergara_diegoboggle.pawschedule.model

data class Appointment(
    // CORRECCIÓN: ID numérico (Int) inicializado en 0
    val id: Int = 0,

    val petName: String,
    val ownerName: String,
    val date: String,
    val time: String,
    val symptoms: String
)