package com.moly.edi.core.auth

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "auth_prefs")

object AuthKeys {
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val USER_EMAIL = stringPreferencesKey("user_email")
}

class AuthPreferences(private val context: Context) {
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[AuthKeys.IS_LOGGED_IN] ?: false
    }
    val userEmail: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[AuthKeys.USER_EMAIL]
    }

    suspend fun setLoggedIn(loggedIn: Boolean, email: String? = null) {
        context.dataStore.edit { prefs ->
            prefs[AuthKeys.IS_LOGGED_IN] = loggedIn
            if (loggedIn && email != null) {
                prefs[AuthKeys.USER_EMAIL] = email
            } else if (!loggedIn) {
                prefs.remove(AuthKeys.USER_EMAIL)
            }
        }
    }
}
