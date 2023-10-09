package com.example.newsapp.screenComponent

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.util.noRippleClick

@Composable
fun ChipsCategory(
		newsViewModel: NewsViewModel,
		onClick: (String , Int) -> Unit,
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

		Surface (
				color = Color.White
		){
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
												onClick.invoke(chipsArray[it] , it)
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

		Surface(modifier = Modifier
				.noRippleClick {
						onClick.invoke()
				}
				.heightIn(min = 30.dp, max = 50.dp)
				.padding(end = 5.dp, start = 5.dp)) {

				val fontSize = if (index == itemSelected) 18.sp else 14.sp
				val fontColor = if (index == itemSelected) Color.Black else Color.Black.copy(alpha = 0.5f)
				val fontWeight = if (index == itemSelected) FontWeight.SemiBold else FontWeight.Medium

				AnimatedContent(
						targetState = fontSize,
						contentAlignment = Alignment.Center,
						label = "",
				) {
						Text(
								text = text,
								color = fontColor,
								fontSize = it,
								fontWeight = fontWeight,
								textAlign = TextAlign.Center,
						)
				}
		}

}
