package com.martinvergara_diegoboggle.pawschedule.data.repository

import com.martinvergara_diegoboggle.pawschedule.data.local.dao.VeterinarioDao
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.VeterinarioEntity
import kotlinx.coroutines.flow.Flow

class VeterinarioRepository(private val veterinarioDao: VeterinarioDao) {

    val allVeterinarios: Flow<List<VeterinarioEntity>> = veterinarioDao.getAllVeterinarios()

    suspend fun insert(veterinario: VeterinarioEntity): Long {
        return veterinarioDao.insert(veterinario)
    }

    suspend fun update(veterinario: VeterinarioEntity) {
        veterinarioDao.update(veterinario)
    }

    suspend fun delete(veterinario: VeterinarioEntity) {
        veterinarioDao.delete(veterinario)
    }

    suspend fun getVeterinarioById(id: Int): VeterinarioEntity? {
        return veterinarioDao.getVeterinarioById(id)
    }

    suspend fun login(email: String, password: String): VeterinarioEntity? {
        return veterinarioDao.login(email, password)
    }

    suspend fun getVeterinarioByEmail(email: String): VeterinarioEntity? {
        return veterinarioDao.getVeterinarioByEmail(email)
    }

    fun getVeterinariosByEspecialidad(especialidad: String): Flow<List<VeterinarioEntity>> {
        return veterinarioDao.getVeterinariosByEspecialidad(especialidad)
    }

    fun searchVeterinarios(query: String): Flow<List<VeterinarioEntity>> {
        return veterinarioDao.searchVeterinarios(query)
    }

    suspend fun desactivar(id: Int) {
        veterinarioDao.desactivar(id)
    }
}