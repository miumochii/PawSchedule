package com.martinvergara_diegoboggle.pawschedule.data.local.dao

import androidx.room.*
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.MascotaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MascotaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(mascota: MascotaEntity): Long

    @Update
    suspend fun actualizar(mascota: MascotaEntity)

    @Delete
    suspend fun eliminar(mascota: MascotaEntity)

    @Query("SELECT * FROM mascotas WHERE id = :id")
    suspend fun obtenerPorId(id: Int): MascotaEntity?

    @Query("SELECT * FROM mascotas WHERE idDueno = :idDueno ORDER BY nombre ASC")
    fun obtenerPorDueno(idDueno: Int): Flow<List<MascotaEntity>>

    @Query("SELECT * FROM mascotas ORDER BY nombre ASC")
    fun obtenerTodas(): Flow<List<MascotaEntity>>

    @Query("SELECT * FROM mascotas WHERE especie = :especie")
    fun obtenerPorEspecie(especie: String): Flow<List<MascotaEntity>>

    @Query("SELECT * FROM mascotas WHERE nombre LIKE '%' || :query || '%' OR raza LIKE '%' || :query || '%'")
    fun buscarMascotas(query: String): Flow<List<MascotaEntity>>

    @Query("SELECT COUNT(*) FROM mascotas WHERE idDueno = :idDueno")
    suspend fun contarMascotasPorDueno(idDueno: Int): Int

    // ------------------- INICIO DE LA CORRECCIÓN -------------------
    @Query("UPDATE mascotas SET activo = 0 WHERE id = :id")
    suspend fun desactivar(id: Int) // <-- Corregido: Se elimina el retorno ': Int'
    // ------------------- FIN DE LA CORRECCIÓN -------------------
}