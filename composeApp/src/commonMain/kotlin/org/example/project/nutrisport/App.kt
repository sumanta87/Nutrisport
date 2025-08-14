package org.example.project.nutrisport

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.nutrisport.navigation.SetUpNavGraph
import com.nutrisport.shared.Constants
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var appReady by remember { mutableStateOf(false) }
    MaterialTheme {
        LaunchedEffect(Unit) {
            GoogleAuthProvider.create(credentials = GoogleAuthCredentials(serverId = Constants.WEB_CLIENT_ID))
            appReady = true
        }
        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = appReady
        ) {
            SetUpNavGraph()
        }

    }
}