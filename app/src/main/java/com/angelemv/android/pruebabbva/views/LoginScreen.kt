package com.angelemv.android.pruebabbva.views

import UserPreferencesManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.angelemv.android.pruebabbva.R
import com.angelemv.android.pruebabbva.model.navigation.AppScreens
import com.angelemv.android.pruebabbva.viewmodel.ViewModel

@Composable
fun LoginScreen(nav: NavHostController, viewModel: ViewModel) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current
    val isFormValid = email.value.isNotEmpty() && password.value.isNotEmpty()
    val showErrorDialog = rememberSaveable { mutableStateOf(false) }
    val userPreferencesManager = remember { UserPreferencesManager(context) }
    val loginResponse = viewModel.loginResponse.observeAsState()


    if (showErrorDialog.value) {
        ErrorDialog(
            onDismiss = { showErrorDialog.value = false }
        )
    }

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

        GenericEditText(value = email, label = "Email", isEmail = true)
        GenericEditText(value = password, label = "Contraseña", isPassword = true)

        Button(
            onClick = {
                viewModel.login("defauluser",email.value, password.value)
                Toast.makeText(context, "Iniciando sesión...", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            enabled = isFormValid
        ) {
            Text("Iniciar Sesión", fontSize = 18.sp)
        }

        LaunchedEffect(loginResponse.value) {
            if (loginResponse.value != null) {
                viewModel.loginSucces(nav)
            }
        }

        viewModel.error.observeAsState().value?.let { error ->
            showErrorDialog.value = true
        }

    }
}

@Composable
fun ErrorDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Error de Inicio de Sesión") },
        text = { Text(text = "Hubo un error al intentar iniciar sesión. Por favor, inténtelo de nuevo.") },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Aceptar")
            }
        },
    )
}

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
        label = { Text(label) },
        keyboardOptions = if (isEmail) {
            KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        } else {
            KeyboardOptions.Default
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(nav = NavHostController(context = LocalContext.current), viewModel = ViewModel(context = LocalContext.current))
}