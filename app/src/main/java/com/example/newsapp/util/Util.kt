package com.example.newsapp.util

import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.example.newsapp.local.model.SettingDataClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
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
				initialValue = -3f * size.width.toFloat(),
				targetValue = 3f * size.width.toFloat(),
				animationSpec = infiniteRepeatable(
						tween(durationMillis = 2500, delayMillis = 30),
						repeatMode = RepeatMode.Restart,
				),
				label = "",
		)

		background(
				Brush.horizontalGradient(
						colors = listOf(
								Color.LightGray.copy(0.7f),
								Color.LightGray.copy(0.7f),
								Color.Black.copy(0.4f),
								Color.LightGray.copy(0.7f),
								Color.LightGray.copy(0.7f),
						),
						startX = size.width.toFloat() * -3f,
						endX = (transitionFloat + size.width.toFloat()) * 3f,
				),
		).onGloballyPositioned { size = it.size }

}

suspend inline fun <T> onIO(crossinline action: () -> T): T {
		return withContext(Dispatchers.IO) {
				action()
		}
}


fun List<SettingDataClass>.convertToString(): String {
		val output = StringBuilder()
		this.forEachIndexed { index, item ->
				output.append(item.code)
				if (this.size != index + 1) {
						output.append(",")
				}
		}
		return output.toString()
}

fun LazyListState.isBottomList(): State<Boolean> {
		return derivedStateOf {
				val layoutInfo = this.layoutInfo
				val visibleItemsInfo = layoutInfo.visibleItemsInfo
				if (layoutInfo.totalItemsCount == 0 || layoutInfo.totalItemsCount == 1) {
						false
				} else {
						val lastVisibleItem = visibleItemsInfo.last()
						val viewportHeight = layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset
						(lastVisibleItem.index == layoutInfo.totalItemsCount.minus(1) &&
										(lastVisibleItem.offset) + lastVisibleItem.size <= viewportHeight)
				}
		}
}
