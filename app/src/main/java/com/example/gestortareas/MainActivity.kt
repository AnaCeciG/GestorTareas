@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gestortareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
            val isDarkTheme = remember { mutableStateOf(false) }

            GestorTareasTheme(darkTheme = isDarkTheme.value) {
                MainApp(isDarkTheme)
            }
        }
    }
}

@Composable
fun MainApp(isDarkTheme: MutableState<Boolean>) {
    val navController = rememberNavController()
    val tareas = remember { mutableStateListOf<Tarea>() }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.imagn),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f)
        )

        NavHost(navController = navController, startDestination = "login") {

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

            composable("tareas") {
                TareasScreen(navController = navController, tareas = tareas)
            }
            composable("listarTareas") {
                ListarTareasScreen(navController = navController)
            }
            composable("cancelarTareas") {
                CancelarTareasScreen(navController = navController, tareas = tareas)
            }
            composable("acercaDe") {
                AcercaDeScreen(navController = navController)
            }
        }
    }
}
