package com.example.mercaleafproductores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mercaleafproductores.apis.ProducerAPI
import com.example.mercaleafproductores.apis.ProductAPI
import com.example.mercaleafproductores.blueprints.Producto
import com.example.mercaleafproductores.blueprints.Productor
import com.example.mercaleafproductores.blueprints.Tipo
import kotlinx.android.synthetic.main.activity_add_product.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        btn_agregar_id.setOnClickListener { create() }
    }

    fun create() {
        val intent = intent
        val email = intent.getStringExtra("email")


        val producerAPI = ProducerAPI.create()

        val callP = producerAPI.getByEmail(email)

        callP.enqueue(object : Callback<Productor>{
            override fun onFailure(call: Call<Productor>, t: Throwable) {
                Log.d("AddProductActivity","Error al llamar ProductAPI.getByEmail(${email}) ->", t)
            }

            override fun onResponse(call: Call<Productor>, response: Response<Productor>) {
                createProduct(response.body())
            }
        })

   /*     val userApi = UserApi.create()
        val call = userApi.getByEmail(email)

        call.enqueue(object : Callback<Usuario> {
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Log.e("appii", "error calling getByEmail en Producto", t)
            }

            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                createProduct(response.body())
            }

        })*/


    }

    private fun createProduct(productor: Productor?) {
        val nombreProducto = et_name_p_1.text.toString()
        val descripcion = et_description_p_id_1.text.toString()
        val capacidadVenta = et_capacity_p_id_1.text.toString()
        val precio = et_price_p_id_1.text.toString()
        val tipo = Tipo(1,"verdura")


        val producto = Producto(productor!!,nombreProducto,tipo,descripcion,precio,capacidadVenta)

        val productApi = ProductAPI.create()
        val call = productApi.saveProduct(producto,productor.idUsuario!!)

        call.enqueue(object :Callback<Producto>{
            override fun onFailure(call: Call<Producto>, t: Throwable) {
                Log.d("Api","Error al llamar el api saveProduct()", t)
            }

            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                val intent: Intent = Intent(this@AddProductActivity, ProducerActivity::class.java)
                startActivity(intent)
                finish()
            }

        })


    }
}