package com.example.mercaleafproductores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.mercaleafproductores.apis.ProductAPI
import com.example.mercaleafproductores.blueprints.Producto
import kotlinx.android.synthetic.main.activity_product.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val intent = intent
        val id = intent.getStringExtra("productID")

        val productAPI = ProductAPI.create()

        Log.d("wtf", "Id producto: <<${productAPI}>>")

        val call = productAPI.getProduct(id.toLong())

        call.enqueue(object : Callback<Producto> {
            override fun onFailure(call: Call<Producto>, t: Throwable) {
                Log.e("!ProductActivity", "Error calling API ProductAPI.getProduct(${id})")
            }

            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                val producto = response.body()




                Toast.makeText(
                    this@ProductActivity,
                    "Producto , Nombre: ${producto?.nombre} ID: ${producto?.id}",
                    Toast.LENGTH_SHORT
                ).show()



                et_name_p_id_2.setText("${producto?.nombre}",TextView.BufferType.EDITABLE)

            }

        })

    }
}