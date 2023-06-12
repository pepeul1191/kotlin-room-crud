package pe.edu.ulima.dbaccess.services

import pe.edu.ulima.dbaccess.models.beans.Pokemon
import pe.edu.ulima.dbaccess.models.beans.User
import pe.edu.ulima.dbaccess.models.requests.UserValidate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("/user/validate")
    suspend  fun validate(@Body requestModel: UserValidate): Response<User>

    @GET("/user/pokemon")
    suspend fun getPokemons(
        @Query("id") id: Int,
    ): Response<List<Pokemon>>

    @GET("/user/following")
    suspend fun getFollowing(
        @Query("user_id") id: Int,
    ): Response<List<User>>

    @GET("/user/follower")
    suspend fun getFollower(
        @Query("user_id") id: Int,
    ): Response<List<User>>
}