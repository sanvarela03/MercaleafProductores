package com.example.mercaleafproductores.apis

import com.example.mercaleafproductores.blueprints.Usuario
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPI {

    companion object Factory {
        fun create(): UserAPI {

            var retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.0.106:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            var service = retrofit.create(UserAPI::class.java)
            return service
        }
    }

    @GET("api/usuarios")
    fun getUsers(): Call<ArrayList<  Usuario>>

    @POST("api/usuarios")
    fun saveUser(@Body usuario: Usuario) : Call<Usuario>

    @GET("api/usuarios/{id}")
    fun getUser(@Path("id") id: Long): Call<Usuario>

    @GET("api/usuarios/productores/email/reconocer/{email}")
    fun isKnown(@Path("email") email:String?):Call<Boolean>

    @GET("api/usuarios/productores/email/{email}")
    fun getByEmail(@Path("email") email: String?):Call<Usuario>

}