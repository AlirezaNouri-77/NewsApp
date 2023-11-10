package com.example.newsapp.View.ScreenComponent.topBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopBar(
  clickOnSetting: () -> Unit = {},
) {
  TopAppBar(
	title = {
	  Text(
		text = "News",
		style = MaterialTheme.typography.titleLarge,
	  )
	},
	actions = {
	  IconButton(onClick = { clickOnSetting.invoke() }) {
		Icon(
		  imageVector = Icons.Default.Settings,
		  contentDescription = "",
		)
	  }
	},
	colors = TopAppBarDefaults.topAppBarColors(
	  containerColor = MaterialTheme.colorScheme.background,
	  titleContentColor = MaterialTheme.colorScheme.primary,
	  actionIconContentColor = MaterialTheme.colorScheme.onBackground
	),
  )
}