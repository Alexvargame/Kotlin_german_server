package com.example.german_server.data.datastore



import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.authDataStore by preferencesDataStore(name = "auth_prefs")

object AuthKeys {
    val TOKEN = stringPreferencesKey("auth_token")
    val USER_ID = longPreferencesKey("user_id")
}

class AuthDataStore(private val context: Context) {

    suspend fun saveAuth(token: String, userId: Long) {
        context.authDataStore.edit { prefs ->
            prefs[AuthKeys.TOKEN] = token
            prefs[AuthKeys.USER_ID] = userId
        }
    }

    suspend fun isAuthorized(): Boolean {
        val prefs = context.authDataStore.data.first()
        return prefs[AuthKeys.TOKEN] != null
    }

    suspend fun logout() {
        context.authDataStore.edit {
            it.clear()
        }
    }
}
