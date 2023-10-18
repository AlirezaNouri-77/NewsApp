package com.example.newsapp.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.navigation.encodeStringNavigation
import com.example.newsapp.remote.model.Article
import com.example.newsapp.remote.model.BaseViewModelContract

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
		localViewModel: LocalViewModel,
		content: String,
		title: String,
		imageurl: String,
		pubDate: String,
		articleId: String,
		link: String,
		description: String,
) {

		val isSavedInDb = localViewModel.isArticleInDb(articleId)

		var imageState by remember {
				mutableIntStateOf(0)
		}

		imageState = if (isSavedInDb) {
				R.drawable.iconbookmarkfill
		} else {
				R.drawable.iconbookmark
		}

		LazyColumn(
				modifier = Modifier
						.fillMaxSize(),
		) {

				stickyHeader {
						Row(
								modifier = Modifier
										.fillMaxWidth()
										.background(Color.White),
								horizontalArrangement = Arrangement.SpaceBetween,
								verticalAlignment = Alignment.CenterVertically,
						) {
								Button(
										onClick = { /*TODO*/ },
										colors = ButtonDefaults.buttonColors(
												containerColor = Color.Transparent,
										),
								) {
										Image(
												painter = painterResource(id = R.drawable.iconback),
												contentDescription = "",
												modifier = Modifier.size(25.dp),
										)
								}
								Row {
										Button(
												onClick = {
														if (!isSavedInDb) {
																localViewModel.setBaseEvent(
																		BaseViewModelContract.BaseEvent.InsertDataToDb(
																				Article(
																						article_id = articleId,
																						title = title,
																						description = description,
																						pubDate = pubDate,
																						content = content,
																						imageuri = "",
																						image_url = imageurl,
																						link = link,
																				)
																		)
																)
																imageState = R.drawable.iconbookmarkfill
														} else {
																localViewModel.setBaseEvent(
																		BaseViewModelContract.BaseEvent.DeleteDataToDb(
																				articleID = articleId
																		)
																)
																imageState = R.drawable.iconbookmark
														}
												},
												colors = ButtonDefaults.buttonColors(
														containerColor = Color.Transparent,
												)
										) {
												Image(
														painter = painterResource(id = imageState),
														contentDescription = "",
														modifier = Modifier.size(25.dp),
												)
										}
								}
						}
				}

				item {
						if (imageurl.isNotBlank()) {
								AsyncImage(
										model = imageurl,
										contentDescription = "",
										modifier = Modifier
												.fillMaxWidth()
												.height(360.dp),
										alignment = Alignment.TopCenter,
										contentScale = ContentScale.FillHeight,
								)
						}
				}

				item {
						Text(
								text = pubDate.encodeStringNavigation(),
								fontWeight = FontWeight.Medium,
								fontSize = 14.sp,
								modifier = Modifier.padding(horizontal = 5.dp)
						)
						Spacer(
								modifier = Modifier
										.height(4.dp),
						)
						Text(
								text = title,
								fontWeight = FontWeight.SemiBold,
								fontSize = 20.sp,
								textAlign = TextAlign.Start,
								modifier = Modifier.padding(horizontal = 5.dp),
						)
						Divider(
								thickness = 1.dp,
								color = Color.Black.copy(alpha = 0.7f),
								modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
						)
						Text(
								text = content ,
								fontSize = 14.sp,
								fontWeight = FontWeight.Medium,
								textAlign = TextAlign.Start,
								lineHeight = 24.sp,
								modifier = Modifier.padding(horizontal = 5.dp, vertical = 7.dp),
						)
				}

		}

}