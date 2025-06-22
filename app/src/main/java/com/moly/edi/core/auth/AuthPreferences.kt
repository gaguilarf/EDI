package com.moly.edi.core.auth

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "auth_prefs")

object AuthKeys {
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
}

class AuthPreferences(private val context: Context) {
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[AuthKeys.IS_LOGGED_IN] ?: false
    }

    suspend fun setLoggedIn(loggedIn: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[AuthKeys.IS_LOGGED_IN] = loggedIn
        }
    }
}
