package com.angelemv.android.pruebabbva.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.buildAnnotatedString
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
import java.time.format.TextStyle


@Composable
fun DashBoard(nav: NavHostController, viewModel: ViewModel) {
    val dogImage = viewModel.dogImage.observeAsState()
    val loginResponse = viewModel.loginResponse.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUserData()
        viewModel.getRandomDogImage()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Spacer(modifier = Modifier.height(32.dp))
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
        }

        Spacer(modifier = Modifier.height(32.dp))

        loginResponse.value?.let { response ->
            Column(horizontalAlignment = Alignment.Start) {
                TextWithLabelStyle("Nombre", response.name)
                Spacer(modifier = Modifier.height(8.dp))
                TextWithLabelStyle("Apellido", response.lastName)
                Spacer(modifier = Modifier.height(8.dp))
                TextWithLabelStyle("ID", response.id)
                Spacer(modifier = Modifier.height(8.dp))
                TextWithLabelStyle("Género", response.gender)
                Spacer(modifier = Modifier.height(8.dp))
                TextWithLabelStyle("Edad", response.age.toString())

            }
        } ?: run {
            Text(text = "Cargando datos del usuario...")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                viewModel.logout(nav)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0x8F3F51B5), // Cambia el color de fondo del botón
                contentColor = Color.White // Cambia el color del texto
            )
        ) {
            Text(text = "Cerrar Sesión")
        }
    }
}

@Composable
fun TextWithLabelStyle(text1: String, text2: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth() // Extiende la fila a todo el ancho
            .background(Color(0x8F3F51B5), shape = RoundedCornerShape(8.dp))
            .padding(16.dp) // Espaciado interno
    ) {
        Text(
            text = buildAnnotatedString {
                append(text1) // Agregar el primer texto
                (androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Bold)) // Aplicar negritas
                append(": ") // Espacio entre los textos
                append(text2) // Agregar el segundo texto
            },
            fontSize = 16.sp,
            color = Color.Black, // Color del texto
            modifier = Modifier.weight(1f) // Asegura que el texto ocupe todo el espacio
        )
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