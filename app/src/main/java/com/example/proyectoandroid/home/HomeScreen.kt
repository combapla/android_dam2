package com.example.proyectoandroid.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectoandroid.data.model.UsuarioEntity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.proyectoandroid.data.model.ProductoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    usuario: UsuarioEntity,
    viewModel: HomeViewModel,
    navController: NavController
) {
    val productos by viewModel.productos.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var selectedProducto by remember { mutableStateOf<ProductoEntity?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                selectedProducto = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Producto")
            }
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(16.dp)) {
            Text(
                text = "Hola ${usuario.correo}",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(Modifier.height(16.dp))

            LazyColumn {
                items(productos) { producto ->
                    ListItem(
                        headlineContent = { Text(producto.nombre) },
                        supportingContent = { Text("${producto.precio}€") },
                        trailingContent = {
                            Row {
                                IconButton(onClick = {
                                    selectedProducto = producto
                                    showDialog = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                                }
                                IconButton(onClick = { viewModel.eliminarProducto(producto) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                }
                            }
                        },
                        modifier = Modifier.clickable {
                            navController.navigate("detail/${producto.id}")
                        }
                    )
                }
            }
        }
    }

    if (showDialog) {
        ProductDialog(
            producto = selectedProducto,
            onDismiss = { showDialog = false },
            onConfirm = { nombre, precio ->
                if (selectedProducto == null) {
                    viewModel.insertarProducto(
                        ProductoEntity(nombre = nombre, precio = precio, imagen = "")
                    )
                } else {
                    viewModel.actualizarProducto(
                        selectedProducto!!.copy(nombre = nombre, precio = precio)
                    )
                }
                showDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDialog(
    producto: ProductoEntity?,
    onDismiss: () -> Unit,
    onConfirm: (String, Double) -> Unit
) {
    var nombre by remember { mutableStateOf(producto?.nombre ?: "") }
    var precio by remember { mutableStateOf(producto?.precio?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (producto == null) "Nuevo Producto" else "Editar Producto") },
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") }
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(nombre, precio.toDoubleOrNull() ?: 0.0) }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}


