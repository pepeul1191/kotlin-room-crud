package pe.edu.ulima.dbaccess.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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
    suspend fun getPokemonById(id: Int): Pokemon?

    @Query("DELETE FROM pokemons WHERE id = :id")
    suspend fun deletePokemonById(id: Int): Unit

    @Update
    suspend fun updatePokemon(pokemon: Pokemon)

    @Insert
    suspend fun insertPokemon(pokemon: Pokemon)
}