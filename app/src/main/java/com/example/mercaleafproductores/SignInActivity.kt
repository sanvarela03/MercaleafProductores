package com.example.mercaleafproductores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.mercaleafproductores.apis.ProducerAPI
import com.example.mercaleafproductores.blueprints.Productor
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signin.the_id_txt
import kotlinx.android.synthetic.main.activity_signin.the_productor_name
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        if (getGoogleSignInAccount() != null) {
            the_productor_name.visibility = View.INVISIBLE
            var nameParams = the_productor_name.layoutParams
            nameParams.height = 0
            nameParams.width = 0
            the_productor_name.layoutParams = nameParams

            the_last_name_txt.visibility = View.INVISIBLE
            var lastNameParams = the_last_name_txt.layoutParams
            lastNameParams.height = 0
            lastNameParams.width = 0
            the_last_name_txt.layoutParams = lastNameParams


            the_email_txt.visibility = View.INVISIBLE
            var emailParams = the_email_txt.layoutParams
            emailParams.height = 0
            emailParams.width = 0
            the_email_txt.layoutParams = emailParams
        }


        setListeners()

    }

    private fun getGoogleSignInAccount(): GoogleSignInAccount? {

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn
            .getClient(this, gso)

        val acct = GoogleSignIn.getLastSignedInAccount(this)

        return acct
    }

    private fun setListeners() {
        the_send_btn.setOnClickListener { eventSave() }
        the_cancel_signin_btn.setOnClickListener { eventSignOut() }
    }

    private fun eventSave() {

        if (getGoogleSignInAccount() == null) {
            val id: Long = the_id_txt.text.toString().toLong()
            val name: String = the_productor_name.text.toString()
            val lastName: String = the_last_name_txt.text.toString()
            val email: String = the_email_txt.text.toString()
            val address: String = the_address_txt.text.toString()
            val userName: String = the_user_name_txt.text.toString()
            val password: String = the_password_txt.text.toString()
            val birthday: String = the_birtday_txt.text.toString()

            var producer: Productor =
                Productor(
                    id,
                    "$name",
                    "$lastName",
                    "$email",
                    "$birthday",
                    "$address",
                    "$userName",
                    "$password"
                )


            val producerAPI = ProducerAPI.create()
            val callP = producerAPI.save(producer)

            callP.enqueue(object : Callback<Productor> {
                override fun onFailure(call: Call<Productor>, t: Throwable) {
                    Log.d("API-p", "Error al llamar la api save de ProducerAPI", t)
                }

                override fun onResponse(call: Call<Productor>, response: Response<Productor>) {
                    val intent: Intent = Intent(this@SignInActivity, ProducerActivity::class.java)
                    startActivity(intent)
                }

            })

        } else {

            val id: Long = the_id_txt.text.toString().toLong()
            val name: String = getGoogleSignInAccount()?.displayName.toString()
            val lastName: String = ""
            val email: String = getGoogleSignInAccount()?.email.toString()
            val address: String = the_address_txt.text.toString()
            val userName: String = the_user_name_txt.text.toString()
            val password: String = the_password_txt.text.toString()
            val birthday: String = the_birtday_txt.text.toString()

            var producer: Productor =
                Productor(
                    id,
                    "$name",
                    "$lastName",
                    "$email",
                    "$birthday",
                    "$address",
                    "$userName",
                    "$password"
                )


            val producerAPI = ProducerAPI.create()
            val callP = producerAPI.save(producer)

            callP.enqueue(object : Callback<Productor> {
                override fun onFailure(call: Call<Productor>, t: Throwable) {
                    Log.d("API-p", "Error al llamar la api save de ProducerAPI", t)
                }

                override fun onResponse(call: Call<Productor>, response: Response<Productor>) {
                    val intent: Intent = Intent(this@SignInActivity, ProducerActivity::class.java)
                    startActivity(intent)
                }

            })
        }

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

    private fun eventSignOut() {
        getGoogleSignInClient().signOut()
            .addOnCompleteListener(this, OnCompleteListener<Void?> {
                Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
                val intent: Intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
                finish()
            })
    }

}