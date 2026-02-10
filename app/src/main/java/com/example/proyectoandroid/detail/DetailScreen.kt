package com.example.proyectoandroid.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.proyectoandroid.data.model.ProductoEntity
import com.example.proyectoandroid.network.NetworkConnectivityObserver
import com.example.proyectoandroid.ui.components.NetworkStatusBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(producto: ProductoEntity, navController: NavController) {

    val context = LocalContext.current
    
    // Attempt to get image resource, fallback to a default if not found or empty
    val imageRes = if (producto.imagen.isNotEmpty()) {
        val resId = context.resources.getIdentifier(
            producto.imagen,
            "drawable",
            context.packageName
        )
        if (resId != 0) resId else android.R.drawable.ic_menu_gallery
    } else {
        android.R.drawable.ic_menu_gallery
    }
    
    val networkObserver = remember { NetworkConnectivityObserver(context) }
    val isConnected by networkObserver.isConnected.collectAsState(initial = true)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del Producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NetworkStatusBar(isConnected = isConnected)
            
            Column(Modifier.padding(24.dp)) {
                Text(producto.nombre, style = MaterialTheme.typography.headlineMedium)

                Spacer(Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = producto.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )

                Spacer(Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = "Información",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Precio: ${producto.precio} €",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
