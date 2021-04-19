package com.robby.moviecatalogue.data.model.response

import com.google.gson.annotations.SerializedName

data class GenreListResponse(

    @field:SerializedName("genres")
    val genres: List<GenresItem>
)

data class GenresItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)
