package com.example.newsapp.screenComponent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreenTopBar() {
		TopAppBar(
				title = {
						Text(
								text = "News",
								fontWeight = FontWeight.Bold,
								fontSize = 26.sp,
								modifier = Modifier.fillMaxWidth(),
								textAlign = TextAlign.Start,
						)
				},
				colors = TopAppBarDefaults.smallTopAppBarColors(
						containerColor = colorScheme.primary,
						titleContentColor = colorScheme.secondary
				),
		)
}