package pe.edu.ulima.dbaccess.ui.app.screens

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import pe.edu.ulima.dbaccess.ui.app.viewmodels.PokemonDetailViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.ProfileViewModel

@Composable
@Preview
fun ProfilePreview(){
    ProfileScreen(
        ProfileViewModel(),
        rememberNavController()
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navController: NavHostController
) {
    // get activity
    val context = LocalContext.current
    val activity = context as Activity
    // models
    val id: Int by viewModel.id.observeAsState(0)
    val name: String by viewModel.name.observeAsState("")
    val user: String by viewModel.user.observeAsState("")
    val email: String by viewModel.email.observeAsState("")
    val imageUrl: String by viewModel.imageUrl.observeAsState("")
    val title: String by viewModel.title.observeAsState("")

    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title.toUpperCase(),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = rememberImagePainter(data = imageUrl),
                        contentDescription = name,
                        modifier = Modifier
                            .size(140.dp)
                            .padding(bottom = 10.dp)
                            .fillMaxSize(),
                    )
                }
                TextField(
                    value = name,
                    onValueChange = {
                        viewModel.updateName(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Nombre")
                    },
                    placeholder = {
                        Text(text = "")
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent
                    )
                )
                TextField(
                    value = imageUrl,
                    onValueChange = {
                        viewModel.updateImageUrl(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Imagen Url")
                    },
                    placeholder = {
                        Text(text = "")
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent
                    )
                )
                TextField(
                    value = "${user.toString()}" ,
                    onValueChange = {
                        viewModel.updateUser(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "usuario")
                    },
                    placeholder = {
                        Text(text = "")
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent
                    )
                )
                TextField(
                    value = "${email.toString()}" ,
                    onValueChange = {
                        viewModel.updateEmail(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Correo")
                    },
                    placeholder = {
                        Text(text = "")
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent
                    )
                )
                // botones
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp/*, start = 40.dp, end = 40.dp*/), // start -> izquierda, end -> derecha
                    onClick = {
                        Log.d("POKEMON_SCREEN", title)
                        if(title == "Editar Pokemon"){
                            //viewModel.updatePokemon(context)
                            //navController.navigate("/")
                        }else if(title == "Crear Pokemon"){
                            Log.d("POKEMON_SCREEN", "Crear Pokemon")
                            //viewModel.createPokemon(context)
                            navController.navigate("/")
                        }
                    }
                ){
                    Text(title.toUpperCase())
                }

            }
        }
    }
}