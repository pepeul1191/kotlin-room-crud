package pe.edu.ulima.dbaccess.ui.app.viewmodels

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.ulima.dbaccess.configs.BackendClient
import pe.edu.ulima.dbaccess.configs.LocalDB
import pe.edu.ulima.dbaccess.exceptions.ErrorAccessLogin
import pe.edu.ulima.dbaccess.models.beans.Pokemon
import pe.edu.ulima.dbaccess.models.beans.ProfileKey
import pe.edu.ulima.dbaccess.models.beans.User
import pe.edu.ulima.dbaccess.models.requests.UserValidate
import pe.edu.ulima.dbaccess.services.PokemonService
import pe.edu.ulima.dbaccess.services.UserService

class LoginViewModel: ViewModel() {
    private val _user = MutableLiveData<String>("")
    var user: LiveData<String> = _user
    fun updateUser(it: String){
        _user.postValue(it)
    }

    private val _password = MutableLiveData<String>("")
    var password: LiveData<String> = _password
    fun updatePassword(it: String){
        _password.postValue(it)
    }

    private val _message = MutableLiveData<String>("")
    var message: LiveData<String> = _message
    fun updateMessage(it: String){
        _message.postValue(it)
    }

    public fun validate(context: Context, navController: NavHostController){
        viewModelScope.launch {
            val apiService = BackendClient.buildService(UserService::class.java)
            try {
                FirebaseCrashlytics.getInstance().setCustomKey("HTTP_ERRORXD", "Servidor apagado?")
                withContext(Dispatchers.IO) {
                    val response = apiService.validate(UserValidate(user.value!!, password.value!!))
                    if (response.code() == 200) {
                        val user: User = response.body()!!
                        Log.d("LOGIN_VM", user.toString())
                        val database = LocalDB.getDatabase(context)
                        val userDao = database.userDao()
                        userDao.deleteAll()
                        userDao.insert(user)
                        updateMessage("Usuario OK")
                        withContext(Dispatchers.Main) {
                            navController.navigate("/")
                        }
                    }else if(response.code() == 500){
                        FirebaseCrashlytics.getInstance().recordException(ErrorAccessLogin("usuario "+ user.value + " quiso entrar pero no pudo"))
                        updateMessage("Usuario y contraseña no válidos")
                    }else{
                        updateMessage("Ocurrió un error no esperado")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("1 +++++++++++++++++++++++++++++++++++++++++++++++++++++++")
                FirebaseCrashlytics.getInstance().recordException(e)
                FirebaseCrashlytics.getInstance().setCustomKey("HTTP_ERROR", "Servidor apagado?")
                FirebaseCrashlytics.getInstance().log("Custom log message")
                println("2 +++++++++++++++++++++++++++++++++++++++++++++++++++++++")
                val activity = context as Activity
                activity.runOnUiThread {
                    Toast.makeText(
                        activity,
                        "Error, No se pudo validar el usuario",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}