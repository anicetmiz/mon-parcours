package com.example.tp_2.ui.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tp_2.R
import com.example.tp_2.data.TypeTri
import com.example.tp_2.ui.ui.components.BakeryTopAppBar
import com.example.tp_2.viewmodels.JeuxViewModel
import com.example.tp_2.viewmodels.jeuViewModelFactory


@Composable
fun ParametreScreen(viewModel: JeuxViewModel= viewModel(factory = jeuViewModelFactory(LocalContext.current)),
                onBack: () -> Unit){
    val tri by viewModel.triActuel.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            BakeryTopAppBar(
                titre = stringResource(R.string.parametre),
                showBackButton = true,
                onBack =  onBack
            )
        }
    ) {  innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FilterChip(
                        selected = tri == TypeTri.PAR_TITRE,
                        onClick = { viewModel.changerTri(TypeTri.PAR_TITRE) },
                        label = { Text(stringResource(R.string.par_titre_a_z)) },
                    )
                    FilterChip(
                        selected = tri == TypeTri.PAR_STATUT,
                        onClick = { viewModel.changerTri(TypeTri.PAR_STATUT) },
                        label = { Text(stringResource(R.string.par_statut)) },
                    )
                    FilterChip(
                        selected = tri == TypeTri.PAR_NOTE,
                        onClick = { viewModel.changerTri(TypeTri.PAR_NOTE) },
                        label = { Text(stringResource(R.string.par_note_desc)) },
                    )
                }
            }
        }
    }
}