package com.seekho.anime.Screens

import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.seekho.anime.APIResponse.Anime
import com.seekho.anime.Model.AnimeViewModel
import com.seekho.anime.R

@Composable
fun DisplayAnimeList(viewModel: AnimeViewModel) {
    val animeList by viewModel.animeList
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchTopAnime()
    }

    Log.e("TAG", "DisplayAnimeList: " + animeList.size)

    if (animeList.isEmpty()) {
        Box(
            modifier = androidx.compose.ui.Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                strokeWidth = 10.dp,
                strokeCap = StrokeCap.Round
            )
        }
    } else {
        LazyColumn(
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .padding(top = 90.dp),
            contentPadding = PaddingValues(16.dp)

        ) {
            items(animeList) { anime ->
                AnimeItem(anime) { id ->

                    Toast.makeText(context, "get id = ${id}", Toast.LENGTH_LONG).show()
                    val intent = Intent(context, AnimeDetailListActivity::class.java).apply {
                        putExtra("ANIME_ID", id) // Pass the ID to the new activity
                    }
                    context.startActivity(intent)
                }
            }
        }
    }


}

@Composable
fun AnimeItem(anime: Anime, onClick: (String) -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable { onClick(anime.mal_id.toString()) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Row(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display Image
            AndroidView(
                factory = { context ->
                    ImageView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(150, 200)
                    }
                },
                update = { imageView ->
                    Glide.with(context)
                        .asBitmap()
                        .load(anime.images.jpg.image_url)
                        .apply(RequestOptions().error(R.drawable.ic_launcher_background)) // Optional: Set an error image
                        .into(imageView)
                }
            )
            // Display anime details
            Column(
                modifier = androidx.compose.ui.Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = anime.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 18.sp
                )
                Spacer(modifier = androidx.compose.ui.Modifier.height(8.dp))
                Text(
                    text = "Episodes: ${anime.episodes}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = androidx.compose.ui.Modifier.height(4.dp))
                Text(text = "Rating: ${anime.score}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
