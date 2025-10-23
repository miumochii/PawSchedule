package com.martinvergara_diegoboggle.pawschedule.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.martinvergara_diegoboggle.pawschedule.data.local.dao.CitaDao
import com.martinvergara_diegoboggle.pawschedule.data.local.dao.MascotaDao
import com.martinvergara_diegoboggle.pawschedule.data.local.dao.UsuarioDao
import com.martinvergara_diegoboggle.pawschedule.data.local.dao.VeterinarioDao
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.CitaEntity
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.MascotaEntity
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.UsuarioEntity
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.VeterinarioEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UsuarioEntity::class,
        VeterinarioEntity::class,
        MascotaEntity::class,
        CitaEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun veterinarioDao(): VeterinarioDao
    abstract fun mascotaDao(): MascotaDao
    abstract fun citaDao(): CitaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pawschedule_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        poblarDatosIniciales(database)
                    }
                }
            }
        }

        private suspend fun poblarDatosIniciales(database: AppDatabase) {
            val usuarioDao = database.usuarioDao()
            val veterinarioDao = database.veterinarioDao()

            // CLIENTES de prueba
            val cliente1 = UsuarioEntity(
                nombre = "Ana",
                apellido = "Silva",
                email = "ana@email.com",
                password = "123456",
                telefono = "+56911111111",
                rol = "CLIENTE"
            )

            val cliente2 = UsuarioEntity(
                nombre = "Pedro",
                apellido = "Martínez",
                email = "pedro@email.com",
                password = "123456",
                telefono = "+56922222222",
                rol = "CLIENTE"
            )

            // VETERINARIOS (en tabla UsuarioEntity)
            val vet1Usuario = UsuarioEntity(
                nombre = "María",
                apellido = "González",
                email = "maria@pawschedule.com",
                password = "vet123",
                telefono = "+56912345678",
                rol = "VETERINARIO",
                especialidad = "Medicina General"
            )

            // Insertar usuarios
            usuarioDao.insertar(cliente1)
            usuarioDao.insertar(cliente2)
            val vetId = usuarioDao.insertar(vet1Usuario)

            // VETERINARIOS (en tabla VeterinarioEntity separada)
            val veterinario1 = VeterinarioEntity(
                nombre = "María",
                apellido = "González",
                email = "maria@pawschedule.com",
                password = "vet123",
                telefono = "+56912345678",
                especialidad = "Medicina General",
                numeroLicencia = "VET-2024-001",
                horaInicio = "09:00",
                horaFin = "18:00"
            )

            val veterinario2 = VeterinarioEntity(
                nombre = "Carlos",
                apellido = "Ramírez",
                email = "carlos@pawschedule.com",
                password = "vet123",
                telefono = "+56987654321",
                especialidad = "Cirugía",
                numeroLicencia = "VET-2024-002",
                horaInicio = "10:00",
                horaFin = "19:00"
            )

            veterinarioDao.insert(veterinario1)
            veterinarioDao.insert(veterinario2)
        }
    }
}