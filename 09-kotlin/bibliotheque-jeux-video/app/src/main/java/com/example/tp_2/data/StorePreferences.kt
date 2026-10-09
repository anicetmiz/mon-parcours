package com.example.tp_2.data

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore("preferences")

val CLE_TRI = stringPreferencesKey("tri")