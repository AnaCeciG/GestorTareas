package com.example.gestortareas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel para la pantalla de registro de tareas.
 * Se encarga de mantener el estado del formulario y manejar la lógica de conexión con la API.
 */
class TareaViewModel : ViewModel() {

    // Estado interno mutable del formulario
    private val _uiState = MutableStateFlow(TareaUiState())

    // Estado externo que observa la UI (solo lectura)
    val uiState: StateFlow<TareaUiState> = _uiState

    // Maneja el cambio del campo Título
    fun onTituloChange(value: String) {
        _uiState.value = _uiState.value.copy(
            titulo = value,
            errorTitulo = value.isBlank() // Marca error si está vacío
        )
    }

    // Maneja el cambio del campo Fecha y valida el formato DD/MM/YYYY
    fun onFechaChange(value: String) {
        val error = value.length == 10 && !value.matches(
            Regex("^([0-2][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$")
        )
        _uiState.value = _uiState.value.copy(
            fecha = value,
            errorFecha = error
        )
    }

    // Maneja el cambio del campo Hora y valida el formato HH:MM (24h)
    fun onHoraChange(value: String) {
        val error = value.length == 5 && !value.matches(
            Regex("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")
        )
        _uiState.value = _uiState.value.copy(
            hora = value,
            errorHora = error
        )
    }

    /**
     * Función que intenta registrar una nueva tarea en la base de datos.
     * Usa Retrofit para enviar una solicitud POST al backend.
     *
     * @param onSuccess Se ejecuta si la tarea se registra correctamente.
     * @param onError Se ejecuta si ocurre un error en la red o en el servidor.
     */
    fun registrarTarea(onSuccess: () -> Unit, onError: (String) -> Unit) {
        // Crea el objeto Tarea con los datos actuales del formulario
        val tarea = Tarea(
            id = null,
            titulo = uiState.value.titulo,
            fecha = uiState.value.fecha,
            hora = uiState.value.hora
        )

        // Crea una instancia de la interfaz de API usando Retrofit
        val api = RetrofitClient.retrofit.create(TareaApi::class.java)

        // Llama al método createTarea (POST) y lo pone en espera con enqueue
        api.createTarea(tarea).enqueue(object : Callback<Tarea> {

            // Si el servidor responde
            override fun onResponse(call: Call<Tarea>, response: Response<Tarea>) {
                if (response.isSuccessful) {
                    // Si la tarea se registró correctamente, limpiamos el formulario
                    _uiState.value = TareaUiState()
                    onSuccess()
                } else {
                    // Si hubo un error en la respuesta (por ejemplo, 400 o 500)
                    onError("Error: ${response.code()}")
                }
            }

            // Si ocurrió un error de red (por ejemplo, sin conexión)
            override fun onFailure(call: Call<Tarea>, t: Throwable) {
                onError("Error: ${t.localizedMessage}")
            }
        })
    }
}



