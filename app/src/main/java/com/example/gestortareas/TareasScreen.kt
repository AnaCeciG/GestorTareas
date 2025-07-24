@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gestortareas

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun TareasScreen(navController: NavController, tareas: MutableList<Tarea>) {
    val customBlue = Color(0xFF3F51B5)
    val context = LocalContext.current

    var titulo by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }

    var errorTitulo by remember { mutableStateOf(false) }
    var errorFecha by remember { mutableStateOf(false) }
    var errorHora by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        TopAppBar(
            title = { Text("Gestor de Tareas") },
            actions = {
                TextButton(onClick = { navController.navigate("menu") }) {
                    Text("Menú Principal", color = Color.White)
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = customBlue,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate("listarTareas") },
                colors = ButtonDefaults.buttonColors(containerColor = customBlue),
                modifier = Modifier.weight(1f)
            ) {
                Text("Listar Tareas", color = MaterialTheme.colorScheme.onPrimary)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { navController.navigate("cancelarTareas") },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancelar Tareas", color = MaterialTheme.colorScheme.onSecondary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Registrar Nueva Tarea", style = MaterialTheme.typography.headlineSmall)

                OutlinedTextField(
                    value = titulo,
                    onValueChange = {
                        titulo = it
                        errorTitulo = it.isBlank()
                    },
                    label = { Text("Título de la Tarea") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorTitulo
                )
                if (errorTitulo) Text("El título no puede estar vacío", color = Color.Red)

                OutlinedTextField(
                    value = fecha,
                    onValueChange = {
                        fecha = it
                        errorFecha = it.length == 10 && !it.matches(Regex("^([0-2][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})\$"))
                    },
                    label = { Text("Fecha (DD/MM/YYYY)") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorFecha
                )
                if (errorFecha) Text("Formato de fecha inválido", color = Color.Red)

                OutlinedTextField(
                    value = hora,
                    onValueChange = {
                        hora = it
                        errorHora = it.length == 5 && !it.matches(Regex("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]\$"))
                    },
                    label = { Text("Hora (HH:MM)") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorHora
                )
                if (errorHora) Text("Formato de hora inválido", color = Color.Red)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val nuevaTarea = Tarea(id = null, titulo = titulo,  fecha = fecha, hora = hora)
                        val api = RetrofitClient.retrofit.create(TareaApi::class.java)

                        api.createTarea(nuevaTarea).enqueue(object : Callback<Tarea> {
                            override fun onResponse(call: Call<Tarea>, response: Response<Tarea>) {
                                if (response.isSuccessful) {
                                    Toast.makeText(context, "Tarea registrada: ${response.body()?.titulo}", Toast.LENGTH_SHORT).show()
                                    // Limpiar campos
                                    titulo = ""
                                    fecha = ""
                                    hora = ""
                                } else {
                                    Toast.makeText(context, "Error al registrar tarea: ${response.code()}", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<Tarea>, t: Throwable) {
                                Toast.makeText(context, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    },
                    enabled = titulo.isNotEmpty() && fecha.isNotEmpty() && hora.isNotEmpty() && !errorTitulo && !errorFecha && !errorHora,
                    colors = ButtonDefaults.buttonColors(containerColor = customBlue),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar Tarea", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}
