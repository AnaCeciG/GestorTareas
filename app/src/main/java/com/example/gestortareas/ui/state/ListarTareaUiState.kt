package com.example.gestortareas

// Estado de la pantalla ListarTareas
data class ListarTareasUiState(
    val tareas: List<Tarea> = emptyList(), // Lista de tareas desde el backend
    val filtroTexto: String = "",          // Texto de búsqueda
    val isLoading: Boolean = true,         // ¿Se está cargando?
    val errorMessage: String = ""          // Mensaje de error (si hay)
)
