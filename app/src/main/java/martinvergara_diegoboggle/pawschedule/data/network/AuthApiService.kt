package martinvergara_diegoboggle.pawschedule.data.network

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("register")
    suspend fun registerUser(@Body auth: UserAuth): LoginSuccess

    @POST("login")
    suspend fun loginUser(@Body auth: UserAuth): LoginSuccess
}