package pe.edu.ulima.dbaccess.models.beans

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_keys")
data class ProfileKey(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "user_id")
    var userId: Int = 0,
    @ColumnInfo(name = "first_load")
    var firstLoad: Boolean = false
)
