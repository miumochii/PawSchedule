package martinvergara_diegoboggle.pawschedule.data.network

//AQUI ESTA LA CONFIG DEL RETROFIT

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://nonpunishing-descendingly-belkis.ngrok-free.dev/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY  // ESTO ES PARA DEBUGGEAR Y VER LOS ERRORES.
    }

    private val okHttpClient = OkHttpClient.Builder() //AQUÍ SE CONFIGURA LA RED DE RETROFIT
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)  // TIEMPO DE CONEXIÓN
        .readTimeout(30, TimeUnit.SECONDS)     // TIEMPO DE LECTURA
        .writeTimeout(30, TimeUnit.SECONDS)    // TIEMPO DE ESCRITURA
        .build()

    private val retrofit: Retrofit by lazy { //SE CREA EL RETROFIT COMO TAL.
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val pawApiService: PawApiService by lazy { // INSTANCIA DONDE SE USA EL RETROFIT
        retrofit.create(PawApiService::class.java)
    }

    val authApiService: AuthApiService by lazy { // INSTANCIA DONDE SE USA EL RETROFIT
        retrofit.create(AuthApiService::class.java)
    }
}