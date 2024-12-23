package com.angelemv.android.pruebabbva.views

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.angelemv.android.pruebabbva.R
import com.angelemv.android.pruebabbva.viewmodel.ViewModel

@Composable
fun LoginScreen(nav: NavHostController, viewModel: ViewModel) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current
    val isFormValid = email.value.isNotEmpty() && password.value.isNotEmpty()
    val errorMessage = viewModel.errorMessage
    var showDialog by remember { mutableStateOf(false) }
    val loginResponse = viewModel.loginResponse.observeAsState()

    LaunchedEffect(loginResponse.value) {
        if (loginResponse.value != null) {
            viewModel.loginSucces(nav)
        }
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            showDialog = true
        }
    }
    CurvedBackgroundScreen {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_login),
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(60.dp))
            
            Text(
                "¡Bienvenid@!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Thin,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(3.dp)
            )
            GenericEditText(value = email, label = "Email", isEmail = true)
            GenericEditText(value = password, label = "Contraseña", isPassword = true)

            Button(
                onClick = {
                    viewModel.login("defaultuser", email.value, password.value)
                    Toast.makeText(context, "Iniciando sesión...", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x8F3F51B5),
                    contentColor = Color.White
                )
            ) {
                Text("Iniciar Sesión", fontSize = 18.sp)
            }

        }
    }
    if (showDialog) {
        ErrorLogin(message = errorMessage ?: "Error desconocido") {
            showDialog = false
            viewModel.clearError()
        }
    }
}


@Composable
fun CurvedBackgroundScreen(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = androidx.compose.ui.graphics.Path().apply {
                moveTo(0f, size.height)
                cubicTo(
                    size.width * 0.25f, size.height * 1f,
                    size.width * 0.90f, size.height * 0.90f,
                    size.width, 0f
                )
                lineTo(size.width, size.height)
                close()
            }
            drawPath(
                path = path,
                color = Color(0xFF4D5276)
            )
        }
        content()
    }
}

@Composable
fun ErrorLogin(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Error")
        },
        text = {
            Text(message)
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Aceptar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericEditText(
    value: MutableState<String>,
    label: String,
    isPassword: Boolean = false,
    isEmail: Boolean = false
) {
    TextField(
        value = value.value,
        onValueChange = { value.value = it },
        label = { Text(label, color = Color.White) },
        keyboardOptions = if (isEmail) {
            KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        } else {
            KeyboardOptions.Default
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0x8F3F51B5),
            cursorColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        nav = NavHostController(context = LocalContext.current),
        viewModel = ViewModel(context = LocalContext.current)
    )
}