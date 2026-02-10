package com.example.proyectoandroid.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.proyectoandroid.login.LoginScreen
import com.example.proyectoandroid.login.LoginViewModel
import com.example.proyectoandroid.home.HomeScreen
import com.example.proyectoandroid.home.HomeViewModel
import com.example.proyectoandroid.detail.DetailScreen
import com.example.proyectoandroid.viewmodel.AppViewModelFactory

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current
    val factory = AppViewModelFactory(context)

    val loginViewModel: LoginViewModel = viewModel(factory = factory)

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(navController, loginViewModel)
        }

        composable("home") {
            val usuario by loginViewModel.usuario.collectAsState()
            val homeViewModel: HomeViewModel = viewModel(factory = factory)

            if (usuario != null) {
                HomeScreen(usuario!!, homeViewModel, navController)
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            }
        }

        composable("detail/{id}") { backStack ->
            val idStr = backStack.arguments?.getString("id")
            val id = idStr?.toIntOrNull() ?: -1
            
            val homeViewModel: HomeViewModel = viewModel(factory = factory)
            
            // Note: Producing a state from a suspend/sync call in composable is not ideal.
            // Ideally DetailScreen would have its own ViewModel or homeViewModel would have a Flow.
            val productos by homeViewModel.productos.collectAsState(initial = emptyList())
            val producto = productos.find { it.id == id }

            producto?.let {
                DetailScreen(it, navController)
            }
        }
    }
}
