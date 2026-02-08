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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(producto: ProductoEntity) {

    val context = LocalContext.current
    val imageRes = context.resources.getIdentifier(
        producto.imagen,
        "drawable",
        context.packageName
    )
    
    val networkObserver = remember { NetworkConnectivityObserver(context) }
    val isConnected by networkObserver.isConnected.collectAsState(initial = true)

    Column(Modifier.fillMaxSize()) {
        NetworkStatusBar(isConnected = isConnected)
        
        Column(Modifier.padding(24.dp)) {
            Text(producto.nombre, style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(16.dp))

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.height(200.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text("Precio: ${producto.precio} €")
        }
    }
}
