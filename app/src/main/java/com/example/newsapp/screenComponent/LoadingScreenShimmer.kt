package com.example.newsapp.screenComponent

import NewsAppTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.newsapp.remote.model.Article
import com.example.newsapp.util.shimmerEffect

@Composable
fun LoadingScreenShimmer(
		modifier: Modifier
) {
		Column(
				modifier = modifier
						.scrollable(
								state = rememberScrollState(),
								orientation = Orientation.Vertical,
								enabled = false,
						),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.Center,
		) {
				repeat(8) {
						LoadingShimmerItem()
				}
		}
}

//@Composable
//fun LoadingShimmerItem() {
//		Row(
//				modifier = Modifier
//						.heightIn(min = 100.dp, max = 120.dp),
//		) {
//				Spacer(
//						modifier = Modifier
//								.fillMaxSize()
//								.weight(0.3f)
//								.padding(5.dp)
//								.shimmerEffect(),
//				)
//				Column(
//						modifier = Modifier
//								.fillMaxSize()
//								.padding(vertical = 2.dp, horizontal = 4.dp)
//								.weight(0.7f),
//				) {
//						Spacer(
//								modifier = Modifier
//										.fillMaxWidth()
//										.padding(4.dp)
//										.shimmerEffect()
//										.weight(0.2f),
//						)
//						Spacer(
//								modifier = Modifier
//										.fillMaxWidth()
//										.padding(4.dp)
//										.shimmerEffect()
//										.weight(0.3f),
//						)
//						Spacer(
//								modifier = Modifier
//										.fillMaxWidth()
//										.padding(4.dp)
//										.shimmerEffect()
//										.weight(0.5f),
//						)
//				}
//		}
//}
@Composable
fun LoadingShimmerItem() {
		Surface(onClick = { }) {
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

						Spacer(
								modifier = Modifier
										.size(60.dp, 15.dp)
										.clip(RoundedCornerShape(15.dp))
										.shimmerEffect()
										.constrainAs(source) {
												top.linkTo(parent.top, margin = 5.dp)
												start.linkTo(startGuideLine)
										},
						)

						Spacer(
								modifier = Modifier
										.size(150.dp, 40.dp)
										.clip(RoundedCornerShape(15.dp))
										.shimmerEffect()
										.constrainAs(title) {
												top.linkTo(source.bottom, margin = 3.dp)
												start.linkTo(startGuideLine)
												end.linkTo(endGuideLine)
												width = Dimension.fillToConstraints
										}
						)

						Spacer(
								modifier = Modifier
										.size(150.dp, 40.dp)
										.clip(RoundedCornerShape(15.dp))
										.shimmerEffect()
										.constrainAs(description) {
												top.linkTo(title.bottom, margin = 3.dp)
												start.linkTo(startGuideLine)
												end.linkTo(endGuideLine, margin = 25.dp)
												bottom.linkTo(bottomGuideLine)
												width = Dimension.fillToConstraints
										},
						)

						Spacer(
								modifier = Modifier
										.size(80.dp, 20.dp)
										.clip(RoundedCornerShape(15.dp))
										.shimmerEffect()
										.constrainAs(pubDate) {
												top.linkTo(bottomGuideLine)
												start.linkTo(startGuideLine)
										}
						)

						Spacer(
								modifier = Modifier
										.clip(RoundedCornerShape(15.dp))
										.aspectRatio(1f)
										.shimmerEffect()
										.constrainAs(image) {
												top.linkTo(topGuideLine, margin = 3.dp)
												start.linkTo(endGuideLine, margin = 5.dp)
												end.linkTo(parent.end, margin = 15.dp)
												bottom.linkTo(bottomGuideLine)
												width = Dimension.fillToConstraints
										},
						)

						Spacer(
								modifier = Modifier
										.size(20.dp)
										.clip(RoundedCornerShape(10.dp))
										.shimmerEffect()
										.constrainAs(bookmarkIcon) {
												bottom.linkTo(parent.bottom, margin = 5.dp)
												top.linkTo(bottomGuideLine)
												end.linkTo(parent.absoluteRight, margin = 15.dp)
										},
						)

				}
		}
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsListItemss() {
		NewsAppTheme {
				LoadingShimmerItem()
		}
}