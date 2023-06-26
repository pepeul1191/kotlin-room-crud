package pe.edu.ulima.dbaccess.services

import okhttp3.MultipartBody
import okhttp3.RequestBody
import pe.edu.ulima.dbaccess.models.beans.Pokemon
import pe.edu.ulima.dbaccess.models.beans.PokemonList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PokemonService{
    @GET("/pokemon/list")
    suspend fun fetchAll(
        @Query("name") name: String,
        @Query("generation_ids") generationIds: String
    ): Response<List<Pokemon>>

    @GET("/pokemon/{id}")
    fun fetchOne(
        @Path("id") id: Int
    ): Call<Pokemon>

    @Multipart
    @POST("/upload/demo")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
        @Part("extra_data") extraData: RequestBody
    ): Call<String>
}