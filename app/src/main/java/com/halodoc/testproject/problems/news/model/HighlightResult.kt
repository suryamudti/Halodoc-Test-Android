package com.halodoc.testproject.problems.news.model


import com.google.gson.annotations.SerializedName

data class HighlightResult(
        @SerializedName("author")
        val author: Author,
        @SerializedName("title")
        val title: Title,
        @SerializedName("url")
        val url: Url
)