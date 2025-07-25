@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gestortareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestortareas.ui.theme.GestorTareasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Estado para alternar entre tema claro/oscuro
            val isDarkTheme = remember { mutableStateOf(false) }

            // Aplica el tema general de la app
            GestorTareasTheme(darkTheme = isDarkTheme.value) {
                // Llama a la función principal que contiene la navegación
                MainApp(isDarkTheme)
            }
        }
    }
}

@Composable
fun MainApp(isDarkTheme: MutableState<Boolean>) {
    val navController = rememberNavController() // Controlador de navegación
    val tareas = remember { mutableStateListOf<Tarea>() } // Lista mutable para las tareas

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo con opacidad
        Image(
            painter = painterResource(id = R.drawable.imagn),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().alpha(0.5f)
        )

        // Configuración de navegación entre pantallas
        NavHost(navController = navController, startDestination = "login") {

            // Pantalla de Login
            composable("login") {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.imagn),
                        contentDescription = null,
                        modifier = Modifier.padding(top = 64.dp)
                    )
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate("menu") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onRegister = {
                            navController.navigate("register")
                        }
                    )
                }
            }

            // Pantalla de Registro
            composable("register") {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    },
                    onCancel = {
                        navController.popBackStack()
                    }
                )
            }

            // Pantalla de Menú Principal
            composable("menu") {
                MenuPrincipalScreen(
                    navController,
                    isDarkTheme = isDarkTheme,
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo("menu") { inclusive = true }
                        }
                    }
                )
            }

            // Pantalla para agregar tareas
            composable("tareas") {
                TareasScreen(navController = navController, tareas = tareas)
            }

            // Pantalla para listar tareas
            composable("listarTareas") {
                ListarTareasScreen(navController = navController)
            }

            // Pantalla para cancelar tareas
            composable("cancelarTareas") {
                CancelarTareasScreen(navController = navController, tareas = tareas)
            }

            // Pantalla Acerca de
            composable("acercaDe") {
                AcercaDeScreen(navController = navController)
            }
        }
    }
}
