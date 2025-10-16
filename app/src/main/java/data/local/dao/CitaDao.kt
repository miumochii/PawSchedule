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

    @Query("SELECT * FROM citas WHERE idCliente = :idCliente ORDER BY fecha DESC")
    fun obtenerPorCliente(idCliente: Int): Flow<List<CitaEntity>>

    @Query("SELECT * FROM citas WHERE idVeterinario = :idVeterinario ORDER BY fecha DESC")
    fun obtenerPorVeterinario(idVeterinario: Int): Flow<List<CitaEntity>>

    @Query("SELECT * FROM citas WHERE idMascota = :idMascota ORDER BY fecha DESC")
    fun obtenerPorMascota(idMascota: Int): Flow<List<CitaEntity>>

    @Query("SELECT * FROM citas WHERE estado = :estado ORDER BY fecha ASC")
    fun obtenerPorEstado(estado: String): Flow<List<CitaEntity>>

    @Query("SELECT * FROM citas WHERE fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY fecha ASC")
    fun obtenerPorRangoFecha(fechaInicio: Long, fechaFin: Long): Flow<List<CitaEntity>>

    @Query("SELECT * FROM citas ORDER BY fecha DESC")
    fun obtenerTodas(): Flow<List<CitaEntity>>

    @Query("UPDATE citas SET estado = :nuevoEstado, fechaActualizacion = :timestamp WHERE id = :id")
    suspend fun actualizarEstado(id: Int, nuevoEstado: String, timestamp: Long)

    @Query("SELECT COUNT(*) FROM citas WHERE idVeterinario = :idVet AND fecha BETWEEN :inicio AND :fin")
    suspend fun contarCitasVeterinarioEnRango(idVet: Int, inicio: Long, fin: Long): Int
}