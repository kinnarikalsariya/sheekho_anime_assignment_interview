package com.seekho.anime.API

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.InstanceCreator
import com.seekho.anime.APIResponse.AnimeResponse
import com.seekho.anime.APIResponse.Items
import com.seekho.anime.APIResponse.Pagination
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    class AnimeResponseCreator : InstanceCreator<AnimeResponse> {
        override fun createInstance(type: java.lang.reflect.Type): AnimeResponse {
            return AnimeResponse(Pagination(0, false, 0, Items(0, 0, 0)), emptyList())
        }
    }

    private const val BASE_URL = "https://api.jikan.moe/v4/"

    val api: AnimeApi by lazy {
        val gson: Gson = GsonBuilder()
            .registerTypeAdapter(AnimeResponse::class.java, AnimeResponseCreator())
            .create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(AnimeApi::class.java)
    }
}