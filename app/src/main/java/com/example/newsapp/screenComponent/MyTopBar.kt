package com.example.newsapp.screenComponent

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
		var isShow by remember {
				mutableStateOf(false)
		}
		TopAppBar(
				title = { Text(text = "News", fontWeight = FontWeight.Bold) },
				colors = TopAppBarDefaults.smallTopAppBarColors(),
		)
}

@Preview
@Composable
fun PreviewMyTopBar() {
		NewsAppTheme {
				MyTopBar()
		}
}