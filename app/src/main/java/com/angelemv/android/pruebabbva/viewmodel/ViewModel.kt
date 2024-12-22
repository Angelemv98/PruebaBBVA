package com.angelemv.android.pruebabbva.viewmodel

import UserPreferencesManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.angelemv.android.pruebabbva.model.network.DogResponse
import com.angelemv.android.pruebabbva.model.network.DogRetrofitInstance
import com.angelemv.android.pruebabbva.model.LoginRequest
import com.angelemv.android.pruebabbva.model.LoginResponse
import com.angelemv.android.pruebabbva.model.network.RetrofitInstance
import com.angelemv.android.pruebabbva.model.navigation.AppScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModel(context: Context) : ViewModel() {
    private val userPreferencesManager = UserPreferencesManager(context)

    val _dogImage = MutableLiveData<DogResponse?>()
    val dogImage: LiveData<DogResponse?> get() = _dogImage

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse: LiveData<LoginResponse?> get() = _loginResponse

    fun loadUserData() {
        viewModelScope.launch {
            val userData = userPreferencesManager.getUserData()
            _loginResponse.postValue(userData)
        }
    }

    fun login(username: String, email: String, password: String) {
        val request = LoginRequest(username, email, password)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.login(request)
                if (response.isSuccessful) {
                    response.body()?.let { loginResponse ->
                        // Guardamos los datos del login en DataStore
                        withContext(Dispatchers.Main) {
                            userPreferencesManager.saveUserData(loginResponse)
                            _loginResponse.postValue(loginResponse)
                            Log.d("LoginResponse", "Datos cargados: $loginResponse")
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _error.postValue("Error: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.postValue(e.message)
                }
            }
        }
    }

    fun getRandomDogImage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = DogRetrofitInstance.api.getRandomDogImage()
                if (response.isSuccessful) {
                    response.body()?.let { dogResponse ->
                        withContext(Dispatchers.Main) {
                            _dogImage.value = dogResponse
                            Log.d("dog", "Datos cargados: $dogResponse")
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _error.value = "Error: ${response.message()}"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }


    fun logout(nav: NavHostController) {
        viewModelScope.launch {
            userPreferencesManager.setLoggedIn(false)
            userPreferencesManager.clearUserData()
            nav.popBackStack()
            nav.navigate(AppScreens.LoginScreen.route)
        }
    }

    fun loginSucces(nav: NavHostController) {
        viewModelScope.launch {
            userPreferencesManager.setLoggedIn(true)
            nav.popBackStack()
            nav.navigate(AppScreens.DashBoardScreen.route)
        }
    }


}
