// Paquete principal de la app
package com.example.gestortareas

// Importaciones necesarias para usar Retrofit
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Data class que representa una tarea
data class Tarea(
    val id: Long?,         // ID de la tarea (nullable porque se genera en el servidor)
    val titulo: String,    // Título o nombre de la tarea
    val fecha: String,     // Fecha en formato texto (puede ser tipo "2025-07-25")
    val hora: String       // Hora en formato texto (por ejemplo, "14:00")
)

// Interfaz que define los endpoints disponibles de la API relacionados a tareas
interface TareaApi {

    // Endpoint GET que obtiene la lista de todas las tareas desde el servidor
    @GET("/tareas")
    fun getAllTareas(): Call<List<Tarea>>

    // Endpoint POST que permite crear una nueva tarea en el servidor
    // Se envía el objeto tarea como JSON en el cuerpo de la solicitud
    @POST("/tareas/crear")
    fun createTarea(@Body tarea: Tarea): Call<Tarea>

    // Endpoint DELETE que permite eliminar una tarea por su ID
    @DELETE("/tareas/eliminar/{id}")
    fun deleteTareaById(@Path("id") id: Long): Call<Void>
}
