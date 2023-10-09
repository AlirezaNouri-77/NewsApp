package com.example.newsapp.screenComponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsapp.remote.model.Article
import com.example.newsapp.util.shimmerEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListItem(
		data: Article,
		clickOnItem: (Article) -> Unit = {}
) {

		Card(
				modifier = Modifier
						.heightIn(min = 100.dp, max = 140.dp)
						.padding(5.dp),
				elevation = CardDefaults.elevatedCardElevation(
						defaultElevation = 3.dp,
						pressedElevation = 7.dp
				),
				colors = CardDefaults.cardColors(
						containerColor = Color(0xFFF7F7F7)
				),
				onClick = {
						clickOnItem.invoke(data)
				}
		) {

				Row(
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.Center,
//						modifier = Modifier.clickable {
//
//						},
				) {

						if (!data.image_url.isNullOrEmpty()) {
								AsyncImage(
										model = data.image_url,
										contentDescription = "",
										modifier = Modifier
												.fillMaxSize()
												.weight(0.2f)
												.clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)),
										contentScale = ContentScale.Crop,
										alignment = Alignment.Center,
								)
						}

						Column(
								modifier = Modifier
										.fillMaxSize()
										.padding(vertical = 2.dp, horizontal = 4.dp)
										.weight(0.7f),
								verticalArrangement = Arrangement.SpaceBetween
						) {

								Text(
										text = data.pubDate,
										fontSize = 13.sp,
										fontWeight = FontWeight.Light,
										modifier = Modifier
												.fillMaxSize()
												.padding(2.dp)
												.weight(0.2f),
								)

								Text(
										text = data.title,
										textAlign = TextAlign.Start,
										fontWeight = FontWeight.Medium,
										maxLines = 3,
										fontSize = 15.sp,
										modifier = Modifier
												.fillMaxWidth()
												.padding(3.dp)
												.weight(0.4f),
								)

								data.description?.let {
										Text(
												text = it,
												fontWeight = FontWeight.Normal,
												maxLines = 3,
												textAlign = TextAlign.Justify,
												overflow = TextOverflow.Ellipsis,
												modifier = Modifier
														.fillMaxWidth()
														.padding(3.dp)
														.weight(0.5f),

												)
								}

						}

				}
		}

}

