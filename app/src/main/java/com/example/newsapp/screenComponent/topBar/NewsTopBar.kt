package com.example.newsapp.screenComponent.topBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.newsapp.remote.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopBar(
		newsViewModel: NewsViewModel,
		clickOnSetting: () -> Unit = {},
) {
				TopAppBar(title = {
						Text(
								text = "News",
								fontWeight = FontWeight.Bold,
								fontSize = 26.sp,
								textAlign = TextAlign.Start,
						)
				}, actions = {

						AnimatedVisibility(
								visible = newsViewModel.isPaging.value,
								enter = fadeIn() + slideInVertically(tween(200), initialOffsetY = { -it * 2 }),
								exit = slideOutVertically(tween(500), targetOffsetY = { it * 2 }) + fadeOut()
						) {
								Text(
										modifier = Modifier.fillMaxWidth(),
										textAlign = TextAlign.Center,
										text = "Loading News",
										fontSize = 17.sp,
										fontWeight = FontWeight.SemiBold,
								)
						}

						IconButton(onClick = { clickOnSetting.invoke() }) {
								Icon(
										imageVector = Icons.Default.Settings,
										contentDescription = "",
								)
						}
				})
}