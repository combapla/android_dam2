package com.example.proyectoandroid.data.database

import android.content.Context // Importante para el Contexto
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
// Importa tus entidades desde el paquete 'model'
import com.example.proyectoandroid.data.model.UsuarioEntity
import com.example.proyectoandroid.data.model.ProductoEntity
// Importa tus DAOs desde el paquete 'dao'
import com.example.proyectoandroid.data.dao.UsuarioDao
import com.example.proyectoandroid.data.dao.ProductoDao
@Database(
    entities = [UsuarioEntity::class, ProductoEntity::class],
    version = 1
)
abstract class TiendaDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun productoDao(): ProductoDao

    companion object {
        @Volatile
        private var INSTANCE: TiendaDatabase? = null

        fun getDatabase(context: Context): TiendaDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TiendaDatabase::class.java,
                    "tienda.db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}