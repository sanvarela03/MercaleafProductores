package com.example.mercaleafproductores.viewmodels

import androidx.lifecycle.SavedStateHandle

import com.example.mercaleafproductores.blueprints.Usuario
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

// UserProfileViewModel
class UserProfileViewModel( savedStateHandle: SavedStateHandle ) : ViewModel() {
    val userId : String = savedStateHandle["uid"] ?:  throw IllegalArgumentException("missing user id")
    val user : LiveData<Usuario> = TODO()
}

// UserProfileFragment
private val viewModel: UserProfileViewModel by viewModels(factoryProducer = { SavedStateVMFactory(this) })
