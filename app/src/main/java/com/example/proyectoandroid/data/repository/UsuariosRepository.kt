package com.example.proyectoandroid.data.repository

import com.example.proyectoandroid.data.dao.UsuarioDao
import com.example.proyectoandroid.data.model.UsuarioEntity

class UsuarioRepository(private val dao: UsuarioDao) {

    suspend fun login(correo: String, password: String): UsuarioEntity {
        val usuario = dao.getUsuarioPorCorreo(correo)

        return if (usuario == null) {
            val nuevo = UsuarioEntity(correo = correo, password = password)
            dao.insertarUsuario(nuevo)
            nuevo
        } else {
            usuario
        }
    }
}