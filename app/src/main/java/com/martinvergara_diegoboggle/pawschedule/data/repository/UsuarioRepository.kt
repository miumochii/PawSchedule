package com.martinvergara_diegoboggle.pawschedule.data.local.repository

import com.martinvergara_diegoboggle.pawschedule.data.local.dao.UsuarioDao
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.UsuarioEntity
import kotlinx.coroutines.flow.Flow

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    fun obtenerVeterinarios(): Flow<List<UsuarioEntity>> {
        return usuarioDao.obtenerVeterinarios()
    }

    fun obtenerTodos(): Flow<List<UsuarioEntity>> {
        return usuarioDao.obtenerTodos()
    }

    suspend fun login(email: String, password: String): UsuarioEntity? {
        return usuarioDao.login(email, password)
    }

    suspend fun registrar(usuario: UsuarioEntity): Long {
        return usuarioDao.insertar(usuario)
    }

    suspend fun obtenerPorId(id: Int): UsuarioEntity? {
        return usuarioDao.obtenerPorId(id)
    }

    suspend fun obtenerPorEmail(email: String): UsuarioEntity? {
        return usuarioDao.obtenerPorEmail(email)
    }

    suspend fun actualizar(usuario: UsuarioEntity) {
        usuarioDao.actualizar(usuario)
    }

    suspend fun desactivar(id: Int) {
        usuarioDao.desactivar(id)
    }
}