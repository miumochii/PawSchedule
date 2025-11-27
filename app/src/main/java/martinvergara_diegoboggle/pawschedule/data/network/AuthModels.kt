package martinvergara_diegoboggle.pawschedule.data.network

import com.google.gson.annotations.SerializedName
// ⚠️ Nota: Borra o comenta la línea de kotlinx.serialization para evitar errores

// DATO A ENVIAR: Datos que enviamos para login/register
data class UserAuth(
    val email: String,
    val password: String
)

// DATO A RECIBIR: Datos que devuelve el servidor si el login/register es exitoso
data class LoginSuccess(
    // 1. Usamos @SerializedName para mapear 'user_id' del JSON de Python
    @SerializedName("user_id")
    val userId: Int,

    // 2. Este campo se mapea automáticamente porque el nombre coincide
    val email: String
)