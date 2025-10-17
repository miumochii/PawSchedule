package com.martinvergara_diegoboggle.pawschedule.data.local.repository

import com.martinvergara_diegoboggle.pawschedule.data.local.dao.MascotaDao
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.MascotaEntity
import kotlinx.coroutines.flow.Flow

class MascotaRepository(private val mascotaDao: MascotaDao) {

    fun obtenerPorDueno(idDueno: Int): Flow<List<MascotaEntity>> {
        return mascotaDao.obtenerPorDueno(idDueno)
    }

    fun obtenerTodas(): Flow<List<MascotaEntity>> {
        return mascotaDao.obtenerTodas()
    }

    suspend fun insertar(mascota: MascotaEntity): Long {
        return mascotaDao.insertar(mascota)
    }

    suspend fun obtenerPorId(id: Int): MascotaEntity? {
        return mascotaDao.obtenerPorId(id)
    }

    suspend fun actualizar(mascota: MascotaEntity) {
        mascotaDao.actualizar(mascota)
    }

    suspend fun eliminar(mascota: MascotaEntity) {
        mascotaDao.eliminar(mascota)
    }

    suspend fun desactivar(id: Int) {
        mascotaDao.desactivar(id)
    }

    suspend fun contarMascotasPorDueno(idDueno: Int): Int {
        return mascotaDao.contarMascotasPorDueno(idDueno)
    }
}