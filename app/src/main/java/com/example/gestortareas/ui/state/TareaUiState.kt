package com.example.gestortareas

// Estado de la UI para la pantalla de Tareas
data class TareaUiState(
    val titulo: String = "",
    val fecha: String = "",
    val hora: String = "",
    val errorTitulo: Boolean = false,
    val errorFecha: Boolean = false,
    val errorHora: Boolean = false,
    val mensaje: String? = null
)
