package com.martinvergara_diegoboggle.pawschedule.data.local.dao

import androidx.room.*
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.VeterinarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VeterinarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(veterinario: VeterinarioEntity): Long

    @Update
    suspend fun update(veterinario: VeterinarioEntity)

    @Delete
    suspend fun delete(veterinario: VeterinarioEntity)

    @Query("SELECT * FROM veterinarios WHERE id = :id AND activo = 1")
    suspend fun getVeterinarioById(id: Int): VeterinarioEntity?

    @Query("SELECT * FROM veterinarios WHERE email = :email AND password = :password AND activo = 1")
    suspend fun login(email: String, password: String): VeterinarioEntity?

    @Query("SELECT * FROM veterinarios WHERE email = :email AND activo = 1")
    suspend fun getVeterinarioByEmail(email: String): VeterinarioEntity?

    @Query("SELECT * FROM veterinarios WHERE activo = 1 ORDER BY nombre ASC")
    fun getAllVeterinarios(): Flow<List<VeterinarioEntity>>

    @Query("SELECT * FROM veterinarios WHERE especialidad = :especialidad AND activo = 1")
    fun getVeterinariosByEspecialidad(especialidad: String): Flow<List<VeterinarioEntity>>

    @Query("SELECT * FROM veterinarios WHERE (nombre LIKE '%' || :query || '%' OR apellido LIKE '%' || :query || '%') AND activo = 1")
    fun searchVeterinarios(query: String): Flow<List<VeterinarioEntity>>

    @Query("UPDATE veterinarios SET activo = 0 WHERE id = :id")
    suspend fun desactivar(id: Int)
}