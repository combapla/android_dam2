package com.example.proyectoandroid.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proyectoandroid.data.model.ProductoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertarProducto(producto: ProductoEntity)

    @androidx.room.Update
    suspend fun actualizarProducto(producto: ProductoEntity)

    @androidx.room.Delete
    suspend fun eliminarProducto(producto: ProductoEntity)

    @Query("SELECT * FROM productos")
    fun getProductos(): Flow<List<ProductoEntity>>

    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun getProductoPorId(id: Int): ProductoEntity?
}