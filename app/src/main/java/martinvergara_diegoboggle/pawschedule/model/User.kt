package martinvergara_diegoboggle.pawschedule.model
import java.util.UUID

// ESTE ES EL ID DEL USUARIO, SE GENERA EN EL TELÉFONO Y SE PASA A LA BASE DE DATOS PARA MAYOR SEGURIDAD. COMO LA SINCRONIZACIÓN DE MOZILLA
// UUID SON LOS MAS SEGUROS DEL MERCADO Y JAMAS HABRAN DOS IGUALES.

data class User(
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    val passwordHash: String
)