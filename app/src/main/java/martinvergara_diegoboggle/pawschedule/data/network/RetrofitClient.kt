package martinvergara_diegoboggle.pawschedule.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // ⚠️ Verifica que esta URL sea la correcta de Ngrok
    private const val BASE_URL = "https://nonpunishing-descendingly-belkis.ngrok-free.dev/"

    // Cliente base de Retrofit (configuración general)
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 1. Servicio para Mascotas y Citas (CRUD general)
    val pawApiService: PawApiService by lazy {
        retrofit.create(PawApiService::class.java)
    }

    // 2. Servicio para Autenticación (Login/Register)
    val authApiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }
}