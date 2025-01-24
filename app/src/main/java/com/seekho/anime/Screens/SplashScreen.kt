package com.seekho.anime.Screens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.size

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seekho.anime.R


class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShowSplashScreen()
        }
        Handler().postDelayed({
            startActivity(Intent(this, AnimeListScreen::class.java))
            finish() // Finish the SplashScreenActivity so it's removed from the back stack
        }, 3000)
    }
}

@Preview(showBackground = true)
@Composable
fun ShowSplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfef9e7)),
        contentAlignment = Alignment.Center

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.anime),
                contentDescription = "Splash Image",
                modifier = Modifier.size(150.dp)
            )

            Text(
                text = "Anime",
                color = Color.Black,
                fontWeight = FontWeight.Bold,

                style = TextStyle(
                    fontFamily = FontFamily.Cursive,
                    fontSize = 50.sp

                )
            )
        }
    }
}




