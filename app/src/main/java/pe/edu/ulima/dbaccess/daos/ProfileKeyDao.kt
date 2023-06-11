package pe.edu.ulima.dbaccess.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pe.edu.ulima.dbaccess.models.beans.ProfileKey

@Dao
interface ProfileKeyDao {
    @Insert
    fun setFirstLoad(profileKey: ProfileKey)

    @Query("SELECT * FROM profile_keys WHERE user_id = :userId")
    fun getProfileUserById(userId: Int): ProfileKey?

    @Query("DELETE from profile_keys")
    fun deleteAllProfileKeys()
}