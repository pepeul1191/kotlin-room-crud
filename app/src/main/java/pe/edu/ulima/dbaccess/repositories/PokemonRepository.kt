package pe.edu.ulima.dbaccess.repositories

import pe.edu.ulima.dbaccess.configs.BackendClient
import pe.edu.ulima.dbaccess.models.beans.Pokemon
import retrofit2.Call

class PokemonRepository {
    private val pokemonService = BackendClient.pokemonService
    /*
    suspend fun getPokemons(): List<Pokemon> {
        return pokemonService.fetchAll("", "")
    }
     */
}