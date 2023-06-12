package pe.edu.ulima.dbaccess.models.requests

import androidx.room.PrimaryKey

data class UserValidate (
    var user: String = "",
    var password: String = "",
)