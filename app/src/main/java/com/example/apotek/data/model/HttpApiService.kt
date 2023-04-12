package com.example.apotek.data.model

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

private const val BASE_URL = "http://192.168.43.217:8080/apotek/"
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create(moshi)).build()
private val stringRetrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build()

interface HttpApiService {
    @GET("test.php")
    suspend fun checkConnection(): String
    @GET("getdatauser.php")
    suspend fun getUserData(@Query("username") username: String, @Query("password") password: String): User
    @POST("auth.php")
    suspend fun login(@Body data: Login): Response<serverResponse>
    @POST("register.php")
    suspend fun register(@Body data: User): Response<serverResponse>
    @GET("obat.php")
    suspend fun getObat(): List<Obat>
}

object HttpApi {
    val retrofitService: HttpApiService by lazy{
        retrofit.create(HttpApiService::class.java)
    }

    val retrofitStringService: HttpApiService by lazy{
        stringRetrofit.create(HttpApiService::class.java)
    }
}

// Data class

data class User (
    val id_user: String,
    val tipe_user: String,
    val nama_user: String,
    val alamat: String,
    val telpon: String,
    val username: String,
    val password: String
    )

data class serverResponse(
    val status: Boolean
)

data class Login (
    val username: String,
    val password: String
)

data class Obat (
    val id_obat: String,
    val nama_obat: String,
    val harga: String
    )


