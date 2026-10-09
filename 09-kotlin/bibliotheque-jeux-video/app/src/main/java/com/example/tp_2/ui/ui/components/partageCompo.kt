@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tp_2.ui.ui.components

import android.provider.CalendarContract
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tp_2.R

@Composable
fun BakeryTopAppBar(
    titre : String,
    onBack: (()-> Unit)?=null,
    onSettings: (() -> Unit)? = null,
    showBackButton: Boolean = false,
    action: @Composable RowScope.() -> Unit = {} ) {
    TopAppBar(

        title =  {
            Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                titre,
                color = MaterialTheme.colorScheme.onPrimary

            )
        } },
        navigationIcon = {
            if (showBackButton && onBack != null) {
                IconButton(onClick = { onBack() },
                    modifier = Modifier
                        .size(48.dp)
                        .background(color = Color.White, shape = CircleShape)) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.retour)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiaryFixedDim
        ),
        actions = {
            if (!showBackButton && onSettings != null) {
                IconButton(onClick = { onSettings() },
                    modifier = Modifier
                        .size(48.dp)
                        .background(color = Color.White, shape = CircleShape)
                    ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.param_tres)
                    )
                }
            }

            action()
        }
    )
}

