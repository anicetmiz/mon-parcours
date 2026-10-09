@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tp_2.ui.ui.screens

import android.widget.Space
import androidx.compose.foundation.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import com.example.tp_2.data.listJeux
import com.example.tp_2.data.Jeu
import com.example.tp_2.data.TypeTri
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.tp_2.data.Statut
import com.example.tp_2.ui.ui.components.BakeryTopAppBar
import com.example.tp_2.viewmodels.JeuxViewModel
import com.example.tp_2.viewmodels.jeuViewModelFactory
import kotlinx.serialization.Serializable






@Preview(showBackground = true)
@Composable
fun ListeTestScreen(){
   ListeScreen(
       onNavigateToParam = {},
       onNavigateToDetail = {}
   )
}

@Composable
fun ListeScreen(viewModel: JeuxViewModel= viewModel(factory = jeuViewModelFactory(LocalContext.current)),
                onNavigateToDetail: (Int) -> Unit,onNavigateToParam: () -> Unit){
   val jeux by viewModel.jeux.collectAsStateWithLifecycle()
   val tri by viewModel.triActuel.collectAsStateWithLifecycle()


    Scaffold(
        containerColor = MaterialTheme.colorScheme.tertiaryFixedDim,
        contentColor = MaterialTheme.colorScheme.onPrimaryFixed,
        topBar = {
            BakeryTopAppBar(
                titre = stringResource(com.example.tp_2.R.string.mes_jeux),
                showBackButton = false,
                onSettings = {onNavigateToParam()}
            )
        }
    ) {innerPadding ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.tertiaryFixedDim )
                ) {

                    Card(

                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = if(tri.name== TypeTri.PAR_TITRE.name){
                                stringResource(com.example.tp_2.R.string.tri_actif_par_titre)
                            }else if(tri.name==TypeTri.PAR_STATUT.name){
                                stringResource(com.example.tp_2.R.string.tri_actif_par_statut)
                            }else if(tri.name==TypeTri.PAR_NOTE.name){
                                stringResource(com.example.tp_2.R.string.tri_actif_par_note_descendante)
                            }else "",
                            modifier = Modifier
                                .padding(16.dp),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
            item {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.tertiaryFixedDim)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(com.example.tp_2.R.string.titre),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(0.5f)

                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = stringResource(com.example.tp_2.R.string.specificiter),
                            fontWeight = FontWeight.Bold
                        )
                    }



                }
            }
            itemsIndexed(
               items = jeux,
                key = {index, jeu -> jeu.id }
            ){   index, jeu ->
             Row(
                 modifier = Modifier
                     .fillMaxWidth()
                     .background(if ((index + 1) % 2 == 0) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.tertiaryFixed)
                     .padding(10.dp)
                     .clickable { onNavigateToDetail(jeu.id) },
                 verticalAlignment = Alignment.CenterVertically,
                 horizontalArrangement = Arrangement.SpaceBetween

             ) {
                 Text(
                     text = jeu.titre,
                     modifier = Modifier.fillMaxWidth(0.5f),
                     fontWeight = FontWeight.Bold
                 )
                 Column(
                     modifier = Modifier.fillMaxWidth(),
                     verticalArrangement = Arrangement.SpaceBetween
                 ) {


                 Text(
                     text = stringResource(com.example.tp_2.R.string.plateforme)+"${jeu.platforme}"
                 )
                 Text(
                     text = stringResource(com.example.tp_2.R.string.statut)+ if(jeu.statut == Statut.PAS_COMMENCE.name) {
                         stringResource(com.example.tp_2.R.string.pas_commencer1)
                     }else if(jeu.statut == Statut.EN_COURS.name){
                         stringResource(com.example.tp_2.R.string.en_cours1)
                     }else if(jeu.statut == Statut.TERMINE.name){
                         stringResource(com.example.tp_2.R.string.termin1)
                     }else{
                         jeu.statut
                     }
                 )
                 Text(
                     text = stringResource(com.example.tp_2.R.string.note)+ if(jeu.note!= null)"${jeu.note}/5" else stringResource(
                         com.example.tp_2.R.string.n_a
                     )
                 )
                 }

             }
            }
        }
    }
}

