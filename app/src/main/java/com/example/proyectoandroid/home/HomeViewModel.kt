package com.example.proyectoandroid.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoandroid.data.model.ProductoEntity
import com.example.proyectoandroid.data.repository.ProductoRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ProductoRepository
) : ViewModel() {

    val productos = repository.getProductos()

    fun insertarProducto(producto: ProductoEntity) {
        viewModelScope.launch {
            repository.insertarProducto(producto)
        }
    }

    fun actualizarProducto(producto: ProductoEntity) {
        viewModelScope.launch {
            repository.actualizarProducto(producto)
        }
    }

    fun eliminarProducto(producto: ProductoEntity) {
        viewModelScope.launch {
            repository.eliminarProducto(producto)
        }
    }

    fun getProductoById(id: Int): ProductoEntity? {
        return repository.getProductoSync(id)
    }
}

