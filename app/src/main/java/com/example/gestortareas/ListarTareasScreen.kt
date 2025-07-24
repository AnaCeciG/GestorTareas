@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gestortareas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.ui.platform.LocalContext
@Composable
fun ListarTareasScreen(navController: NavController) {
    val customBlue = Color(0xFF3F51B5)
    var filtroTexto by remember { mutableStateOf("") }
    val tareas = remember { mutableStateListOf<Tarea>() }
    val errorMessage = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val api = RetrofitClient.retrofit.create(TareaApi::class.java)
        api.getAllTareas().enqueue(object : Callback<List<Tarea>> {
            override fun onResponse(call: Call<List<Tarea>>, response: Response<List<Tarea>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        tareas.clear()
                        tareas.addAll(it)
                    }
                    isLoading.value = false
                } else {
                    errorMessage.value = "Error al cargar tareas: ${response.code()} ${response.message()}"
                    isLoading.value = false
                }
            }

            override fun onFailure(call: Call<List<Tarea>>, t: Throwable) {
                errorMessage.value = "Error: ${t.localizedMessage}"
                isLoading.value = false
            }
        })
    }

    val tareasFiltradas = remember(filtroTexto, tareas) {
        if (filtroTexto.isBlank()) tareas
        else tareas.filter { tarea ->
            tarea.titulo.contains(filtroTexto, ignoreCase = true) ||
                    tarea.fecha.contains(filtroTexto, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
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

        OutlinedTextField(
            value = filtroTexto,
            onValueChange = { filtroTexto = it },
            label = { Text("Filtrar (opcional)") },
            placeholder = { Text("Buscar por título o fecha") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading.value) {
            Text("Cargando tareas...", style = MaterialTheme.typography.bodyLarge)
        } else if (errorMessage.value.isNotEmpty()) {
            Text("Error: ${errorMessage.value}", color = MaterialTheme.colorScheme.error)
        } else if (tareasFiltradas.isEmpty()) {
            Text("No hay tareas que coincidan con el filtro.", style = MaterialTheme.typography.bodyLarge)
        } else {
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


                    }}
                    }


            }}








