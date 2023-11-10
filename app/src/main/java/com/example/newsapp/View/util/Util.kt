package com.example.newsapp.View.util

import NewsAppTheme
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.Domain.Model.Article
import com.example.newsapp.Domain.Model.SettingModel
import com.example.newsapp.View.navigation.encodeStringNavigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

inline fun Modifier.noRippleClick(crossinline onClick: () -> Unit): Modifier = composed {
  clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
	onClick()
  }
}

fun Modifier.shimmerEffect(): Modifier = composed {
  
  var size by remember {
	mutableStateOf(IntSize.Zero)
  }
  
  val transition = rememberInfiniteTransition(label = "")
  
  val transitionFloat by transition.animateFloat(
	initialValue = 0f,
	targetValue = size.width.toFloat() + size.width.toFloat() / 2,
	animationSpec = infiniteRepeatable(
	  tween(durationMillis = 1000),
	  repeatMode = RepeatMode.Restart,
	),
	label = "shimmerEffect",
  )
  
  background(
	Brush.horizontalGradient(
	  colors = listOf(
		MaterialTheme.colorScheme.background.copy(0.6f),
		MaterialTheme.colorScheme.onBackground.copy(0.4f),
		MaterialTheme.colorScheme.background.copy(0.6f),
	  ),
	  startX = transitionFloat - size.width / 4,
	  endX = transitionFloat * 3f,
	),
  ).onGloballyPositioned { size = it.size }
  
}

suspend inline fun <T> onIO(crossinline action: () -> T): T {
  return withContext(Dispatchers.IO) {
	action()
  }
}

fun List<SettingModel>.convertToStringForUrl(): String {
  val output = StringBuilder()
  this.forEachIndexed { index, item ->
	output.append(item.code)
	if (this.size != index + 1) {
	  output.append(",")
	}
  }
  return output.toString()
}

@Composable
fun LazyListState.isBottomList(): State<Boolean> {
  return remember {
	derivedStateOf(policy = referentialEqualityPolicy()) {
	  (((this.layoutInfo.visibleItemsInfo.lastOrNull()?.index
		?: -9) == this.layoutInfo.totalItemsCount.minus(3)))
	}
  }
}

fun NavController.navToDetailScreen(data: Article) {
  val content = data.content.encodeStringNavigation()
  val imageUrl =
	if (!data.image_url.isNullOrEmpty()) data.image_url.encodeStringNavigation() else null
  val title = data.title
  val pubDate = data.pubDate
  val articleId = data.article_id
  val link = data.link.encodeStringNavigation()
  val description = data.description?.encodeStringNavigation()
  val source = data.source_id
  this.navigate(
	"DetailScreen/${content}/${imageUrl}/${title}/${pubDate}/${articleId}/${link}/${description}/${source}"
  )
}

@Composable
@Preview(showBackground = true)
fun Previewshimmer() {
  NewsAppTheme {
	Spacer(
	  modifier = Modifier
		.size(100.dp, 50.dp)
		.shimmerEffect()
	)
  }
}