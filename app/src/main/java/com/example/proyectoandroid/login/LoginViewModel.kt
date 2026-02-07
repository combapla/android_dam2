package com.example.proyectoandroid.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoandroid.data.model.UsuarioEntity
import com.example.proyectoandroid.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _usuario = MutableStateFlow<UsuarioEntity?>(null)
    val usuario: StateFlow<UsuarioEntity?> = _usuario

    fun login(correo: String, password: String) {
        viewModelScope.launch {
            _usuario.value = repository.login(correo, password)
        }
    }
}


