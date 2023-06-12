package pe.edu.ulima.dbaccess.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pe.edu.ulima.dbaccess.models.beans.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("DELETE FROM users")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int
}