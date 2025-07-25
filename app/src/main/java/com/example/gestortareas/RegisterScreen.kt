package com.example.gestortareas


// --- Importaciones necesarias para la UI y lógica del registro ---
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit, // Callback que se ejecuta si el registro fue exitoso
    onCancel: () -> Unit           // Callback que se ejecuta si el usuario cancela
) {
    // --- Variables de estado que almacenan los inputs del usuario ---
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }

    // --- Color personalizado tomado desde los recursos XML ---
    val blueColor = colorResource(id = R.color.blue)

    // --- Contenedor principal de la pantalla (en columna, centrado) ---
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White) // Fondo blanco
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Registro de Usuario",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF312020)
                )

                // Campo de texto: Usuario
                OutlinedTextField(
                    value = username.value,
                    onValueChange = {
                        username.value = it
                        errorMessage.value = ""
                    },
                    label = { Text("Usuario") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Icono de usuario") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = blueColor,
                        focusedLabelColor = blueColor
                    )
                )

                // Campo de texto: Contraseña
                OutlinedTextField(
                    value = password.value,
                    onValueChange = {
                        password.value = it
                        errorMessage.value = ""
                    },
                    label = { Text("Contraseña") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Icono de contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = blueColor,
                        focusedLabelColor = blueColor
                    )
                )

                // Campo de texto: Confirmar contraseña
                OutlinedTextField(
                    value = confirmPassword.value,
                    onValueChange = {
                        confirmPassword.value = it
                        errorMessage.value = ""
                    },
                    label = { Text("Confirmar Contraseña") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Icono confirmar contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = blueColor,
                        focusedLabelColor = blueColor
                    )
                )

                // --- Mostrar error si existe ---
                if (errorMessage.value.isNotEmpty()) {
                    Text(
                        text = errorMessage.value,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // --- Botón para registrarse ---
                Button(
                    onClick = {
                        // Validaciones de los campos antes de enviar al servidor
                        if (username.value.isEmpty() || password.value.isEmpty() || confirmPassword.value.isEmpty()) {
                            errorMessage.value = "Completa todos los campos"
                        } else if (password.value != confirmPassword.value) {
                            errorMessage.value = "Las contraseñas no coinciden"
                        } else {
                            // Crear el objeto Usuario para enviar al backend
                            val nuevoUsuario = Usuario(0, username.value, password.value)

                            // --------- CONEXIÓN AL BACKEND USANDO RETROFIT ---------
                            // RetrofitClient es un objeto singleton que contiene la configuración de Retrofit
                            // usuarioApi es la interfaz que define los métodos HTTP (como @POST /usuarios)

                            RetrofitClient.usuarioApi.register(nuevoUsuario)
                                .enqueue(object : Callback<Usuario> {

                                    // Este método se ejecuta si el servidor responde (ya sea éxito o error)
                                    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                                        if (response.isSuccessful) {
                                            // El servidor respondió con éxito (código 200-299)
                                            println("Usuario registrado: ${response.body()}")

                                            // Ejecutamos la función que redirige a la siguiente pantalla (por ejemplo, al Login)
                                            onRegisterSuccess()
                                        } else {
                                            // El servidor respondió, pero con un error (por ejemplo, 400 o 500)
                                            errorMessage.value = "No se pudo registrar: Código ${response.code()}"
                                        }
                                    }

                                    // Este método se ejecuta si hubo un error de red (sin respuesta del servidor)
                                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                                        // Fallo de red, posiblemente por falta de conexión o error de URL
                                        errorMessage.value = "Error de red: ${t.message}"
                                    }
                                })
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = blueColor, contentColor = Color.White)
                ) {
                    Text("Registrarse")
                }

                // --- Botón para cancelar y volver atrás ---
                Button(
                    onClick = onCancel,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray, contentColor = Color.White)
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}

