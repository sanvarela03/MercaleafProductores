package com.example.mercaleafproductores.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mercaleafproductores.apis.UserAPI
import com.example.mercaleafproductores.blueprints.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository {
    private val webService: UserAPI = TODO()

    fun getUser(userEmail:String) : LiveData<Usuario> {
        val data = MutableLiveData<Usuario>()

        webService.getByEmail(userEmail).enqueue(object : Callback<Usuario>{

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Log.e("!API2","Error al llamar la api UserAPI.getByEmail(${userEmail}) ->", t)
            }

            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                data.value = response.body()
            }

        })
        return data
    }
}