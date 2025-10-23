package com.martinvergara_diegoboggle.pawschedule.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "veterinarios")
data class VeterinarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val apellido: String,
    val email: String,
    val telefono: String,
    val especialidad: String,
    val numeroLicencia: String,
    val password: String,
    val horaInicio: String = "09:00",
    val horaFin: String = "18:00",
    val fechaRegistro: Long = System.currentTimeMillis(),
    val activo: Boolean = true
)