package com.seekho.anime.APIResponse

data class AnimeResponse(
    val pagination: Pagination,
    val data: List<Anime>
)

data class Pagination(
    val last_visible_page: Int,
    val has_next_page: Boolean,
    val current_page: Int,
    val items: Items
)

data class Items(
    val count: Int,
    val total: Int,
    val per_page: Int
)

data class Anime(
    val mal_id: Int,
    val url: String,
    val images: Images,
    val title: String,
    val episodes: Int,
    val score: Double
)

data class Images(
    val jpg: ImageDetails
)

data class ImageDetails(
    val image_url: String,
    val small_image_url: String,
    val large_image_url: String
)