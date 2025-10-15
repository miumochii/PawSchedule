package data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val apellido: String,
    val email: String,
    val password: String,
    val telefono: String,
    val rol: String, // "CLIENTE" o "VETERINARIO"
    val especialidad: String? = null, // Solo para veterinarios
    val fechaRegistro: Long = System.currentTimeMillis(),
    val activo: Boolean = true
)




