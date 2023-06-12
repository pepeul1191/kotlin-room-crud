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
}