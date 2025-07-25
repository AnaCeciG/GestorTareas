
// Importaciones necesarias para la configuración de Retrofit y llamadas HTTP
import com.example.gestortareas.TareaApi
import com.example.gestortareas.UsuarioApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Objeto Singleton que gestiona la instancia de Retrofit para llamadas a la API
object RetrofitClient {

    // Dirección base de la API (en este caso, localhost para el emulador Android)
    private const val BASE_URL = "http://10.0.2.2:8080/"

    // Cliente HTTP personalizado con tiempo de espera configurado
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)  // Tiempo máximo para establecer conexión
        .readTimeout(10, TimeUnit.SECONDS)     // Tiempo máximo para leer la respuesta
        .writeTimeout(10, TimeUnit.SECONDS)    // Tiempo máximo para escribir en la solicitud
        .build()

    // Instancia única de Retrofit configurada con la URL base, cliente y conversor JSON
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)                       // Usamos la URL base definida arriba
            .client(client)                          // Añadimos el cliente con timeout
            .addConverterFactory(GsonConverterFactory.create()) // Conversor de JSON a objetos Kotlin
            .build()
    }

    // Instancia de la interfaz UsuarioApi para llamar a los endpoints de usuario (login, registro)
    val usuarioApi: UsuarioApi by lazy {
        retrofit.create(UsuarioApi::class.java) // Se crea una implementación de la interfaz
    }

    // Instancia de la interfaz TareaApi para llamar a los endpoints de tareas
    val tareaApi: TareaApi by lazy {
        retrofit.create(tareaApi::class.java) // Lo mismo, pero para tareas
    }
}




