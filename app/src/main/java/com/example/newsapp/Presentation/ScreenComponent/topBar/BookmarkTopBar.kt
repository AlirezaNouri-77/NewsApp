package com.example.newsapp.Presentation.ScreenComponent.topBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkTopBar(
		clickOnDeleteAll : () -> Unit
) {
		TopAppBar(
				title = {
						Text(
								text = "Bookmark",
								style = MaterialTheme.typography.titleLarge,
						)
				},
				actions = {
						Image(
								painter = painterResource(id = R.drawable.icondelete),
								contentDescription = "",
								colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
								modifier = Modifier.clickable {
										clickOnDeleteAll.invoke()
								})
						Spacer(modifier = Modifier.width(5.dp))
				},
				colors = TopAppBarDefaults.topAppBarColors(
						containerColor = MaterialTheme.colorScheme.background,
						titleContentColor = MaterialTheme.colorScheme.primary,
						actionIconContentColor = MaterialTheme.colorScheme.onBackground
				),
		)
}