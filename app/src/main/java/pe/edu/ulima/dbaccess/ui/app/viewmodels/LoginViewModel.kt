package pe.edu.ulima.dbaccess.ui.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
}