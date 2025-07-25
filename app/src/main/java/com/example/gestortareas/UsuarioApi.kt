package com.example.gestortareas

// Importaciones necesarias para trabajar con Retrofit y peticiones HTTP
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// ----------- CLASE DE DATOS PARA LOGIN -------------

// Clase que representa la estructura del cuerpo de una solicitud de inicio de sesión
data class LoginRequest(
    val usuario: String,      // nombre de usuario
    val contrasenha: String   // contraseña del usuario
)

// ----------- INTERFAZ DE RETROFIT PARA USUARIOS -------------

// Interfaz que define las funciones que representan las llamadas HTTP relacionadas con los usuarios
interface UsuarioApi {

    // Llamada POST al endpoint /login con un cuerpo de tipo LoginRequest
    // Devuelve un Call con el ID del usuario si el login es exitoso
    @POST("/login")
    fun login(@Body loginRequest: LoginRequest): Call<Long>

    // Llamada POST al endpoint /usuarios para registrar un nuevo usuario
    @POST("usuarios")
    fun register(@Body usuario: Usuario): Call<Usuario>

    // Llamada GET para obtener todos los usuarios registrados
    @GET("/usuarios")
    fun getAllUsuarios(): Call<List<Usuario>>

    // Llamada GET para obtener un usuario específico por su ID
    @GET("/usuarios/{idUsuario}")
    fun getUsuarioById(@Path("idUsuario") idUsuario: Int): Call<Usuario>
}

// ----------- CLASE DE DATOS PARA USUARIO -------------

// Representa la entidad de un usuario en la app
data class Usuario(
    val idUsuario: Int,       // ID del usuario
    val usuario: String,      // nombre de usuario
    val contrasenha: String   // contraseña del usuario
)
