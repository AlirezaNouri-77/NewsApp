package com.example.newsapp.screenComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoadingPagingItem() {
		Text(
				text = "Loading",
				fontFamily = FontFamily.Serif,
				fontSize = 16.sp,
				textAlign = TextAlign.Center,
				fontWeight = FontWeight.Bold,
				modifier = Modifier
						.fillMaxWidth()
						.padding(10.dp),
		)
}

@Composable
fun LoadingPagingItemFail(
		onClickTryAgain: () -> Unit,
) {
		Row(
				horizontalArrangement = Arrangement.Center,
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
						.fillMaxWidth()
						.padding(10.dp),
		) {
				Text(
						text = "Failed To Load News",
						fontSize = 15.sp,
						fontWeight = FontWeight.Medium,
				)
				Spacer(modifier = Modifier.width(2.dp))
				Button(
						onClick = {
								onClickTryAgain.invoke()
						}, colors = ButtonDefaults.buttonColors(
								containerColor = Color.Transparent,
								contentColor = MaterialTheme.colorScheme.error,
						)
				) {
						Text(text = "Try Again",modifier= Modifier.padding(5.dp))
				}
		}
}

@Composable
fun LoadingItemFail(
		clickOnTryAgain: () -> Unit,
		modifier: Modifier,
		massageText: String,
		showRetry: Boolean = true,
		iconInt: Int,
) {
		Column(
				modifier = modifier,
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally,
		) {
				Image(
						painter = painterResource(id = iconInt),
						contentDescription = "",
						modifier = Modifier
								.aspectRatio(4f)
								.padding(10.dp),
						colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error)
				)
				Text(
						text = massageText,
						fontSize = 18.sp,
						fontWeight = FontWeight.Medium,
						textAlign = TextAlign.Center,
						modifier = Modifier
								.fillMaxWidth(0.8f)
								.padding(vertical = 25.dp),
				)
				Spacer(modifier = Modifier.height(5.dp))
				if (showRetry) {
						Button(
								colors = ButtonDefaults.buttonColors(
										containerColor = MaterialTheme.colorScheme.errorContainer,
								),
								onClick = {
										clickOnTryAgain.invoke()
								},
						) {
								Text(text = "Try Again", color = MaterialTheme.colorScheme.onErrorContainer)
						}
				}
		}
}
