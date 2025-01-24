package com.seekho.anime.Screens


import android.view.ViewGroup
import android.widget.ImageView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.seekho.anime.APIResponse.AnimeData
import com.seekho.anime.R


@Composable
fun DisplayAnimeDetails(animeDetail: AnimeData) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp)
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = animeDetail.title,
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center // Center the text
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (!animeDetail.trailer.youtube_id.isNullOrEmpty()) {
            YouTubePlayerScreen(
                animeDetail.trailer.youtube_id,
                "AIzaSyBIZvm3en9wXnQqlzC4RDJfjGnwFSDyw_c"
            )
        } else {

            AndroidView(
                factory = { context ->
                    ImageView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(500, 500)
                        scaleType = ImageView.ScaleType.FIT_CENTER
                    }
                },
                update = { imageView ->
                    Glide.with(context)
                        .asBitmap()
                        .load(animeDetail.trailer.images.image_url)
                        .apply(RequestOptions().error(R.drawable.anime))
                        .into(imageView)
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = animeDetail.synopsis,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Genres: ${animeDetail.genres.joinToString { it.name }}")

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Main Cast: ${animeDetail.broadcast.day}")

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Episodes: ${animeDetail.episodes}")

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Rating: ${animeDetail.rating}")
    }

}

@Composable
fun YouTubePlayerScreen(videoId: String, apiKey: String) {
    AndroidView(
        factory = {
            var view = YouTubePlayerView(it)
            val fragment = view.addYouTubePlayerListener(
                object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
                }
            )
            view
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}