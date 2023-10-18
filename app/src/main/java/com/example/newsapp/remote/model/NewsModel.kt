package com.example.newsapp.remote.model

data class NewsModel(
		val nextPage: String,
		val results: List<Article>,
		val status: String,
		val totalResults: Int,
)

data class Article(
		val article_id: String = "",
		val category: List<String> = emptyList(),
		val content: String = "",
		val country: List<String> = emptyList(),
		val creator: List<String>? = emptyList(),
		val description: String? = null,
		val image_url: String? = null,
		val keywords: List<String>? = emptyList(),
		val language: String = "",
		val link: String = "",
		val pubDate: String = "",
		val source_id: String = "",
		val source_priority: Int = 0,
		val title: String = "",
		val video_url: String? = null,
		val imageuri: String? = null,
)

