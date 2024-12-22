package com.angelemv.android.pruebabbva.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.angelemv.android.pruebabbva.R
import com.angelemv.android.pruebabbva.viewmodel.ViewModel


@Composable
fun DashBoard(nav: NavHostController, viewModel: ViewModel) {
    val dogImage = viewModel.dogImage.observeAsState()
    val loginResponse = viewModel.loginResponse.observeAsState()

    // Cargar los datos del usuario desde DataStore cuando la vista se lanza
    LaunchedEffect(Unit) {
        viewModel.loadUserData()
    }

    // Llama a getRandomDogImage solo una vez
    LaunchedEffect(Unit) {
        viewModel.getRandomDogImage()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Muestra la imagen del perro
        if (dogImage.value != null) {
            val imageUrl = dogImage.value?.message
            AsyncImage(
                model = imageUrl,
                contentDescription = "Dog Image",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            Text(
                text = "Cargando imagen del perro...",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Muestra los datos del usuario si están disponibles
        loginResponse.value?.let { response ->
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = "Nombre: ${response.name}")
                Text(text = "Apellido: ${response.lastName}")
                Text(text = "ID: ${response.id}")
                Text(text = "Género: ${response.gender}")
                Text(text = "Edad: ${response.age}")
            }
        } ?: run {
            Text(text = "Cargando datos del usuario...")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.logout(nav)
            }
        ) {
            Text(text = "Cerrar Sesión")
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashBoardViewPreview() {
    DashBoard(
        nav = NavHostController(context = LocalContext.current),
        viewModel = ViewModel(context = LocalContext.current)
    )
}