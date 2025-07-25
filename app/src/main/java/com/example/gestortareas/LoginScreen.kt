package com.example.gestortareas

// --- IMPORTACIONES NECESARIAS PARA LA UI Y FUNCIONES ---
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// --- COMPOSABLE PRINCIPAL PARA LA PANTALLA DE LOGIN ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit, // Callback que se ejecuta si el login es exitoso
    onRegister: () -> Unit       // Callback para navegar a la pantalla de registro
) {
    // --- DEFINICIÓN DE COLORES PERSONALIZADOS ---
    val customRed = Color(0xFF312020) // Color para títulos y bordes
    val blueColor = colorResource(id = R.color.blue) // Color azul definido en colors.xml

    // --- OBTENER CONTEXTO PARA GUARDAR SESIÓN ---
    val context = LocalContext.current

    // --- VARIABLES DE ESTADO PARA USUARIO, CONTRASEÑA Y MENSAJE DE ERROR ---
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // --- CONTENEDOR PRINCIPAL CENTRADO ---
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // --- TARJETA DE LOGIN QUE CONTIENE TODOS LOS ELEMENTOS ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            // --- COLUMN PARA ORDENAR LOS ELEMENTOS VERTICALMENTE ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- TÍTULO DEL FORMULARIO ---
                Text(
                    text = "Inicio de Sesión",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = customRed,
                    modifier = Modifier
                        .padding(bottom = 16.dp, top = 16.dp)
                )

                // --- CAMPO DE TEXTO PARA USUARIO ---
                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                        errorMessage = "" // Limpiar error al escribir
                    },
                    label = {
                        Text(
                            "Usuario",
                            color = if (errorMessage.isNotEmpty()) customRed else MaterialTheme.colorScheme.onBackground
                        )
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = "Icono de usuario")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Introduce tu usuario") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = customRed,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        focusedLabelColor = customRed,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )

                // --- CAMPO DE TEXTO PARA CONTRASEÑA ---
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = "" // Limpiar error al escribir
                    },
                    label = {
                        Text(
                            "Contraseña",
                            color = if (errorMessage.isNotEmpty()) customRed else MaterialTheme.colorScheme.onBackground
                        )
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Icono de contraseña")
                    },
                    visualTransformation = PasswordVisualTransformation(), // Oculta el texto con puntos
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Introduce tu contraseña") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = customRed,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        focusedLabelColor = customRed,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )

                // --- MOSTRAR MENSAJE DE ERROR SI EXISTE ---
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // --- BOTÓN PARA INICIAR SESIÓN ---
                Button(
                    onClick = {
                        // Validación simple de campos vacíos
                        if (username.isEmpty() || password.isEmpty()) {
                            errorMessage = "Por favor, ingrese usuario y contraseña"
                        } else {
                            // Crear objeto de login y hacer solicitud a la API
                            val loginRequest = LoginRequest(username, password)
                            RetrofitClient.usuarioApi.login(loginRequest).enqueue(object : Callback<Long> {
                                override fun onResponse(call: Call<Long>, response: Response<Long>) {
                                    if (response.isSuccessful) {
                                        val idUsuario = response.body()
                                        if (idUsuario != null) {
                                            Log.d("LoginSuccess", "ID usuario: $idUsuario")
                                            // Guardar ID de usuario en SharedPreferences
                                            UsuarioSession.guardarIdUsuario(context, idUsuario.toInt())
                                            onLoginSuccess() // Ir a la siguiente pantalla
                                        } else {
                                            errorMessage = "Error: respuesta vacía"
                                        }
                                    } else {
                                        errorMessage = "Usuario o contraseña incorrectos"
                                    }
                                }

                                override fun onFailure(call: Call<Long>, t: Throwable) {
                                    errorMessage = "Error de conexión: ${t.message}"
                                }
                            })
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Iniciar Sesión")
                }

                // --- BOTÓN PARA IR A LA PANTALLA DE REGISTRO ---
                Button(
                    onClick = { onRegister() }, // Navega a pantalla de registro
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = blueColor,
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Registrarse",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
