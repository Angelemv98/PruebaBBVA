package com.angelemv.android.pruebabbva.views

import UserPreferencesManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.angelemv.android.pruebabbva.R
import com.angelemv.android.pruebabbva.model.navigation.AppScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(nav: NavHostController, userPreferencesManager: UserPreferencesManager) {
    val isLoggedIn = userPreferencesManager.isLoggedIn.collectAsState(initial = false)

    LaunchedEffect(key1 = isLoggedIn.value) {
        delay(2000)
        if (isLoggedIn.value) {
            nav.popBackStack()
            nav.navigate(AppScreens.DashBoardScreen.route)
        } else {
            nav.popBackStack()
            nav.navigate(AppScreens.LoginScreen.route)
        }
    }
    Splash()
}



@Composable
fun Splash() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash),
            contentDescription = "App Logo",
            modifier = Modifier.size(180.dp)
        )
        Spacer(modifier = Modifier.height(46.dp))
        Text(
            " Prueba BBVA \n Morales Varela Angel Ernesto",
            fontSize = 30.sp,
            fontWeight = FontWeight.Thin,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(3.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Splash()
}