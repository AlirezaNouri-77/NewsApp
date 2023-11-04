package com.example.newsapp.Presentation.ScreenComponent

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.Presentation.ViewModel.NewsViewModel
import com.example.newsapp.Presentation.util.noRippleClick

@Composable
fun ChipsCategory(
		newsViewModel: NewsViewModel,
		onClick: (String, Int) -> Unit,
) {

		val chipsArray = arrayOf(
				"Top",
				"Business",
				"Politics",
				"Entertainment",
				"Health",
				"Science",
				"Sports",
				"Technology",
				"Environment",
				"Food",
				"World",
				"Tourism",
		)

		Surface(
				color = MaterialTheme.colorScheme.background,
				contentColor = MaterialTheme.colorScheme.onBackground,
		) {
				LazyRow(
						Modifier.fillMaxWidth(),
						contentPadding = PaddingValues(5.dp),
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.Center,
				) {

						items(count = chipsArray.size) {
								ChipsDesign(
										text = chipsArray[it],
										onClick = {
												onClick.invoke(chipsArray[it], it)
												newsViewModel.newsCategoryState.intValue = it
										},
										index = it,
										itemSelected = newsViewModel.newsCategoryState.intValue,
								)
						}

				}
		}

}

@Composable
fun ChipsDesign(
		text: String,
		onClick: () -> Unit,
		index: Int,
		itemSelected: Int,
) {

		Surface(
				modifier = Modifier
						.noRippleClick {
								onClick.invoke()
						}
						.heightIn(min = 30.dp, max = 50.dp)
						.padding(end = 5.dp, start = 5.dp),
		) {

				val fontSize = if (index == itemSelected) 20.sp else 14.sp
				val fontColor =
						if (index == itemSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(
								alpha = 0.5f
						)
				val fontWeight = if (index == itemSelected) FontWeight.SemiBold else FontWeight.Medium

				AnimatedContent(
						targetState = fontSize,
						contentAlignment = Alignment.Center,
						transitionSpec = {
								ContentTransform(
										targetContentEnter = scaleIn(tween(300)),
										initialContentExit = scaleOut(tween(300))
								)
						},
						label = "",
				) {
						Text(
								text = text,
								fontSize = it,
								color = fontColor,
								fontWeight = fontWeight,
								textAlign = TextAlign.Center,
						)
				}
		}

}
