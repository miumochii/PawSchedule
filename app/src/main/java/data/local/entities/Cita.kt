package data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "citas",
    foreignKeys = [
        ForeignKey(
            entity = MascotaEntity::class,
            parentColumns = ["id"],
            childColumns = ["idMascota"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["idVeterinario"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["idCliente"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CitaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idMascota: Int,
    val idVeterinario: Int,
    val idCliente: Int,
    val fecha: Long, // Timestamp de la fecha/hora de la cita
    val motivo: String,
    val estado: String, // "PENDIENTE", "CONFIRMADA", "EN_PROCESO", "FINALIZADA", "CANCELADA"
    val diagnostico: String? = null,
    val tratamiento: String? = null,
    val observaciones: String? = null,
    val costo: Double? = null,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)

