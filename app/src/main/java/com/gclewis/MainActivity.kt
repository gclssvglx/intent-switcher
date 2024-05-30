package com.gclewis

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gclewis.ui.theme.IntentSwitcherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntentSwitcherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextButton(onClick = { openSecondaryActivity() }) {
                            Text(text = "Open Secondary Activity")
                        }
                        TextButton(onClick = { openYouTube() }) {
                            Text(text = "Open YouTube")
                        }
                        TextButton(onClick = { sendEmail() }) {
                            Text(text = "Send Email")
                        }
                        TextButton(onClick = { openGovUk() }) {
                            Text(text = "Open GOV.UK")
                        }
                    }
                }
            }
        }
    }

    private fun openSecondaryActivity() {
        Intent(applicationContext, SecondActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun openYouTube() {
        Intent(Intent.ACTION_MAIN).also {
            it.`package` = "com.google.android.youtube"
            try {
                startActivity(it)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("test@test.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Subject of email")
            putExtra(Intent.EXTRA_TEXT, "Body of email")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Send email"))
        }
    }

    private fun openGovUk() {
        val url = "https://www.gov.uk"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
