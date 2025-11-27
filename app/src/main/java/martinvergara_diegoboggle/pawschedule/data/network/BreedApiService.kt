package martinvergara_diegoboggle.pawschedule.data.network

// API, BASICAMENTE PARA CONSUMIR UNA API EXTERNA, EN ESTE CASO, DE RAZAS DE PERROS Y GATOS.

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class DogBreed(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

data class CatBreed(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)

interface DogApiService {
    @GET("breeds")
    suspend fun getBreeds(): List<DogBreed>
}

interface CatApiService {
    @GET("breeds")
    suspend fun getBreeds(): List<CatBreed>
}

object DogApiClient {
    private const val BASE_URL = "https://api.thedogapi.com/v1/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: DogApiService by lazy {
        retrofit.create(DogApiService::class.java)
    }
}

object CatApiClient {
    private const val BASE_URL = "https://api.thecatapi.com/v1/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: CatApiService by lazy {
        retrofit.create(CatApiService::class.java)
    }
}