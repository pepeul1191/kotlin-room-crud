package pe.edu.ulima.dbaccess.ui.app.viewmodels

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import pe.edu.ulima.dbaccess.models.beans.User
import pe.edu.ulima.dbaccess.services.PokemonService
import pe.edu.ulima.dbaccess.services.UserService
import retrofit2.awaitResponse
import kotlin.concurrent.thread

class HomeViewModel: ViewModel() {
    private val _userId = MutableLiveData<Int>(0)
    var userId: LiveData<Int> = _userId
    fun updateUserId(it: Int){
        _userId.postValue(it)
    }

    private var _pokemons = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemons: StateFlow<List<Pokemon>> get() = _pokemons
    fun setPokemons(newItems: List<Pokemon>) {
        _pokemons.value = newItems
    }
    fun setPokemons(activity: Activity) {
        val apiService = BackendClient.buildService(PokemonService::class.java)
        val userApiService = BackendClient.buildService(UserService::class.java)
        viewModelScope.launch{
            try {
                withContext(Dispatchers.IO) {
                    val database = LocalDB.getDatabase(activity as Context)
                    val pokemonDao = database.pokemonDao()
                    val profileKeyDao: ProfileKeyDao = database.profileKeyDao()
                    val profileKey: ProfileKey? = profileKeyDao.getProfileUserById(1)
                    if (profileKey == null){
                        // val response = apiService.fetchAll("", "")
                        // acceder a db y traer el usuario logueado
                        val userDao = database.userDao()
                        val user: User = userDao.getUser()!!
                        // traer los pokemons del servidor de ese usuario
                        val response = userApiService.getPokemons(user.id)
                        if(response.code() == 200){
                            val pokemons: List<Pokemon> = response.body()!!
                            setPokemons(pokemons)
                            updateUserId(user.id)
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

    private val _pokemonCount = MutableLiveData<Int>(0)
    var pokemonCount: LiveData<Int> = _pokemonCount
    fun updatePokemonCount(){
        viewModelScope.launch{
            withContext(Dispatchers.IO) {
                pokemons.collect { myList ->
                    val size = myList.size
                    _pokemonCount.postValue(size)
                }
            }
        }
    }

    private val _followingCount = MutableLiveData<Int>(0)
    var followingCount: LiveData<Int> = _followingCount
    fun updateFollowingCount(context: Context){
        val userApiService = BackendClient.buildService(UserService::class.java)
        try{
            viewModelScope.launch{
                withContext(Dispatchers.IO) {
                    val database = LocalDB.getDatabase(context)
                    val userDao = database.userDao()
                    val user: User = userDao.getUser()!!
                    val response = userApiService.getFollowing(user.id)
                    if(response.code() == 200){
                        val users: List<User> = response.body()!!
                        println(users.toString())
                        _followingCount.postValue(users.size)
                    }
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
            val activity: Activity = context as Activity
            activity.runOnUiThread{
                Toast.makeText(
                    activity,
                    "Error HTTP: No se pudo traer la cantidad de personas que sigues",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private val _followerCount = MutableLiveData<Int>(0)
    var followerCount: LiveData<Int> = _followerCount
    fun updateFollowerCount(context: Context){
        val userApiService = BackendClient.buildService(UserService::class.java)
        try{
            viewModelScope.launch{
                withContext(Dispatchers.IO) {
                    val database = LocalDB.getDatabase(context)
                    val userDao = database.userDao()
                    val user: User = userDao.getUser()!!
                    val response = userApiService.getFollower(user.id)
                    if(response.code() == 200){
                        val users: List<User> = response.body()!!
                        println(users.toString())
                        _followerCount.postValue(users.size)
                    }
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
            val activity: Activity = context as Activity
            activity.runOnUiThread{
                Toast.makeText(
                    activity,
                    "Error HTTP: No se pudo traer la cantidad de personas que te siguen",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}