// En tu archivo CitaEntity.kt

package com.martinvergara_diegoboggle.pawschedule.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "citas")
data class CitaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val clienteId: Int,
    val veterinarioId: Int,
    val mascotaId: Int,
    val fecha: Long,

    // --- INICIO DE LA CORRECCIÓN ---
    val horaInicio: String, // <-- AÑADE ESTA LÍNEA
    val horaFin: String,    // <-- Probablemente también necesites esta
    // --- FIN DE LA CORRECCIÓN ---

    val motivo: String,
    val estado: String,
    val observaciones: String
)