package com.example.tp_2.ui.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tp_2.R
import com.example.tp_2.data.Statut
import com.example.tp_2.data.TypeTri
import com.example.tp_2.ui.ui.components.BakeryTopAppBar
import com.example.tp_2.viewmodels.JeuxViewModel
import com.example.tp_2.viewmodels.jeuViewModelFactory


//@Preview(showBackground = true)
//@Composable
//fun DetailScreenpreview(){
//    DetailScreen(
//        onBack = {},
//         id=7
//   )
//}
@Composable
fun DetailScreen(viewModel: JeuxViewModel= viewModel(factory = jeuViewModelFactory(LocalContext.current)),
               id: Int,onBack: () -> Unit){
    val statut by viewModel.statutActuel.collectAsStateWithLifecycle()
    val jeu = viewModel.getJeuParId(viewModel,id)
    var note by remember { mutableStateOf(jeu?.note?.toFloat() ?: 1f) }
    if (jeu?.note != null){
        note = jeu.note.toFloat()
    }
    var temp by remember {mutableStateOf(jeu?.tempsEstimer.toString())}
      if(jeu?.tempsEstimer != null){
        temp = jeu.tempsEstimer.toString()
      }else{
          temp = 0.toString()
      }
    Scaffold(
        topBar = {
            BakeryTopAppBar(
                titre = stringResource(R.string.detail),
                showBackButton = true,
                onBack =  onBack
            )
        }
    ) {  innerPadding ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(innerPadding)) {

            item {
                Spacer(modifier = Modifier.height(18.dp))
            }
            item {
                Text(stringResource(R.string.changer_statut_du_jeu))
            }
            item {
                Text("${jeu?.titre}",
                    textAlign = TextAlign.Center)
            }
            item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        FilterChip(
                            selected = statut == Statut.PAS_COMMENCE,
                            onClick = { viewModel.changerStatut(id,Statut.PAS_COMMENCE.name) },
                            label = { Text(stringResource(R.string.pas_commencer)) },
                        )
                        FilterChip(
                            selected = statut == Statut.EN_COURS,
                            onClick = {  viewModel.changerStatut(id,Statut.EN_COURS.name) },
                            label = { Text(stringResource(R.string.en_cours)) },
                        )
                        FilterChip(
                            selected = statut == Statut.TERMINE,
                            onClick = {  viewModel.changerStatut(id,Statut.TERMINE.name) },
                            label = { Text(stringResource(R.string.termine)) },
                        )
                    }
            }
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            item {
                if (jeu?.note != null) {
                    Text(stringResource(R.string.modifier_la_note_du_jeu))
                }else{
                    Text(stringResource(R.string.aucune_note_pour_ce_jeu))
                }
            }
            item {
                if (jeu?.note != null) {
                    Text("${note.toInt()}")
                }else{
                    Text(stringResource(R.string.mettez_en_une))
                }
            }
            item {
                Slider(
                    value = note,
                    onValueChange = {n->
                        note = n
                        jeu?.let {
                            viewModel.changerNoteJeu(id=it.id, note = n.toInt())   }
                    },
                    valueRange = 1f..5f,
                    steps = 3,
                    modifier = Modifier.padding(15.dp)
                )
            }
            item {
                if (jeu?.note == null) {
                    Text("${note.toInt()}")
                }
            }
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            item {
                if(jeu?.tempsEstimer != null) {
                    Text(stringResource(R.string.modifier_le_temp_estimer_du_jeu))
                }else{
                    Text(stringResource(R.string.aucun_temp_estimer_pour_ce_jeu))
                }
            }
            item {
                if(jeu?.tempsEstimer != null) {
                    Text("${temp.toInt()} H")
                }else{
                    Text(stringResource(R.string.combien_avez_vous_fait))
                }
            }

            item {
                OutlinedTextField(
                    value = temp,
                    onValueChange = { n->
                        temp = n
                        jeu?.let {
                            viewModel.changerTempJeu(id=it.id, temp = n.toInt())   }
                    },
                    label = {Text(stringResource(R.string.temp_estimer))}
                )
            }


        }
    }
}