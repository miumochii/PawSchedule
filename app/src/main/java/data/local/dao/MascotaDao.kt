package com.martinvergara_diegoboggle.pawschedule.data.local.dao

import androidx.room.*
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.MascotaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MascotaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mascota: MascotaEntity): Long

    @Update
    suspend fun update(mascota: MascotaEntity)

    @Delete
    suspend fun delete(mascota: MascotaEntity)

    @Query("SELECT * FROM mascotas WHERE id = :id")
    suspend fun getMascotaById(id: Int): MascotaEntity?

    @Query("SELECT * FROM mascotas WHERE clienteId = :clienteId ORDER BY nombre ASC")
    fun getMascotasByCliente(clienteId: Int): Flow<List<MascotaEntity>>

    @Query("SELECT * FROM mascotas ORDER BY nombre ASC")
    fun getAllMascotas(): Flow<List<MascotaEntity>>

    @Query("SELECT * FROM mascotas WHERE especie = :especie")
    fun getMascotasByEspecie(especie: String): Flow<List<MascotaEntity>>

    @Query("SELECT * FROM mascotas WHERE nombre LIKE '%' || :query || '%' OR raza LIKE '%' || :query || '%'")
    fun searchMascotas(query: String): Flow<List<MascotaEntity>>

    @Query("SELECT COUNT(*) FROM mascotas WHERE clienteId = :clienteId")
    suspend fun countMascotasByCliente(clienteId: Int): Int
}