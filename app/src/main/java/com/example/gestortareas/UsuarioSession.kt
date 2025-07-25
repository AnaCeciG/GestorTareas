package com.example.gestortareas

import android.content.Context

// Clase Singleton para manejar la sesión del usuario usando SharedPreferences
object UsuarioSession {

    // Nombre del archivo de preferencias donde se guarda la información del usuario
    private const val PREF_NAME = "usuario_data"

    // Clave que se usará para guardar y recuperar el id del usuario
    private const val KEY_ID_USUARIO = "idUsuario"

    // Función para guardar el ID del usuario en SharedPreferences
    fun guardarIdUsuario(context: Context, idUsuario: Int) {
        // Se obtiene la instancia de SharedPreferences con el nombre definido
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // Se abre un editor para modificar los valores
        val editor = sharedPreferences.edit()

        // Se guarda el id con su clave correspondiente
        editor.putInt(KEY_ID_USUARIO, idUsuario)

        // Se aplican los cambios de forma asincrónica
        editor.apply()
    }

    // Función para recuperar el ID del usuario guardado
    fun obtenerIdUsuario(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // Se devuelve el valor almacenado o -1 si no existe
        return sharedPreferences.getInt(KEY_ID_USUARIO, -1)
    }
}
