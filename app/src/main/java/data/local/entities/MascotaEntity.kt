package com.martinvergara_diegoboggle.pawschedule.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "mascotas",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["idDueno"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MascotaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idDueno: Int,
    val nombre: String,
    val especie: String, // "Perro", "Gato", "Ave", "Otro"
    val raza: String,
    val edad: Int, // En años
    val peso: Double, // En kg
    val color: String,
    val sexo: String, // "Macho" o "Hembra"
    val fotoUri: String? = null,
    val observaciones: String? = null,
    val fechaRegistro: Long = System.currentTimeMillis(),
    val activo: Boolean = true
)