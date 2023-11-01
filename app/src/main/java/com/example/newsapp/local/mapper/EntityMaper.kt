package com.example.newsapp.local.mapper

import com.example.newsapp.local.model.NewsEntity
import com.example.newsapp.remote.model.Article

class EntityMapper : EntityMapperImpl<NewsEntity,Article> {
		override fun entityToArticle(entitylist: List<NewsEntity>): List<Article> {
				val mutableList: MutableList<Article> = mutableListOf()
				entitylist.indices.forEach { index ->
						mutableList.add(
								Article(
										article_id = entitylist[index].articleId,
										category = emptyList(),
										content = entitylist[index].content,
										country = emptyList(),
										creator = emptyList(),
										description = entitylist[index].description,
										image_url = entitylist[index].imageUrl,
										keywords = emptyList(),
										language = "",
										link = entitylist[index].link,
										pubDate = entitylist[index].publishedAt,
										source_id = "",
										source_priority = 0,
										title = entitylist[index].title,
										video_url = "",
								)
						)
				}
				return mutableList
		}

		override fun articleToRoomEntity(article: Article): NewsEntity {
				return NewsEntity(
						id = 0,
						articleId = article.article_id,
						title = article.title,
						description = article.description!!,
						publishedAt = article.pubDate,
						content = article.content,
						imageUri = "",
						imageUrl = article.image_url!!,
						link = article.link,
						isRead = false,
				)
		}

}

interface EntityMapperImpl<T,E> {
		fun entityToArticle(entitylist: List<T>): List<E>
		fun articleToRoomEntity(article: E): T
}