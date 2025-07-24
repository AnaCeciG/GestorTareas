@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gestortareas

import RetrofitClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException



// Pantalla para cancelar/eliminar tareas
@Composable
fun CancelarTareasScreen(navController: NavController, tareas: MutableList<Tarea>) {
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
                } else {
                    errorMessage.value = "Error al cargar las tareas: ${response.code()} ${response.message()}"
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<List<Tarea>>, t: Throwable) {
                errorMessage.value = "Error: ${t.localizedMessage}"
                isLoading.value = false
            }
        })
    }

    val customRed = Color(0xFF2196F3)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TopAppBar(
            title = { Text("Cancelar Tarea") },
            navigationIcon = {
                IconButton(onClick = { navController.navigate("tareas") }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = customRed,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.value.isNotEmpty()) {
            Text("Error: ${errorMessage.value}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.error)
        }

        if (isLoading.value) {
            Text("Cargando tareas...", style = MaterialTheme.typography.bodyLarge)
        } else {
            if (tareas.isEmpty()) {
                Text("No hay tareas para cancelar.", style = MaterialTheme.typography.bodyLarge)
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(tareas) { tarea ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Tarea: ${tarea.titulo}", style = MaterialTheme.typography.bodyLarge)
                                    Text("Fecha: ${tarea.fecha}", style = MaterialTheme.typography.bodyMedium)
                                    Text("Horario: ${tarea.hora}", style = MaterialTheme.typography.bodyMedium)
                                }
                                Button(
                                    onClick = {
                                        val api = RetrofitClient.retrofit.create(TareaApi::class.java)
                                        api.deleteTareaById(tarea.id!!).enqueue(object : Callback<Void> {
                                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                                if (response.isSuccessful) {
                                                    tareas.remove(tarea)
                                                } else {
                                                    errorMessage.value = "No se pudo eliminar la tarea: ${response.code()} ${response.message()}"
                                                }
                                            }

                                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                                errorMessage.value = "Error: ${t.localizedMessage}"
                                            }
                                        })
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                                    modifier = Modifier.padding(start = 8.dp)
                                ) {
                                    Text("Eliminar", color = MaterialTheme.colorScheme.onError)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

