package com.example.proyectoandroid.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proyectoandroid.data.model.UsuarioEntity

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    suspend fun getUsuarioPorCorreo(correo: String): UsuarioEntity?

    @Insert
    suspend fun insertarUsuario(usuario: UsuarioEntity)
}
