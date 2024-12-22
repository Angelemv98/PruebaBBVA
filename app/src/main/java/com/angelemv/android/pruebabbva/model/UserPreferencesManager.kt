import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.angelemv.android.pruebabbva.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferencesManager(private val context: Context) {

    private val dataStore = context.dataStore

    private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    private val USER_NAME = stringPreferencesKey("user_name")
    private val USER_LAST_NAME = stringPreferencesKey("user_last_name")
    private val USER_ID = stringPreferencesKey("user_id")
    private val USER_GENDER = stringPreferencesKey("user_gender")
    private val USER_AGE = intPreferencesKey("user_age")

    val isLoggedIn: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }

    val userName: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[USER_NAME]
        }

    val userLastName: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[USER_LAST_NAME]
        }

    val userId: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[USER_ID]
        }

    val userGender: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[USER_GENDER]
        }

    val userAge: Flow<Int?> = dataStore.data
        .map { preferences ->
            preferences[USER_AGE]
        }

    suspend fun getUserData(): LoginResponse? {
        val preferences = dataStore.data.first()
        val name = preferences[USER_NAME]
        val lastName = preferences[USER_LAST_NAME]
        val id = preferences[USER_ID]
        val gender = preferences[USER_GENDER]
        val age = preferences[USER_AGE] ?: -1

        return if (name != null && lastName != null && id != null && gender != null) {
            LoginResponse(name, lastName, id, gender, age)
        } else {
            null
        }
    }

    suspend fun saveUserData(loginResponse: LoginResponse) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = loginResponse.name
            preferences[USER_LAST_NAME] = loginResponse.lastName
            preferences[USER_ID] = loginResponse.id
            preferences[USER_GENDER] = loginResponse.gender
            preferences[USER_AGE] = loginResponse.age
        }
    }

    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }

    suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = ""
            preferences[USER_LAST_NAME] = ""
            preferences[USER_ID] = ""
            preferences[USER_GENDER] = ""
            preferences[USER_AGE] = -1
        }
    }
}
