package pe.edu.ulima.dbaccess.ui.app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pe.edu.ulima.dbaccess.ui.app.viewmodels.LoginViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.SplashViewModel

@Composable
@Preview
fun SplashScreenPreview(){
    SplashScreen(
        SplashViewModel(),
        rememberNavController()
    )
}

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navController: NavHostController
){
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Bienvenido!?",
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        )
    }
    // validar si el usuario está logeado, osea, que existe en la tabla users
    viewModel.checkUser(context, navController)
}