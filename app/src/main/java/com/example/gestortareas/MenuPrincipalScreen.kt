package com.example.gestortareas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MenuPrincipalScreen(
    navController: NavController,
    isDarkTheme: MutableState<Boolean>,
    onLogout: () -> Unit
) {
    val customRed = Color(0xFF791414)
    val blueColor = colorResource(id = R.color.blue)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(350.dp)
                .height(500.dp)
                .background(color = Color.White, shape = MaterialTheme.shapes.medium)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¡Organiza tus tareas!",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = customRed,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 50.dp)
            )

            // Botón Agregar Tarea
            Button(
                onClick = { navController.navigate("tareas") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = blueColor)
            ) {
                Icon(Icons.Default.Person, contentDescription = "Agregar", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Agregar Tarea", color = Color.White)
            }

            // Botón Ver Tareas
            Button(
                onClick = { navController.navigate("listarTareas") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = blueColor)
            ) {
                Text("Ver Tareas", color = Color.White)
            }

            // Botón Acerca de
            Button(
                onClick = { navController.navigate("acercaDe") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = blueColor)
            ) {
                Icon(Icons.Default.Info, contentDescription = "Acerca de", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Acerca de", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Botón Cerrar Sesión
            Button(
                onClick = { onLogout() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = blueColor),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    "Cerrar Sesión",
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge
                )
            }

        }
    }
}
