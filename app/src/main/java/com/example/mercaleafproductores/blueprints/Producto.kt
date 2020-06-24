package com.example.mercaleafproductores.blueprints


class Producto(var propietario: Productor) {

    var id:Long? = null
    var tipo: Tipo? = null
    var nombre: String = "nombre indefinido"
    var descripcion: String = "descripcion indefinida"
    var precio: String = "#"
    var costo: String = "#"
    var capacidadVenta = ""

    constructor(productor:Productor,nombre:String) : this(productor){
        this.nombre = nombre
    }
    constructor(productor:Productor,nombre:String, tipo:Tipo,descripcion:String, precio:String,capacidadVenta:String) : this(productor){
        this.nombre = nombre
        this.tipo = tipo
        this.descripcion = descripcion
        this.precio = precio
        this.capacidadVenta = capacidadVenta
    }
}