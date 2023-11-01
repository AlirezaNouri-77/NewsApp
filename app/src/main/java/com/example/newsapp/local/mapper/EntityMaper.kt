package com.example.newsapp.local.mapper

import com.example.newsapp.local.model.NewsEntity
import com.example.newsapp.remote.model.Article

class EntityMapper : EntityMapperImpl<NewsEntity, Article> {
		override fun entityToArticle(entitylist: List<NewsEntity>): List<Article> {
				val mutableList: MutableList<Article> = mutableListOf()
				entitylist.indices.forEach { index ->
						mutableList.add(
								Article(
										article_id = entitylist[index].articleId,
										content = entitylist[index].content,
										description = entitylist[index].description,
										image_url = entitylist[index].imageUrl ?:"",
										link = entitylist[index].link,
										pubDate = entitylist[index].publishedAt,
										source_id = entitylist[index].source,
										title = entitylist[index].title,
								)
						)
				}
				return mutableList
		}

		override fun articleToRoomEntity(article: Article): NewsEntity {
				return NewsEntity(
						articleId = article.article_id,
						title = article.title,
						description = article.description ?: "",
						publishedAt = article.pubDate,
						content = article.content,
						source = article.source_id,
						imageUrl = article.image_url ?: "",
						link = article.link,
				)
		}

}

interface EntityMapperImpl<T, E> {
		fun entityToArticle(entitylist: List<T>): List<E>
		fun articleToRoomEntity(article: E): T
}