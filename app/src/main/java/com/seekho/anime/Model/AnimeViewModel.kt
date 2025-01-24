package com.seekho.anime.Model


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.seekho.anime.API.RetrofitClient
import com.seekho.anime.APIResponse.Anime
import com.seekho.anime.APIResponse.AnimeData
import com.seekho.anime.APIResponse.AnimeDetailResponse
import com.seekho.anime.APIResponse.AnimeResponse
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response

class AnimeViewModel : ViewModel() {

    var animeList = mutableStateOf<List<Anime>>(emptyList())
    private var _animeDetail = MutableLiveData<AnimeData?>()
    val animeDetail: MutableLiveData<AnimeData?> get() = _animeDetail


    fun fetchTopAnime() {
        RetrofitClient.api.getTopAnime().enqueue(object : Callback<AnimeResponse> {
            override fun onResponse(call: Call<AnimeResponse>, response: Response<AnimeResponse>) {
                if (response.isSuccessful) {
                    animeList.value = response.body()?.data ?: emptyList()
                } else {
                    println("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<AnimeResponse>, t: Throwable) {
                println("Failure: ${t.message}")
            }
        })
    }


    fun fetchAnimeDetails(animeId: Int) {
        viewModelScope.launch {
            RetrofitClient.api.getAnimeDetail(animeId)
                .enqueue(object : Callback<AnimeDetailResponse> {
                    override fun onResponse(
                        call: Call<AnimeDetailResponse>,
                        response: Response<AnimeDetailResponse>
                    ) {
                        if (response.isSuccessful) {
                            _animeDetail.value = response.body()?.data
                            Log.e("KKKKK==>", "onResponse: ${_animeDetail.value}")
                        } else {
                            _animeDetail.value = null
                        }
                    }

                    override fun onFailure(call: Call<AnimeDetailResponse>, t: Throwable) {
                        _animeDetail.value = null
                    }
                })
        }
    }


}