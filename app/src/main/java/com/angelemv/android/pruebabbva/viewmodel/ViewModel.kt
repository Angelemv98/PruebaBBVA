package com.angelemv.android.pruebabbva.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelemv.android.pruebabbva.model.ApiService
import com.angelemv.android.pruebabbva.model.LoginRequest
import com.angelemv.android.pruebabbva.model.LoginResponse
import com.angelemv.android.pruebabbva.model.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModel : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse: LiveData<LoginResponse?> get() = _loginResponse

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun login(username: String, email: String, password: String) {
        val request = LoginRequest(username, email, password)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.login(request)
                withContext(Dispatchers.Main) {
                    _loginResponse.value = response.body()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }
}
