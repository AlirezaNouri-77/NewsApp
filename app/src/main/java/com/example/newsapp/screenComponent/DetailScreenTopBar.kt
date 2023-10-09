package com.example.newsapp.screenComponent

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenTopBar(
		onClickBack: () -> Unit
) {

		TopAppBar(
				title = {},
				navigationIcon = {
						Button(
								onClick = { onClickBack.invoke() } ,
								colors = ButtonDefaults.buttonColors(
										containerColor = Color.Transparent,
										contentColor = Color.Black,
								),
						) {
								Icon(
										imageVector = Icons.Rounded.KeyboardArrowLeft,
										contentDescription = "",
								)
						}
				},
		)

}