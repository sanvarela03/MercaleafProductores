package com.example.mercaleafproductores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class SplashActivity : AppCompatActivity() {
    // This is the loading time of the splash screen
    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            // Check for existing Google Sign In account, if the user is already signed in
            // the GoogleSignInAccount will be non-null.
            val account = GoogleSignIn.getLastSignedInAccount(this)
            updateUI(account)

            // close this activity

        }, SPLASH_TIME_OUT)
    }
    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            val intent = Intent(this, ProducerActivity::class.java)
            startActivity(intent)
        }else{
            val intent: Intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}