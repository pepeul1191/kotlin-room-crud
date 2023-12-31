package pe.edu.ulima.dbaccess.ui.app.screens

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
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
    var bitMapImage: Bitmap? by viewModel::bitmap
    // intent
    val takePicture = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            println("3 ++++++++++++++++++++++++++++++++++++")
            val imageBitmap = result.data?.extras?.get("data") as? Bitmap
            imageBitmap?.let { bitmap ->
                println(bitmap)
                viewModel.updateBitmap(bitmap)
                viewModel.uploadImage(context)
            }
        }
    }

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
                    if(bitMapImage == null){
                        Image(
                            painter = rememberImagePainter(data = imageUrl),
                            contentDescription = name,
                            modifier = Modifier
                                .size(140.dp)
                                .padding(bottom = 10.dp)
                                .fillMaxSize(),
                        )
                    }else{
                        Image(
                            bitmap = bitMapImage!!.asImageBitmap(),
                            contentDescription = name,
                            modifier = Modifier
                                .size(140.dp)
                                .padding(bottom = 10.dp)
                                .fillMaxSize(),
                        )
                    }
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
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
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
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
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
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
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
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        takePicture.launch(intent)
                    }
                ){
                    Text("Tomar Foto".toUpperCase())
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp/*, start = 40.dp, end = 40.dp*/), // start -> izquierda, end -> derecha
                    onClick = {
                        Log.d("POKEMON_SCREEN", title)
                        if(title == "Editar Pokemon"){
                            viewModel.updatePokemon(context)
                            //navController.navigate("/")
                        }else if(title == "Crear Pokemon"){
                            Log.d("POKEMON_SCREEN", "Crear Pokemon")
                            viewModel.createPokemon(context)
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
                            viewModel.deletePokemon(context)
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