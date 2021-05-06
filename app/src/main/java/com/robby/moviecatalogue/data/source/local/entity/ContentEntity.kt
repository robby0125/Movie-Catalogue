package com.robby.moviecatalogue.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.robby.moviecatalogue.utils.ContentType

@Entity(tableName = "contents")
data class ContentEntity(
    @PrimaryKey
    @NonNull
    val id: Int,
    val type: ContentType,
    val title: String,
    val genre: String,
    val releaseDate: String,
    val voteAverage: Double,
    val originalLanguage: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    var isFavorite: Boolean = false
)
