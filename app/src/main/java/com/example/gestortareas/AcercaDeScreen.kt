package com.example.gestortareas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcercaDeScreen(navController: NavController) {
    // Definimos un color personalizado para usar en la barra superior
    val customBlue = Color(0xFF3F51B5)

    Column(
        modifier = Modifier
            .fillMaxSize() // Que ocupe toda la pantalla
            .padding(16.dp), // Espaciado interno
        horizontalAlignment = Alignment.CenterHorizontally // Centramos horizontalmente
    ) {
        // Barra superior (TopAppBar) con botón de retroceso
        TopAppBar(
            title = { Text("Acerca de") }, // Título de la barra
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) { // Botón para volver a la pantalla anterior
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = customBlue, // Fondo azul personalizado
                titleContentColor = Color.White // Texto blanco
            )
        )

        Spacer(modifier = Modifier.height(32.dp)) // Espacio entre la barra y el contenido

        // Contenido informativo de la pantalla
        Text(
            "📱 Gestor de Tareas",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Versión: 1.0") // Número de versión
        Text("Desarrollador: Alexis Parodi, Ana Gonzalez") // Nombres del grupo
        Text("Materia: Intro. Dispositivos Móviles") // Materia
        Text("Proyecto Final") // Motivo de la app
    }
}
