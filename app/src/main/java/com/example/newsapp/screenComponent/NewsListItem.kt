package com.example.newsapp.screenComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsapp.remote.model.Article
import com.example.newsapp.remote.model.Source
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsListItem(
		data: Article
) {
		Surface(Modifier.padding(10.dp)
		) {
				Row(
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.Center,
						modifier = Modifier
								.heightIn(min = 100.dp, max = 120.dp),
				) {
						AsyncImage(
								model = data.urlToImage,
								contentDescription = "",
								modifier = Modifier
										.width(90.dp)
										.clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)),
								contentScale = ContentScale.Crop,
								alignment = Alignment.Center,
						)

						Column(
								modifier = Modifier
										.fillMaxSize()
										.padding(vertical = 2.dp, horizontal = 4.dp),
								verticalArrangement = Arrangement.SpaceBetween
						) {
								data.publishedAt?.let {
										Text(
												text = it,
												fontSize = 13.sp,
												fontWeight = FontWeight.Light,
										)
								}
								data.title?.let {
										Text(
												text = it.substringBefore("-"),
												textAlign = TextAlign.Justify,
												fontWeight = FontWeight.Medium,
												fontSize = 15.sp,
										)
								}
								Spacer(modifier = Modifier.height(8.dp))
								data.description?.let {
										Text(
												text = it,
												fontWeight = FontWeight.Normal,
												maxLines = 3,
												textAlign = TextAlign.Justify,
												overflow = TextOverflow.Ellipsis,
										)
								}
						}
				}

		}

}

@Preview(showBackground = true)
@Composable
fun PreviewNewsListItem() {
		NewsAppTheme {
				NewsListItem(
						data = (Article(
								author = "Karishma Mehrotra, Anant Gupta",
								content = "Content",
								description = "The alleged assassination of a Sikh separatist is not unlike the plot of the spy thrillers showing Indian intelligence exploits that dominate the box office.",
								publishedAt = "2023/12/23",
								source = Source(id = "0", name = "The Washington Post"),
								title = "Indians express anger at Canada over backing Sikh separatists - The Washington Post",
								url = "String",
								urlToImage = "https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/5CRJQRFJ2JJM22KDHH7L6SQCEU_size-normalized.JPG&w=1440"
						))
				)
		}
}