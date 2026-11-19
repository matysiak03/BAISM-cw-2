
package com.example.passwordguard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.passwordguard.ui.theme.PasswordGuardTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PasswordGuardTheme {
                PasswordGuardScreen()
            }
        }
    }
}

@Composable
fun PasswordGuardScreen() {

    var password by remember { mutableStateOf("") }

    val result = PasswordAnalyzer.analyze(password)

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "PasswordGuard",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = "Sprawdz sile swojego hasla",
                style = MaterialTheme.typography.bodyLarge
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Text("Wprowadz haslo")
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Sila hasla: ${result.strength}",
                style = MaterialTheme.typography.titleMedium
            )

            LinearProgressIndicator(
                progress = { result.score / 100f },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Wynik: ${result.score}/100",
                style = MaterialTheme.typography.bodyLarge
            )

            HorizontalDivider()

            Text(
                text = "Wskazowki bezpieczenstwa",
                style = MaterialTheme.typography.titleMedium
            )

            if (result.suggestions.isEmpty()) {
                Text(
                    text = "Haslo spelnia wszystkie sprawdzane kryteria."
                )
            } else {
                result.suggestions.forEach { suggestion ->
                    Text(text = "- $suggestion")
                }
            }

            Text(
                text = "Analiza odbywa sie lokalnie. " +
                        "Haslo nie jest wysylane na serwer.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
