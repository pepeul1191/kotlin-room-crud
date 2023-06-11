package pe.edu.ulima.dbaccess.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pe.edu.ulima.dbaccess.models.beans.Pokemon

@Dao
interface PokemonDao {
    @Insert
    fun insertMany(pokemon: List<Pokemon>)

    @Query("SELECT * FROM pokemons")
    suspend fun getPokemons(): List<Pokemon>

    @Query("DELETE FROM pokemons")
    fun deleteAllPokemons()

    @Query("SELECT * FROM pokemons WHERE id = :id")
    fun getPokemonById(id: Int): Pokemon?
}