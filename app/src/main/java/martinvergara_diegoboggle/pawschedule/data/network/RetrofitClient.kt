package martinvergara_diegoboggle.pawschedule.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // ⚠️ IMPORTANTE: Actualiza esta URL con tu ngrok actual
    private const val BASE_URL = "https://nonpunishing-descendingly-belkis.ngrok-free.dev/"

    // Logging para depuración (verás las peticiones en Logcat)
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY  // Ver todo el contenido
    }

    // Cliente HTTP con timeout y logging
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)  // Tiempo de conexión
        .readTimeout(30, TimeUnit.SECONDS)     // Tiempo de lectura
        .writeTimeout(30, TimeUnit.SECONDS)    // Tiempo de escritura
        .build()

    // Cliente base de Retrofit
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)  // ✅ Agregamos el cliente con logging
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