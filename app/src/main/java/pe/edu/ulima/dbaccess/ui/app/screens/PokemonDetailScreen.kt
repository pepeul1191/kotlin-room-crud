package pe.edu.ulima.dbaccess.ui.app.screens

import android.app.Activity
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import pe.edu.ulima.dbaccess.ui.app.viewmodels.HomeViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.PokemonDetailViewModel

@Composable
@Preview
fun PokemonDetailPreview(){
    PokemonDetailScreen(
        PokemonDetailViewModel(),
        rememberNavController()
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailViewModel,
    navController: NavHostController
) {
    // get activity
    val context = LocalContext.current
    val activity = context as Activity
    // models
    val id: Int by viewModel.id.observeAsState(0)
    val name: String by viewModel.name.observeAsState("")
    val number: Int by viewModel.number.observeAsState(0)
    val weight: Float by viewModel.weight.observeAsState(0f)
    val height: Float by viewModel.height.observeAsState(0f)
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
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 10.dp),
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
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
                    value = "${number.toString()}" ,
                    onValueChange = {
                        viewModel.updateNumber(it.toInt())
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Número")
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
                    value = "${weight.toString()}" ,
                    onValueChange = {
                        viewModel.updateWeight(it.toFloat())
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Peso(kg)")
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
                    value = "${height.toString()}" ,
                    onValueChange = {
                        viewModel.updateHeight(it.toFloat())
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Estatura(m)")
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
                        if(title.toUpperCase() == "Editar Pokemon"){
                            Log.d("POKEMON_SCREEN", "Editar Pokemon")
                            navController.navigate("/")
                        }else if(title.toUpperCase() == "Crear Pokemon"){
                            Log.d("POKEMON_SCREEN", "Crear Pokemon")
                            navController.navigate("/")
                        }
                    }
                ){
                    Text(title.toUpperCase())
                }
                // boton eliminar
                if(title.toUpperCase() == "Editar Pokemon".toUpperCase()){
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp/*, start = 40.dp, end = 40.dp*/), // start -> izquierda, end -> derecha
                        onClick = {
                            Log.d("POKEMON_SCREEN", "Borrar Pokemon")
                            navController.navigate("/")
                        }
                    ){
                        Text("Borrar Pokemon".toUpperCase())
                    }
                }
            }
        }
    }
}