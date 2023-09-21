package com.example.newsapp.screenComponent

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.util.noRippleClick

@Composable
fun ChipsCategory(
		itemIndex: Int,
		onClick: (String) -> Unit,
) {

		val itemSelect = remember {
				mutableIntStateOf(itemIndex)
		}

		val chipsArray = arrayOf(
				"All",
				"Top Headline",
				"Business",
				"Entertainment",
				"General",
				"Health",
				"Science",
				"Sports",
				"Technology"
		)

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
										itemSelect.intValue = it
										onClick.invoke(chipsArray[it])
								},
								index = it,
								itemSelected = itemSelect.intValue,
						)
				}
		}
}

@Composable
fun ChipsDesign(
		text: String, onClick: () -> Unit, index: Int, itemSelected: Int
) {
		Surface(modifier = Modifier
				.noRippleClick {
						onClick.invoke()
				}
				.heightIn(min = 30.dp, max = 50.dp)
				.padding(end = 5.dp, start = 5.dp)) {
				val fontSize = if (index == itemSelected) 16.sp else 14.sp
				val fontColor = if (index == itemSelected) Color.Black else Color.Black.copy(alpha = 0.6f)
				val fontWeight = if (index == itemSelected) FontWeight.Bold else FontWeight.Medium

				AnimatedContent(
						targetState = fontSize,
						label = "",
				) {
						Text(
								text = text, color = fontColor, fontSize = it, fontWeight = fontWeight
						)
				}

		}
}

@Preview
@Composable
fun PreviewChipsCategory() {
		NewsAppTheme {
				ChipsCategory(1, onClick = {})
		}
}

