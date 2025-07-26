@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gestortareas

// Importaciones necesarias para diseño, navegación y red
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ListarTareasScreen(navController: NavController) {
    val customBlue = Color(0xFF3F51B5) // Color personalizado para la barra superior

    // ViewModel propio para esta pantalla
    val viewModel: ListarTareasViewModel = viewModel()

    // Estado observado desde el ViewModel
    val state by viewModel.uiState.collectAsState()

    // Filtrar las tareas si hay texto en el campo de búsqueda
    val tareasFiltradas = remember(state.filtroTexto, state.tareas) {
        if (state.filtroTexto.isBlank()) state.tareas
        else state.tareas.filter { tarea ->
            tarea.titulo.contains(state.filtroTexto, ignoreCase = true) ||
                    tarea.fecha.contains(state.filtroTexto, ignoreCase = true)
        }
    }

    // Contenedor principal
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // Barra superior con botón de volver
        TopAppBar(
            title = { Text("Tareas Registradas") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = customBlue,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para filtrar tareas
        OutlinedTextField(
            value = state.filtroTexto,
            onValueChange = { viewModel.onFiltroChange(it) },
            label = { Text("Filtrar (opcional)") },
            placeholder = { Text("Buscar por título o fecha") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading -> {
                // Mostrar mensaje mientras se cargan las tareas
                Text("Cargando tareas...", style = MaterialTheme.typography.bodyLarge)
            }

            state.errorMessage.isNotEmpty() -> {
                // Mostrar mensaje de error si lo hay
                Text("Error: ${state.errorMessage}", color = MaterialTheme.colorScheme.error)
            }

            tareasFiltradas.isEmpty() -> {
                // Mostrar si no hay coincidencias con el filtro
                Text("No hay tareas que coincidan con el filtro.", style = MaterialTheme.typography.bodyLarge)
            }

            else -> {
                // Mostrar lista de tareas filtradas
                LazyColumn {
                    items(tareasFiltradas) { tarea ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Título: ${tarea.titulo}", style = MaterialTheme.typography.bodyLarge)
                                Text("Fecha: ${tarea.fecha}", style = MaterialTheme.typography.bodyMedium)
                                Text("Hora: ${tarea.hora}", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}









