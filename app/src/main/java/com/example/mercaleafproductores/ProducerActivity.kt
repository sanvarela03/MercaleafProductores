package com.example.mercaleafproductores

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mercaleafproductores.apis.ProducerAPI
import com.example.mercaleafproductores.blueprints.Producto
import com.example.mercaleafproductores.blueprints.Productor
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_producer.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProducerActivity : AppCompatActivity() {

    private lateinit var usuario: Productor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producer)
        showUserInfo()

        the_sign_out_btn.setOnClickListener { eventSignOut() }
        the_delete_user_btn.setOnClickListener { deleteThisUser() }

        val myArray = ArrayList<String>()
        myArray.add("Ver Perfil")
        myArray.add("Mis pedidos")

        val myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArray)

        the_options_spinner.adapter = myAdapter


    }

    fun deleteThisUser() {
        val producerAPI = ProducerAPI.create()
        val call = producerAPI.delete(this.usuario.idUsuario)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "!ProducerActivity",
                    "Error calling api ProducerAPI.delete(${this@ProducerActivity.usuario.idUsuario})",
                    t
                )
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                Toast.makeText(this@ProducerActivity, "Hasta pronto", Toast.LENGTH_LONG).show()
                eventSignOut()
            }
        })
    }

    private fun showUserInfo() {

        val cuenta = GoogleSignIn.getLastSignedInAccount(this)

        Log.d("!cuentaProductor", "<<${cuenta?.email}>>")
        Log.d("!valor", "<<${cuenta != null}>>")


        val producerAPI = ProducerAPI.create()
        val callP = producerAPI.getByEmail(cuenta?.email)
        callP.enqueue(object : Callback<Productor> {
            override fun onFailure(call: Call<Productor>, t: Throwable) {
                Log.d(
                    "!ProducerActivity",
                    "Error al llamar ProducerAPI.getByEmail(${cuenta?.email}) ->",
                    t
                )
            }

            override fun onResponse(call: Call<Productor>, response: Response<Productor>) {
                val producer: Productor? = response.body()
                this@ProducerActivity.usuario = producer!!
                Toast.makeText(
                    this@ProducerActivity,
                    "Bienvenido ${producer?.nombre}",
                    Toast.LENGTH_LONG
                )
                    .show()

                the_productor_name.text = producer?.nombre
                the_rol_name_txt.text = producer?.rol?.nombre
                the_productor_email.text = producer?.email
//                cuenta?.photoUrl.toString()
                Glide.with(this@ProducerActivity).load("http://192.168.0.108:8090/api/verfoto/9f9a5610-2920-4864-88e0-6267a6a37311_2020-07-02-19-48-00-503.jpg")
                    .apply(RequestOptions.circleCropTransform()).into(the_productor_photo)

                Log.d("FOTO","URL DE FOTO: ${cuenta?.photoUrl.toString()}")
                setListeners()

                the_product_list.adapter =
                    MyCustomAdapter(this@ProducerActivity, producer.productos!!)

                seeProduct()

            }

        })

        /*       //Obtener Usuario por la api
               val userApi = UserApi.create()
               val call = userApi.getByEmail(cuenta?.email)
               call.enqueue(object : Callback<Usuario> {
                   override fun onFailure(call: Call<Usuario>, t: Throwable) {
                       Log.d("!API", "Error calling API usuarios getByEmail(email)", t)
                   }

                   override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                       val user = response.body()

                       this@ProducerActivity.usuario = user!!

                       Log.d("!respuestaUsuarioCall", "<<${user?.nombre}>>")
                       Toast.makeText(this@ProducerActivity, "Bienvenido ${user?.nombre}", Toast.LENGTH_LONG)
                           .show()

                       the_productor_name.text = user?.nombre
                       the_rol_name_txt.text = user?.rol?.nombre
                       the_productor_email.text = user?.email

                       Glide.with(this@ProducerActivity).load(cuenta?.photoUrl.toString())
                           .apply(RequestOptions.circleCropTransform()).into(the_productor_photo)

                       setListeners()
                   }

               })*/

    }

    private fun getGoogleSignInClient(): GoogleSignInClient {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        return mGoogleSignInClient
    }

    private class MyCustomAdapter(var mContext: Context, var products: ArrayList<Producto>) :
        BaseAdapter() {

        // responsable de cunatas filas tengo en my lista
        override fun getCount(): Int {
            return products.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        //
        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }

        //Responsable de renderizar cada fila
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val layoutInflater = LayoutInflater.from(mContext)
            val productRow = layoutInflater.inflate(R.layout.product_main_row, parent, false)


            val name = productRow.findViewById<TextView>(R.id.the_product_name)
            name.text = products.get(position).nombre


            val description = productRow.findViewById<TextView>(R.id.the_product_description)
            description.text = "Descripcion: ${products.get(position).descripcion}"

            val precio = productRow.findViewById<TextView>(R.id.the_product_value)
            precio.text = "Precio: ${products.get(position).precio}"
            return productRow
//            val textView = TextView(this.mContext)
//            textView.text = "HERE is my row for my list view"
//            return textView
        }
    }


    private fun setListeners() {
        the_sign_out_btn.setOnClickListener { eventSignOut() }
        the_add_product_btn.setOnClickListener { eventAddProduct() }
    }

    private fun eventAddProduct() {
        val intent: Intent = Intent(this, AddProductActivity::class.java)
        intent.putExtra("email", this.usuario.email)
        startActivity(intent)
        finish()
    }

    private fun eventSignOut() {
        getGoogleSignInClient().signOut()
            .addOnCompleteListener(this, OnCompleteListener<Void?> {
                Toast.makeText(this, "GoodBye", Toast.LENGTH_LONG).show()
                val intent: Intent = Intent(this, SplashActivity
                ::class.java)
                startActivity(intent)
                finish()
            })
    }

    fun seeProduct() {
        the_product_list.setOnItemClickListener { parent, view, position, id ->
            var producto = usuario.productos?.get(position)
//            Toast.makeText(
//                this,
//                "Producto # ${position} nombre: ${producto?.nombre} idProducto:${producto?.id}",
//                Toast.LENGTH_SHORT
//            ).show()

            Log.d("wtf1", "id : <<${producto?.id}>>")

            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("productID", producto?.id.toString())
            startActivity(intent)


        }
    }


}