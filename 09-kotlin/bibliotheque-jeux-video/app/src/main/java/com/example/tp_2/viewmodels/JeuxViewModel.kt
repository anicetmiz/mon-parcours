package com.example.tp_2.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tp_2.data.CLE_TRI
import com.example.tp_2.data.Jeu
import com.example.tp_2.data.Statut
import com.example.tp_2.data.TypeTri
import com.example.tp_2.data.dataStore
import com.example.tp_2.data.listJeux
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JeuxViewModel(private val context: Context) : ViewModel() {
    private val _jeux = MutableStateFlow(listJeux)
    val jeux: StateFlow<List<Jeu>> = _jeux.asStateFlow()

    private val _triActuel = MutableStateFlow(TypeTri.PAR_TITRE)
    val triActuel: StateFlow<TypeTri> = _triActuel.asStateFlow()
    private val _statutActuel = MutableStateFlow(Statut.PAS_COMMENCE)
    val statutActuel: StateFlow<Statut> = _statutActuel.asStateFlow()

    init {
        viewModelScope.launch {
            context.dataStore.data.collect { prefs ->
                _triActuel.value = TypeTri.valueOf(prefs[CLE_TRI] ?: TypeTri.PAR_TITRE.name)
                trierListe()
            }
        }

    }

    fun changerTri(tri: TypeTri) {
        _triActuel.value = tri
        trierListe()

        viewModelScope.launch {
            context.dataStore.edit { prefs ->
                prefs[CLE_TRI] = tri.name
            }
        }
    }
    fun changerStatut(id:Int,statut: String){
        val statutEnum = Statut.valueOf(statut)
        _jeux.value = _jeux.value.map{
            jeu -> if (jeu.id == id){
                jeu.copy(statut = statut)
        }else{
            jeu
        }
        }
        _statutActuel.value = statutEnum
    }
    fun changerNoteJeu(id: Int,note:Int?){
        if(note != null){
        _jeux.value = _jeux.value.map{
            jeu -> if(jeu.id == id){
                jeu.copy(note = note)
        }else
            jeu
        }
    }else{
        _jeux.value.find { it.id == id }
    }
    }
    fun changerTempJeu(id: Int,temp:Int?){
        if (temp!== null) {
            _jeux.value = _jeux.value.map { jeu ->
                if (jeu.id == id) {
                    jeu.copy(tempsEstimer = temp)
                } else
                    jeu
            }
        }else{
            _jeux.value.find { it.id == id }
        }
    }
    @SuppressLint("FlowOperatorInvokedInComposition")
    @Composable
    fun getJeuParId(viewModel: JeuxViewModel, id: Int): Jeu? {
        val jeu by viewModel.jeux
            .map{jeux -> jeux.find { it.id == id }}
            .collectAsState(initial = null)
        return jeu
    }
    private fun trierListe() {
        _jeux.value = when (_triActuel.value) {
            TypeTri.PAR_TITRE -> _jeux.value.sortedBy{ it.titre }
            TypeTri.PAR_STATUT -> _jeux.value.sortedBy { it.statut }
            TypeTri.PAR_NOTE -> _jeux.value.sortedByDescending { it.note }
        }
    }
}

fun jeuViewModelFactory(context: Context) = viewModelFactory {
    initializer {
        JeuxViewModel(context.applicationContext)
    }
}