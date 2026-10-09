@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tp_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.R
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.tp_2.ui.ui.screens.ListeScreen
import com.example.tp_2.ui.ui.theme.Tp_2Theme
import kotlinx.serialization.Serializable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.tp_2.ui.ui.screens.DetailScreen
import com.example.tp_2.ui.ui.screens.ParametreScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tp_2Theme {
              JeuApp()
            }
        }
    }
}

@Serializable
object ListeDestination : NavKey

@Serializable
data class DetailDestination(val id: Int) : NavKey

@Serializable
object ParametreDestination : NavKey

@Preview(showBackground = true)
@Composable
fun JeuApp() {
    val backStack = rememberNavBackStack(ListeDestination)
    NavDisplay(
        backStack = backStack,
        onBack = {backStack.removeLastOrNull()},
        entryProvider  = entryProvider{
            entry<ListeDestination>{
                ListeScreen(onNavigateToParam = {backStack.add(ParametreDestination)},
                    onNavigateToDetail = {id -> backStack.add(DetailDestination(id))})
            }

            entry<DetailDestination>{ jeu ->
                DetailScreen(onBack = {backStack.removeLastOrNull()}, id = jeu.id)
            }

            entry<ParametreDestination>{
                ParametreScreen(onBack = {backStack.removeLastOrNull()})
            }
        }
    )
}

