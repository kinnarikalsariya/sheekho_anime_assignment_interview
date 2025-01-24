package com.seekho.anime.API

import com.seekho.anime.APIResponse.AnimeDetailResponse
import com.seekho.anime.APIResponse.AnimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApi {

    @GET("top/anime")
    fun getTopAnime(): Call<AnimeResponse>

    @GET("anime/{anime_id}")
    fun getAnimeDetail(@Path("anime_id") animeId: Int): Call<AnimeDetailResponse>


}

