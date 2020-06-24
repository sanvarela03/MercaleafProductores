package com.example.mercaleafproductores.apis

import com.example.mercaleafproductores.blueprints.Productor
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ProducerAPI {
    companion object Factory {
        fun create(): ProducerAPI {

            var retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.0.106:8090/mercaleaf/productores/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            var service = retrofit.create(ProducerAPI::class.java)

            return service
        }
    }

    @POST("guardar")
    fun save(@Body productor: Productor): Call<Productor>

    @GET("obtener/{email}")
    fun getByEmail(@Path("email") email: String?): Call<Productor>

    @DELETE("borrar/{id}")
    fun delete(@Path("id") id: Long): Call<ResponseBody>
}