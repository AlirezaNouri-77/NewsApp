package com.example.newsapp.util

import android.util.Log
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

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

		val startx by transition.animateFloat(
				initialValue = -3 * size.width.toFloat(),
				targetValue = 3 * size.width.toFloat(),
				animationSpec = infiniteRepeatable(tween(durationMillis = 1200, delayMillis = 50)),
				label = "",
		)

		background(
				Brush.linearGradient(
						listOf(
								Color.LightGray.copy(0.5f),
								Color.DarkGray.copy(0.5f),
								Color.LightGray.copy(0.5f),
						),
						start = Offset(x = startx, y = 0f),
						end = Offset(x = startx + size.width.toFloat(), y = size.height.toFloat())
				)
		).onGloballyPositioned { size = it.size }

}

fun LazyListState.isBottomList(): State<Boolean> {
		return derivedStateOf {
				val layoutInfo = this.layoutInfo
				val visibleItemsInfo = layoutInfo.visibleItemsInfo
				if (layoutInfo.totalItemsCount == 0) {
						false
				} else {
						val lastVisibleItem = visibleItemsInfo.last()
						val viewportHeight = layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset
						(lastVisibleItem.index.plus(1) == layoutInfo.totalItemsCount &&
										(lastVisibleItem.offset) + lastVisibleItem.size <= viewportHeight)
				}
		}
}
