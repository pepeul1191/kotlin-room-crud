package pe.edu.ulima.dbaccess.ui.app.viewmodels

import pe.edu.ulima.dbaccess.models.beans.User
import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.ulima.dbaccess.configs.LocalDB
import pe.edu.ulima.dbaccess.models.beans.Pokemon
import kotlin.concurrent.thread

class ProfileViewModel: ViewModel() {
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

    private val _imageUrl = MutableLiveData<String>("")
    var imageUrl: LiveData<String> = _imageUrl
    fun updateImageUrl(it: String){
        _imageUrl.postValue(it)
    }

    private val _user = MutableLiveData<String>("0")
    var user: LiveData<String> = _user
    fun updateUser(it: String){
        _user.postValue(it)
    }

    private val _email = MutableLiveData<String>("0")
    var email: LiveData<String> = _email
    fun updateEmail(it: String){
        _email.postValue(it)
    }


    fun getUser(context: Context){
        val _this = this
        viewModelScope.launch{
            try {
                withContext(Dispatchers.IO) {
                    val database = LocalDB.getDatabase(context)
                    val userDao = database.userDao()
                    val user: User = userDao.getUser()!!
                    _this.updateId(user!!.id)
                    _this.updateImageUrl(user!!.imageUrl)
                    _this.updateName(user!!.name)
                    _this.updateEmail(user!!.email)
                    _this.updateUser(user!!.user)
                    _this.updateTitle("Editar Usuario")
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

    fun updateUser(context: Context){

    }
}