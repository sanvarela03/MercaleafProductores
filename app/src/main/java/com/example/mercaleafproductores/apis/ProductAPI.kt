package com.example.mercaleafproductores.apis


import com.example.mercaleafproductores.blueprints.Producto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductAPI {

    companion object Factory {
        fun create(): ProductAPI {

            var retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.0.106:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            var service = retrofit.create(ProductAPI::class.java)
            return service
        }
    }

    @GET("api/productos")
    fun getProducts(): Call<ArrayList<Producto>>

    @POST("api/productos/{idUsuario}")
    fun saveProduct(@Body producto: Producto, @Path("idUsuario") id: Long): Call<Producto>

    @GET("api/productos/{id}")
    fun getProduct(@Path("id") id: Long): Call<Producto>
}