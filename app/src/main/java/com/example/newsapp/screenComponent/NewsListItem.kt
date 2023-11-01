package com.example.newsapp.screenComponent

import NewsAppTheme
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.remote.model.Article
import com.example.newsapp.util.shimmerEffect

@Composable
fun NewsListItem(
		data: Article,
		clickOnItem: (Article) -> Unit = {},
		isBookmarked: Boolean = true,
		showBookmarkIcon: Boolean = false,
) {

		val icon = if (isBookmarked) {
				R.drawable.iconbookmarkfill
		} else {
				R.drawable.iconbookmark
		}

		Surface(onClick = { clickOnItem.invoke(data) }) {
				ConstraintLayout(
						Modifier
								.fillMaxWidth(),
				) {

						val (title,
								description,
								image,
								bookmarkIcon,
								source,
								pubDate) = createRefs()

						val startGuideLine = createGuidelineFromStart(10.dp)
						val absouluteEnd = createGuidelineFromAbsoluteRight(10.dp)
						val topGuideLine = createGuidelineFromTop(10.dp)
						val bottomGuideLine = createGuidelineFromBottom(0.2f)
						val endGuideLine = createGuidelineFromEnd(0.3f)


						val constrait = if (!data.image_url.isNullOrBlank()) {
								endGuideLine
						} else {
								absouluteEnd
						}

						Text(
								text = data.source_id.replace("_", " "),
								textAlign = TextAlign.Start,
								color = MaterialTheme.colorScheme.primary,
								style = MaterialTheme.typography.headlineMedium,
								maxLines = 4,
								modifier = Modifier.constrainAs(source) {
										top.linkTo(parent.top, margin = 5.dp)
										start.linkTo(startGuideLine)
								},
						)

						Text(
								text = data.title,
								textAlign = TextAlign.Start,
								style = MaterialTheme.typography.titleMedium,
								modifier = Modifier.constrainAs(title) {
										top.linkTo(source.bottom)
										end.linkTo(constrait, margin = 5.dp)
										start.linkTo(startGuideLine)
										width = Dimension.fillToConstraints
								}
						)

						Text(
								text = data.content,
								maxLines = 3,
								textAlign = TextAlign.Start,
								style = MaterialTheme.typography.titleSmall,
								overflow = TextOverflow.Ellipsis,
								modifier = Modifier.constrainAs(description) {
										top.linkTo(title.bottom)
										start.linkTo(title.start)
										bottom.linkTo(bottomGuideLine)
										end.linkTo(constrait, margin = 5.dp)
										width = Dimension.fillToConstraints
								},
						)

						Text(
								text = data.pubDate,
								style = MaterialTheme.typography.titleSmall,
								modifier = Modifier
										.constrainAs(pubDate) {
												bottom.linkTo(parent.bottom)
												top.linkTo(bottomGuideLine)
												start.linkTo(startGuideLine)
										}
						)


						if (!data.image_url.isNullOrEmpty()) {

								val url = if (data.image_url.last() == '/') {
										data.image_url.substring(data.image_url.lastIndex)
								} else {
										data.image_url
								}

								SubcomposeAsyncImage(
										model = ImageRequest.Builder(LocalContext.current)
												.data(url)
												.crossfade(true)
												.build(),
										contentDescription = "",
										modifier = Modifier
												.clip(RoundedCornerShape(15.dp))
												.aspectRatio(1f)
												.constrainAs(image) {
														top.linkTo(topGuideLine, margin = 3.dp)
														start.linkTo(endGuideLine, margin = 5.dp)
														end.linkTo(parent.end, margin = 15.dp)
														bottom.linkTo(bottomGuideLine)
														width = Dimension.fillToConstraints
												},
										contentScale = ContentScale.Crop,
										alignment = Alignment.Center,
										filterQuality = FilterQuality.Medium,
								) {
										when (painter.state) {
												is AsyncImagePainter.State.Loading -> {
														Spacer(modifier = Modifier.shimmerEffect())
												}
												is AsyncImagePainter.State.Success -> {
														SubcomposeAsyncImageContent()
												}
												else -> {
														SubcomposeAsyncImageContent(
																painter = painterResource(id = R.drawable.noimageplaceholder),
																contentScale = ContentScale.Fit,
																colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
														)
												}
										}
								}
						}
						if (showBookmarkIcon) {
								Image(
										painter = painterResource(
												id = icon
										),
										contentDescription = "",
										modifier = Modifier
												.size(18.dp)
												.background(
														color = MaterialTheme.colorScheme.primaryContainer,
														shape = CircleShape,
												)
												.constrainAs(bookmarkIcon) {
														bottom.linkTo(parent.bottom, margin = 3.dp)
														top.linkTo(bottomGuideLine)
														end.linkTo(parent.absoluteRight, margin = 15.dp)
												},
										colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimaryContainer),
								)
						}
				}
		}
}

@Preview(showBackground = true, wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE)
@Composable
fun PreviewNewsListItem() {
		NewsAppTheme {
				NewsListItem(
						data = (Article(
								content = "The alleged assassination of a Sikh separatist is not unlike the plot of the spy The alleged assassination of a Sikh separatist is not unlike the plot of the spy ",
								source_id = "BBC",
								description = "The alleged assassination of a Sikh separatist is not unlike the plot of the spy thrillers showing Indian intelligence exploits that dominate the box office.",
								pubDate = "2023/12/23",
								title = "Indians express anger at Canada over backing Sikh separatists - The Washington Post",
								image_url = "https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/5CRJQRFJ2JJM22KDHH7L6SQCEU_size-normalized.JPG&w=1440"
						))
				)
		}
}