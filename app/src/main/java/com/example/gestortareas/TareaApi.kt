package com.example.gestortareas

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class Tarea(
    val id: Long?,
    val titulo: String,
    val fecha: String,
    val hora: String,

)

// 2. Simulando API (sin conexión a red)
interface TareaApi {
    @GET("/tareas")
    fun getAllTareas(): Call<List<Tarea>>

    @POST("/tareas/crear")
    fun createTarea(@Body tarea: Tarea): Call<Tarea>

    @DELETE("/tareas/eliminar/{id}")
    fun deleteTareaById(@Path("id") id: Long): Call<Void>
}