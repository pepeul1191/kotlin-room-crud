package pe.edu.ulima.dbaccess.ui.app.viewmodels

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.ulima.dbaccess.configs.BackendClient
import pe.edu.ulima.dbaccess.configs.LocalDB
import pe.edu.ulima.dbaccess.daos.ProfileKeyDao
import pe.edu.ulima.dbaccess.models.beans.Pokemon
import pe.edu.ulima.dbaccess.models.beans.ProfileKey
import pe.edu.ulima.dbaccess.services.PokemonService
import retrofit2.awaitResponse
import kotlin.concurrent.thread

class HomeViewModel: ViewModel() {
    private var _pokemons = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemons: StateFlow<List<Pokemon>> get() = _pokemons
    fun setPokemons(newItems: List<Pokemon>) {
        _pokemons.value = newItems
    }

    fun setPokemons(activity: Activity) {
        val apiService = BackendClient.buildService(PokemonService::class.java)
        viewModelScope.launch{
            try {
                withContext(Dispatchers.IO) {
                    val database = LocalDB.getDatabase(activity as Context)
                    val pokemonDao = database.pokemonDao()
                    val profileKeyDao: ProfileKeyDao = database.profileKeyDao()
                    val profileKey: ProfileKey? = profileKeyDao.getProfileUserById(1)
                    if (profileKey == null){
                        val response = apiService.fetchAll("", "")
                        if(response.code() == 200){
                            val pokemons: List<Pokemon> = response.body()!!
                            setPokemons(pokemons)
                            pokemonDao.insertMany(pokemons)
                            profileKeyDao.setFirstLoad(ProfileKey(userId = 1, firstLoad = true))
                        }
                    }else{
                        setPokemons(pokemonDao.getPokemons())
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
                activity.runOnUiThread{
                    Toast.makeText(
                        activity,
                        "Error HTTP: No se pudo traer el pokemon",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}