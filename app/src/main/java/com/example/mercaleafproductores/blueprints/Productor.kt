package com.example.mercaleafproductores.blueprints

import android.provider.ContactsContract

class Productor(id: Long) : Usuario(id) {

    constructor(
        id: Long,
        nombre: String,
        apellido: String,
        email: String,
        fechaNacimiento: String,
        direccion: String,
        nombreUsuario: String,
        password: String
    ) : this(id) {
        super.nombre = nombre
        super.apellido = apellido
        super.fechaNacimiento = fechaNacimiento
        super.direccion = direccion
        super.nombreUsuario = nombreUsuario
        super.password = password
        super.email = email
    }

    var productos: ArrayList<Producto>? = null
}