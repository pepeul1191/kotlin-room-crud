package pe.edu.ulima.dbaccess.ui.app.screens

import android.app.Activity
import android.graphics.Color
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import pe.edu.ulima.dbaccess.ui.app.viewmodels.HomeViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.LoginViewModel
import pe.edu.ulima.dbaccess.R as RE

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
    val context = LocalContext.current
    val user : String by viewModel.user.observeAsState(initial = "")
    val password : String by viewModel.password.observeAsState(initial = "")
    val message : String by viewModel.message.observeAsState(initial = "")
    val signInResult = remember { mutableStateOf<Result<Unit>?>(null) }
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        println("2 ++++++++++++++++++++++++++++++++++++++++++++")
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            // Handle the successful sign-in here
            println(account.email)
            println(account.familyName)
            println(account.photoUrl)
        } catch (e: ApiException) {
            // Handle sign-in failure
            println(e.stackTraceToString())
        }
    }
    // UI
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart,
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Bienvenido",
                textAlign = TextAlign.Center,
            )
            if(message.contains("Error")){
                Text(
                    text = message.split(":")[1],
                    textAlign = TextAlign.Center,
                    color = androidx.compose.ui.graphics.Color.Red
                )
            }else{
                Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    color = androidx.compose.ui.graphics.Color.Green
                )
            }
            // txtUser
            TextField(
                value = user,
                onValueChange = {
                    viewModel.updateUser(it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Usuario")
                },
                placeholder = {
                    Text(text= "")
                },
                singleLine = true,

            )
            // txtPassword
            TextField(
                value = password,
                onValueChange = {
                    viewModel.updatePassword(it)
                },
                label = {
                    Text(text = "Contraseña")
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text= "")
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            // boton Ingresar
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp/*, start = 40.dp, end = 40.dp*/), // start -> izquierda, end -> derecha
                onClick = {
                    viewModel.validate(context, navController)
                }
            ){
                Text("INGRESAR")
            }
            // boton Ingresar con Google
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp, /*start = 40.dp, end = 40.dp*/),
                onClick = {
                    println("1 ++++++++++++++++++++++++++++++++++++++")
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("400630042855-oon2jamp4s2u0o8r2due3vckm1u7ercq.apps.googleusercontent.com")
                        .requestEmail()
                        .build()
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    val signInIntent = googleSignInClient.signInIntent
                    googleSignInLauncher.launch(signInIntent)
                },
                //colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)) ,
                colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.ui.graphics.Color.Green)
            ){
                Text(
                    "INGRESAR CON GOOGLE",
                    color = androidx.compose.ui.graphics.Color.White
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                thickness = 2.dp,
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp/*, start = 40.dp, end = 40.dp*/), // start -> izquierda, end -> derecha
                onClick = {


                },
                colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.ui.graphics.Color.Gray)
            ){
                Text("Crear Cuenta".toUpperCase())
            }
        }
    }
}