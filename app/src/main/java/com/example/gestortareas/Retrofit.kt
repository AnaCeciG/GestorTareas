
// Importaciones necesarias para la configuración de Retrofit y llamadas HTTP
import com.example.gestortareas.TareaApi
import com.example.gestortareas.UsuarioApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Objeto Singleton que gestiona la instancia de Retrofit para llamadas a la API
object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val usuarioApi: UsuarioApi by lazy {
        retrofit.create(UsuarioApi::class.java)
    }

    val tareaApi: TareaApi by lazy {
        retrofit.create(tareaApi::class.java) // <-- Aquí la corrección
    }
}





