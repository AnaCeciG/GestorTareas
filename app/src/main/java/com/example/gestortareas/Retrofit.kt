
import com.example.gestortareas.TareaApi
import com.example.gestortareas.UsuarioApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Clase Singleton para configurar y obtener instancias de Retrofit
object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/" // URL base de la API

    // Crear un cliente OkHttp con tiempo de espera personalizado
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)  // Timeout de conexión
       .readTimeout(10, TimeUnit.SECONDS)     // Timeout de lectura
        .writeTimeout(10, TimeUnit.SECONDS)    // Timeout de escritura
        .build()

    // Instanciar Retrofit con el cliente configurado
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)  // Usar el cliente con los timeouts
            .addConverterFactory(GsonConverterFactory.create())  // Usar conversor Gson
            .build()
    }




    // Crear instancias de las APIs
    val usuarioApi: UsuarioApi by lazy {
        retrofit.create(UsuarioApi::class.java)  // Instancia de UsuarioApi
    }



    val tareaApi: TareaApi by lazy {
        retrofit.create(tareaApi::class.java)  // Instancia de ReservaApi (suponiendo que la clase esté definida)
    }
}



