package com.example.proyectoandroid.data.repository

import com.example.proyectoandroid.data.dao.ProductoDao
import com.example.proyectoandroid.data.model.ProductoEntity

class ProductoRepository(private val dao: ProductoDao) {
    fun getProductos() = dao.getProductos()

    suspend fun insertarProducto(producto: ProductoEntity) {
        dao.insertarProducto(producto)
    }

    suspend fun actualizarProducto(producto: ProductoEntity) {
        dao.actualizarProducto(producto)
    }

    suspend fun eliminarProducto(producto: ProductoEntity) {
        dao.eliminarProducto(producto)
    }

    suspend fun getProductoById(id: Int): ProductoEntity? {
        return dao.getProductoPorId(id)
    }

    fun getProductoSync(id: Int): ProductoEntity? {

        return null
    }
}