package pe.edu.ulima.dbaccess.ui.app.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pe.edu.ulima.dbaccess.ui.app.viewmodels.HomeViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.LoginViewModel

@Composable
@Preview
fun LoginScreenPreview(){
    LoginScreen(
        LoginViewModel(),
        rememberNavController()
    )
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavHostController
){

}