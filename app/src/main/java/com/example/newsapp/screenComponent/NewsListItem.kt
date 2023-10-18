package com.example.newsapp.screenComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.remote.model.Article
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.util.noRippleClick
import com.example.newsapp.util.shimmerEffect

@Composable
fun NewsListItem(
		listData: Article,
		clickOnItem: (Article) -> Unit = {},
		isInReadLater: Boolean = false,
) {

		val imageId = if (isInReadLater) {
				R.drawable.iconbookmarkfill
		} else {
				R.drawable.iconbookmark
		}

		Surface(
				modifier = Modifier
						.padding(5.dp),
				onClick = {
						clickOnItem.invoke(listData)
				}
		) {

				Row(
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.Start,
				) {

						listData.image_url?.let {
								SubcomposeAsyncImage(
										model = ImageRequest.Builder(LocalContext.current).data(it).crossfade(true).build(),
										contentDescription = "",
										modifier = Modifier
												.clip(RoundedCornerShape(10.dp))
												.weight(0.3f)
												.aspectRatio(1f),
										contentScale = ContentScale.Crop,
										alignment = Alignment.Center,
										filterQuality = FilterQuality.Medium,
								) {
										val state = painter.state
										if (state is AsyncImagePainter.State.Loading) {
												Box(modifier = Modifier.shimmerEffect())
										} else {
												SubcomposeAsyncImageContent()
										}
								}
						}

						Column(
								modifier = Modifier
										.padding(vertical = 2.dp, horizontal = 4.dp)
										.weight(0.7f),
								verticalArrangement = Arrangement.SpaceBetween,
								horizontalAlignment = Alignment.CenterHorizontally,
						) {

								Row(
										modifier = Modifier
												.fillMaxWidth()
												.heightIn(min = 10.dp, max = 25.dp),
										horizontalArrangement = Arrangement.SpaceBetween,
										verticalAlignment = Alignment.CenterVertically,
								) {
										Text(
												text = listData.pubDate,
												fontSize = 13.sp,
												fontWeight = FontWeight.Light,
												modifier = Modifier
														.weight(0.3f),
										)
										Image(
												painter = painterResource(
														id = imageId
												),
												contentDescription = "",
												modifier = Modifier.size(15.dp),
										)
								}

								Text(
										text = listData.title,
										textAlign = TextAlign.Start,
										fontWeight = FontWeight.SemiBold,
										maxLines = 3,
										fontSize = 14.sp,
										lineHeight = 15.sp,
										modifier = Modifier
												.fillMaxWidth()
												.padding(2.dp),
								)

								listData.description?.let {
										Text(
												text = it,
												fontWeight = FontWeight.Normal,
												maxLines = 2,
												fontSize = 13.sp,
												lineHeight = 15.sp,
												textAlign = TextAlign.Justify,
												overflow = TextOverflow.Ellipsis,
												modifier = Modifier
														.fillMaxWidth()
														.padding(2.dp),
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
						listData = (Article(
								content = "Content",
								description = "The alleged assassination of a Sikh separatist is not unlike the plot of the spy thrillers showing Indian intelligence exploits that dominate the box office.",
								pubDate = "2023/12/23",
								title = "Indians express anger at Canada over backing Sikh separatists - The Washington Post",
								image_url = "https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/5CRJQRFJ2JJM22KDHH7L6SQCEU_size-normalized.JPG&w=1440"
						))
				)
		}
}