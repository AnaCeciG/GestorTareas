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
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ListarTareasScreen(navController: NavController) {
    val customBlue = Color(0xFF3F51B5) // Color personalizado para la barra superior

    // Estado para el filtro de búsqueda de tareas
    var filtroTexto by remember { mutableStateOf("") }

    // Lista de tareas que se mostrará en pantalla
    val tareas = remember { mutableStateListOf<Tarea>() }

    // Estados para mensajes de error y carga
    val errorMessage = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(true) }

    // Se ejecuta una vez al cargar la pantalla
    LaunchedEffect(Unit) {
        // Cliente API usando Retrofit
        val api = RetrofitClient.retrofit.create(TareaApi::class.java)

        // Llamada GET para obtener todas las tareas
        api.getAllTareas().enqueue(object : Callback<List<Tarea>> {
            override fun onResponse(call: Call<List<Tarea>>, response: Response<List<Tarea>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        tareas.clear() // Limpiar lista previa
                        tareas.addAll(it) // Agregar tareas nuevas
                    }
                } else {
                    errorMessage.value = "Error al cargar tareas: ${response.code()} ${response.message()}"
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<List<Tarea>>, t: Throwable) {
                errorMessage.value = "Error: ${t.localizedMessage}"
                isLoading.value = false
            }
        })
    }

    // Filtrar las tareas si hay texto en el campo de búsqueda
    val tareasFiltradas = remember(filtroTexto, tareas) {
        if (filtroTexto.isBlank()) tareas
        else tareas.filter { tarea ->
            tarea.titulo.contains(filtroTexto, ignoreCase = true) ||
                    tarea.fecha.contains(filtroTexto, ignoreCase = true)
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
            value = filtroTexto,
            onValueChange = { filtroTexto = it },
            label = { Text("Filtrar (opcional)") },
            placeholder = { Text("Buscar por título o fecha") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar mensaje mientras se cargan las tareas
        if (isLoading.value) {
            Text("Cargando tareas...", style = MaterialTheme.typography.bodyLarge)
        }
        // Mostrar mensaje de error si lo hay
        else if (errorMessage.value.isNotEmpty()) {
            Text("Error: ${errorMessage.value}", color = MaterialTheme.colorScheme.error)
        }
        // Mostrar si no hay coincidencias con el filtro
        else if (tareasFiltradas.isEmpty()) {
            Text("No hay tareas que coincidan con el filtro.", style = MaterialTheme.typography.bodyLarge)
        }
        // Mostrar lista de tareas filtradas
        else {
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









