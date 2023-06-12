package pe.edu.ulima.dbaccess.navigations.uis

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.ulima.dbaccess.configs.LocalDB
import pe.edu.ulima.dbaccess.models.beans.User

class TopbarViewModel: ViewModel() {
    fun goToProfile(context: Context, navController: NavHostController){
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val database = LocalDB.getDatabase(context)
                    val userDao = database.userDao()
                    val user: User = userDao.getUser()!!
                    navController.navigate("/profile/?user_id=${user.id}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                val activity: Activity = context as Activity
                activity.runOnUiThread {
                    Toast.makeText(
                        activity,
                        "Error, no se puede ir al perfil de usuario",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}