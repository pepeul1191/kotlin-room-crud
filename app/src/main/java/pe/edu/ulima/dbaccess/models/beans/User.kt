package pe.edu.ulima.dbaccess.models.beans

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var user: String = "",
    var name: String = "",
    var email: String = "",
    @SerializedName("image_url")
    @ColumnInfo(name = "image_url")
    var imageUrl: String = "",
)