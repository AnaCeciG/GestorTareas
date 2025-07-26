@file:OptIn(ExperimentalMaterial3Api::class) // Permite usar componentes experimentales de Material3

package com.example.gestortareas

// Importaciones necesarias
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun TareasScreen(navController: NavController, tareas: MutableList<Tarea>) {
    val context = LocalContext.current

    val colorScheme = MaterialTheme.colorScheme

    // ViewModel y estado
    val viewModel: TareaViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Text("Gestor de Tareas", color = colorScheme.onPrimary) },
            actions = {
                TextButton(onClick = { navController.navigate("menu") }) {
                    Text("Menú Principal", color = colorScheme.onPrimary)
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate("listarTareas") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onPrimary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("Listar Tareas")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { navController.navigate("cancelarTareas") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.secondary,
                    contentColor = colorScheme.onSecondary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancelar Tareas")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Formulario con fondo contrastado
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Registrar Nueva Tarea",
                    style = MaterialTheme.typography.headlineSmall,
                    color = colorScheme.onSurface
                )

                OutlinedTextField(
                    value = state.titulo,
                    onValueChange = { viewModel.onTituloChange(it) },
                    label = { Text("Título de la Tarea") },
                    isError = state.errorTitulo,
                    modifier = Modifier.fillMaxWidth()
                )
                if (state.errorTitulo) Text("El título no puede estar vacío", color = Color.Red)

                OutlinedTextField(
                    value = state.fecha,
                    onValueChange = { viewModel.onFechaChange(it) },
                    label = { Text("Fecha (DD/MM/YYYY)") },
                    isError = state.errorFecha,
                    modifier = Modifier.fillMaxWidth()
                )
                if (state.errorFecha) Text("Formato de fecha inválido", color = Color.Red)

                OutlinedTextField(
                    value = state.hora,
                    onValueChange = { viewModel.onHoraChange(it) },
                    label = { Text("Hora (HH:MM)") },
                    isError = state.errorHora,
                    modifier = Modifier.fillMaxWidth()
                )
                if (state.errorHora) Text("Formato de hora inválido", color = Color.Red)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.registrarTarea(
                            onSuccess = {
                                Toast.makeText(context, "Tarea registrada", Toast.LENGTH_SHORT).show()
                            },
                            onError = {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    enabled = state.titulo.isNotBlank() && state.fecha.isNotBlank() && state.hora.isNotBlank()
                            && !state.errorTitulo && !state.errorFecha && !state.errorHora,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        contentColor = colorScheme.onPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar Tarea")
                }
            }
        }
    }
}

