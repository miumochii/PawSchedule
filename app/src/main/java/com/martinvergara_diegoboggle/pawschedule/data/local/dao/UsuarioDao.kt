package com.martinvergara_diegoboggle.pawschedule.data.local.dao

import androidx.room.*
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(usuario: UsuarioEntity): Long

    @Update
    suspend fun actualizar(usuario: UsuarioEntity)

    @Delete
    suspend fun eliminar(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun obtenerPorId(id: Int): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun obtenerPorEmail(email: String): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE rol = :rol AND activo = 1")
    fun obtenerPorRol(rol: String): Flow<List<UsuarioEntity>>

    @Query("SELECT * FROM usuarios WHERE rol = 'VETERINARIO' AND activo = 1")
    fun obtenerVeterinarios(): Flow<List<UsuarioEntity>>

    @Query("SELECT * FROM usuarios WHERE activo = 1")
    fun obtenerTodos(): Flow<List<UsuarioEntity>>

    @Query("UPDATE usuarios SET activo = 0 WHERE id = :id")
    suspend fun desactivar(id: Int)
}