package com.martinvergara_diegoboggle.pawschedule.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.martinvergara_diegoboggle.pawschedule.data.local.dao.CitaDao
import com.martinvergara_diegoboggle.pawschedule.data.local.dao.MascotaDao
import com.martinvergara_diegoboggle.pawschedule.data.local.dao.UsuarioDao
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.CitaEntity
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.MascotaEntity
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.UsuarioEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UsuarioEntity::class,
        MascotaEntity::class,
        CitaEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
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
                    "vetcare_database"
                )
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

            // Crear usuarios de prueba
            val veterinario1 = UsuarioEntity(
                nombre = "María",
                apellido = "González",
                email = "maria.gonzalez@vetcare.com",
                password = "vet123",
                telefono = "+56912345678",
                rol = "VETERINARIO",
                especialidad = "Medicina General"
            )

            val veterinario2 = UsuarioEntity(
                nombre = "Carlos",
                apellido = "Ramírez",
                email = "carlos.ramirez@vetcare.com",
                password = "vet123",
                telefono = "+56987654321",
                rol = "VETERINARIO",
                especialidad = "Cirugía"
            )

            val cliente1 = UsuarioEntity(
                nombre = "Ana",
                apellido = "Silva",
                email = "ana.silva@email.com",
                password = "cli123",
                telefono = "+56911111111",
                rol = "CLIENTE"
            )

            usuarioDao.insertar(veterinario1)
            usuarioDao.insertar(veterinario2)
            usuarioDao.insertar(cliente1)
        }
    }
}