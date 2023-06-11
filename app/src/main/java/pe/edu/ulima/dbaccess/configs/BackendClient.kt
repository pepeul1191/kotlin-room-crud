package pe.edu.ulima.dbaccess.configs

import pe.edu.ulima.dbaccess.services.PokemonService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendClient {
    // const val BASE_URL = "https://programamovilv2.jose-jesusjes13.repl.co/"
    const val BASE_URL ="http://192.168.1.27:8000/"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val pokemonService: PokemonService by lazy {
        retrofit.create(PokemonService::class.java)
    }

    fun <T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}