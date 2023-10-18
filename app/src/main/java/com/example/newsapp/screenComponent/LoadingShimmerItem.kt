package com.example.newsapp.screenComponent

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.util.shimmerEffect

@Composable
fun LoadingShimmer() {
		Column(
				modifier = Modifier.scrollable(rememberScrollState(), Orientation.Vertical, enabled = false)
		) {
				repeat(7) {
						LoadingShimmerItem()
				}
		}
}

@Composable
fun LoadingShimmerItem() {

		Row(
				modifier = Modifier
						.heightIn(min = 100.dp, max = 120.dp),
		) {
				Box(
						modifier = Modifier
								.fillMaxSize()
								.weight(0.3f)
								.padding(5.dp)
								.shimmerEffect(),
				)
				Column(
						modifier = Modifier
								.fillMaxSize()
								.padding(vertical = 2.dp, horizontal = 4.dp)
								.weight(0.7f),
				) {
						Box(
								modifier = Modifier
										.fillMaxWidth()
										.padding(4.dp)
										.shimmerEffect()
										.weight(0.2f),
						)
						Box(
								modifier = Modifier
										.fillMaxWidth()
										.padding(4.dp)
										.shimmerEffect()
										.weight(0.3f),
						)
						Box(
								modifier = Modifier
										.fillMaxWidth()
										.padding(4.dp)
										.shimmerEffect()
										.weight(0.5f),
						)
				}
		}

}