package com.martinvergara_diegoboggle.pawschedule.data.local.repository // Asegúrate que esta sea la ruta correcta

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

    // ------------------- INICIO DE LA CORRECCIÓN -------------------
    suspend fun actualizarEstado(id: Int, nuevoEstado: String) {
        // Simplemente borramos el System.currentTimeMillis() que ya no se necesita
        citaDao.actualizarEstado(id, nuevoEstado)
    }
    // ------------------- FIN DE LA CORRECCIÓN -------------------

    suspend fun eliminar(cita: CitaEntity) {
        citaDao.eliminar(cita)
    }

    suspend fun verificarDisponibilidad(idVet: Int, inicio: Long, fin: Long): Boolean {
        val count = citaDao.contarCitasVeterinarioEnRango(idVet, inicio, fin)
        return count == 0
    }
}