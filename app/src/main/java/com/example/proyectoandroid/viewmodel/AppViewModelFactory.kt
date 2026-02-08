package com.example.proyectoandroid.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoandroid.data.database.TiendaDatabase
import com.example.proyectoandroid.data.repository.ProductoRepository
import com.example.proyectoandroid.data.repository.UsuarioRepository
import com.example.proyectoandroid.login.LoginViewModel
import com.example.proyectoandroid.home.HomeViewModel

class AppViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    private val database by lazy { TiendaDatabase.getDatabase(context) }
    private val usuarioRepository by lazy { UsuarioRepository(database.usuarioDao()) }
    private val productoRepository by lazy { ProductoRepository(database.productoDao()) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(usuarioRepository) as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(productoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
