package com.martinvergara_diegoboggle.pawschedule.data.local.dao

import androidx.room.*
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.CitaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CitaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(cita: CitaEntity): Long

    @Update
    suspend fun actualizar(cita: CitaEntity)

    @Delete
    suspend fun eliminar(cita: CitaEntity)

    @Query("SELECT * FROM citas WHERE id = :id")
    suspend fun obtenerPorId(id: Int): CitaEntity?

    // --- CORRECCIÓN 1: 'idCliente' -> 'clienteId' ---
    @Query("SELECT * FROM citas WHERE clienteId = :idCliente ORDER BY fecha DESC")
    fun obtenerPorCliente(idCliente: Int): Flow<List<CitaEntity>>

    // --- CORRECCIÓN 2: 'idVeterinario' -> 'veterinarioId' ---
    @Query("SELECT * FROM citas WHERE veterinarioId = :idVeterinario ORDER BY fecha DESC")
    fun obtenerPorVeterinario(idVeterinario: Int): Flow<List<CitaEntity>>

    // --- CORRECCIÓN 3: 'idMascota' -> 'mascotaId' ---
    @Query("SELECT * FROM citas WHERE mascotaId = :idMascota ORDER BY fecha DESC")
    fun obtenerPorMascota(idMascota: Int): Flow<List<CitaEntity>>

    @Query("SELECT * FROM citas WHERE estado = :estado ORDER BY fecha ASC")
    fun obtenerPorEstado(estado: String): Flow<List<CitaEntity>>

    @Query("SELECT * FROM citas WHERE fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY fecha ASC")
    fun obtenerPorRangoFecha(fechaInicio: Long, fechaFin: Long): Flow<List<CitaEntity>>

    @Query("SELECT * FROM citas ORDER BY fecha DESC")
    fun obtenerTodas(): Flow<List<CitaEntity>>

    // --- CORRECCIÓN 4: Eliminada 'fechaActualizacion' porque no existe en CitaEntity ---
    @Query("UPDATE citas SET estado = :nuevoEstado WHERE id = :id")
    suspend fun actualizarEstado(id: Int, nuevoEstado: String) // Se elimina el parámetro 'timestamp'

    // --- CORRECCIÓN 5: 'idVeterinario' -> 'veterinarioId' ---
    @Query("SELECT COUNT(*) FROM citas WHERE veterinarioId = :idVet AND fecha BETWEEN :inicio AND :fin")
    suspend fun contarCitasVeterinarioEnRango(idVet: Int, inicio: Long, fin: Long): Int
}