package com.halodoc.testproject.problems.news.model


import com.google.gson.annotations.SerializedName

data class Title(
        @SerializedName("fullyHighlighted")
        val fullyHighlighted: Boolean,
        @SerializedName("matchLevel")
        val matchLevel: String,
        @SerializedName("matchedWords")
        val matchedWords: List<String>,
        @SerializedName("value")
        val value: String
)