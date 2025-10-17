package com.martinvergara_diegoboggle.pawschedule.data.local.repository

import com.martinvergara_diegoboggle.pawschedule.data.local.dao.CitaDao
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.CitaEntity
import kotlinx.coroutines.flow.Flow

class CitaRepository(private val citaDao: CitaDao) {

    fun obtenerPorCliente(idCliente: Int): Flow<List<CitaEntity>> {
        return citaDao.obtenerPorCliente(idCliente)
    }

    fun obtenerPorVeterinario(idVeterinario: Int): Flow<List<CitaEntity>> {
        return citaDao.obtenerPorVeterinario(idVeterinario)
    }

    fun obtenerPorMascota(idMascota: Int): Flow<List<CitaEntity>> {
        return citaDao.obtenerPorMascota(idMascota)
    }

    fun obtenerTodas(): Flow<List<CitaEntity>> {
        return citaDao.obtenerTodas()
    }

    suspend fun insertar(cita: CitaEntity): Long {
        return citaDao.insertar(cita)
    }

    suspend fun obtenerPorId(id: Int): CitaEntity? {
        return citaDao.obtenerPorId(id)
    }

    suspend fun actualizar(cita: CitaEntity) {
        citaDao.actualizar(cita)
    }

    suspend fun actualizarEstado(id: Int, nuevoEstado: String) {
        citaDao.actualizarEstado(id, nuevoEstado, System.currentTimeMillis())
    }

    suspend fun eliminar(cita: CitaEntity) {
        citaDao.eliminar(cita)
    }

    suspend fun verificarDisponibilidad(idVet: Int, inicio: Long, fin: Long): Boolean {
        val count = citaDao.contarCitasVeterinarioEnRango(idVet, inicio, fin)
        return count == 0
    }
}