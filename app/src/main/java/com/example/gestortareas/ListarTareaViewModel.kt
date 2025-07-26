package com.example.gestortareas

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel responsable de obtener y mantener la lista de tareas.
 */
class ListarTareasViewModel : ViewModel() {

    // Estado interno mutable
    private val _uiState = MutableStateFlow(ListarTareasUiState())
    val uiState: StateFlow<ListarTareasUiState> = _uiState

    init {
        cargarTareas()
    }

    // Actualiza el texto de filtro
    fun onFiltroChange(texto: String) {
        _uiState.value = _uiState.value.copy(filtroTexto = texto)
    }

    // Carga tareas desde la API (GET)
    private fun cargarTareas() {
        val api = RetrofitClient.retrofit.create(TareaApi::class.java)
        api.getAllTareas().enqueue(object : Callback<List<Tarea>> {
            override fun onResponse(call: Call<List<Tarea>>, response: Response<List<Tarea>>) {
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(
                        tareas = response.body() ?: emptyList(),
                        isLoading = false,
                        errorMessage = ""
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Error: ${response.code()} ${response.message()}",
                        isLoading = false
                    )
                }
            }

            override fun onFailure(call: Call<List<Tarea>>, t: Throwable) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Error: ${t.localizedMessage}",
                    isLoading = false
                )
            }
        })
    }
}
