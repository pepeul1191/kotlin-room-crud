package pe.edu.ulima.dbaccess.ui.app.viewmodels

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pe.edu.ulima.dbaccess.configs.BackendClient
import pe.edu.ulima.dbaccess.configs.LocalDB
import pe.edu.ulima.dbaccess.models.beans.Pokemon
import pe.edu.ulima.dbaccess.models.beans.User
import pe.edu.ulima.dbaccess.services.PokemonService
import pe.edu.ulima.dbaccess.services.UserService
import java.io.ByteArrayOutputStream
import kotlin.concurrent.thread

class PokemonDetailViewModel: ViewModel() {
    private val _id = MutableLiveData<Int>(0)
    var id: LiveData<Int> = _id
    fun updateId(it: Int){
        _id.postValue(it)
    }

    private val _title = MutableLiveData<String>("")
    var title: LiveData<String> = _title
    fun updateTitle(it: String){
        _title.postValue(it)
    }

    private val _name = MutableLiveData<String>("")
    var name: LiveData<String> = _name
    fun updateName(it: String){
        _name.postValue(it)
    }

    var bitmap by mutableStateOf<Bitmap?>(null)
    fun updateBitmap(newBitmap: Bitmap) {
        bitmap = newBitmap
    }


    private val _imageUrl = MutableLiveData<String>("")
    var imageUrl: LiveData<String> = _imageUrl
    fun updateImageUrl(it: String){
        _imageUrl.postValue(it)
    }

    private val _number = MutableLiveData<Int>(0)
    var number: LiveData<Int> = _number
    fun updateNumber(it: Int){
        _number.postValue(it)
    }

    private val _weight = MutableLiveData<Float>(0f)
    var weight: LiveData<Float> = _weight
    fun updateWeight(it: Float){
        _weight.postValue(it)
    }

    private val _height = MutableLiveData<Float>(0f)
    var height: LiveData<Float> = _height
    fun updateHeight(it: Float){
        _height.postValue(it)
    }

    fun getPokemon(id: Int, context: Context){
        val _this = this
        viewModelScope.launch{
            try {
                withContext(Dispatchers.IO) {
                    val database = LocalDB.getDatabase(context)
                    val pokemonDao = database.pokemonDao()
                    val pokemon: Pokemon? = pokemonDao.getPokemonById(id)
                    Log.d("POKEMON_VW", pokemon.toString())
                    _this.updateId(pokemon!!.id)
                    _this.updateImageUrl(pokemon!!.imageUrl)
                    _this.updateName(pokemon!!.name)
                    _this.updateHeight(pokemon!!.height)
                    _this.updateWeight(pokemon!!.weight)
                    _this.updateNumber(pokemon!!.number)
                    _this.updateTitle("Editar Pokemon")
                }
            }catch (e: Exception){
                e.printStackTrace()
                val activity = context as Activity
                activity.runOnUiThread{
                    Toast.makeText(
                        activity,
                        "Error: No se pudo mostrar el pokemon",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun deletePokemon(context: Context){
        val _this = this
        viewModelScope.launch{
            try {
                withContext(Dispatchers.IO) {
                    val id = _this.id.value!!
                    val database = LocalDB.getDatabase(context)
                    val pokemonDao = database.pokemonDao()
                    pokemonDao.deletePokemonById(id)
                }
            }catch (e: Exception){
                e.printStackTrace()
                val activity = context as Activity
                activity.runOnUiThread{
                    Toast.makeText(
                        activity,
                        "Error: No se pudo borrar el pokemon",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun unSetPokemon(context: Context){
        updateId(0)
        updateNumber(0)
        updateWeight(0f)
        updateHeight(0f)
        updateName("")
        updateImageUrl("")
        updateTitle("Crear Pokemon")
    }

    fun updatePokemon(context: Context){
        val _this = this
        Log.d("VW", "1 ++++++++++++++++++++++++++++++++++++++++++++++++")
        viewModelScope.launch{
            try {
                withContext(Dispatchers.IO) {
                    val pokemon = Pokemon(
                        id =  _this.id.value!!,
                        number = _this.number.value!!,
                        weight = _this.weight.value!!,
                        name = _this.name.value!!,
                        height = _this.height.value!!,
                        imageUrl = _this._imageUrl.value!!
                    )
                    Log.d("VW", pokemon.toString())
                    val database = LocalDB.getDatabase(context)
                    val pokemonDao = database.pokemonDao()
                    pokemonDao.updatePokemon(pokemon)
                }
            }catch (e: Exception){
                e.printStackTrace()
                val activity = context as Activity
                activity.runOnUiThread{
                    Toast.makeText(
                        activity,
                        "Error: No se pudo actualizar    el pokemon",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun createPokemon(context: Context){
        val _this = this
        viewModelScope.launch{
            try {
                withContext(Dispatchers.IO) {
                    val pokemon = Pokemon(
                        id = kotlin.random.Random.nextInt(900, 10000 + 1),
                        number = _this.number.value!!,
                        weight = _this.weight.value!!,
                        height = _this.height.value!!,
                        name = _this.name.value!!,
                        imageUrl = _this._imageUrl.value!!
                    )
                    Log.d("create", pokemon.toString())
                    val database = LocalDB.getDatabase(context)
                    val pokemonDao = database.pokemonDao()
                    pokemonDao.insertPokemon(pokemon)
                }
            }catch (e: Exception){
                e.printStackTrace()
                val activity = context as Activity
                activity.runOnUiThread{
                    Toast.makeText(
                        activity,
                        "Error: No se pudo actualizar el pokemon",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun uploadImage(context: Context){
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val filePart: RequestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), byteArray)
        val extraDataBody = RequestBody.create("text/plain".toMediaTypeOrNull(), this.id.toString())

        viewModelScope.launch{
            val pokemonApiService = BackendClient.buildService(PokemonService::class.java)
            try{
                withContext(Dispatchers.IO) {
                    val charPool: List<Char> = ('A'..'Z') + ('a'..'z') + ('0'..'9')
                    val randomString = (1..20)
                        .map { _ -> kotlin.random.Random.nextInt(0, charPool.size) }
                        .map(charPool::get)
                        .joinToString("")
                    val response = pokemonApiService.uploadFile(
                        MultipartBody.Part.createFormData("file", "$randomString.jpg", filePart),
                        extraDataBody
                    )

                }
            }catch (e: Exception){
                e.printStackTrace()
                val activity: Activity = context as Activity
                activity.runOnUiThread{
                    Toast.makeText(
                        activity,
                        "Error HTTP: No se subir la imagen",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}