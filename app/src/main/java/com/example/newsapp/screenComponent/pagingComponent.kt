package com.example.newsapp.screenComponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.remote.model.BaseViewModelContract

@Composable
fun LoadingPagingItem() {
		Text(
				text = "Loading News",
				fontSize = 20.sp,
				fontWeight = FontWeight.SemiBold,
				textAlign = TextAlign.Center,
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
						.padding(10.dp)
						.height(30.dp),
		) {
				Text(
						text = "Failed To Load",
						fontSize = 16.sp,
						fontWeight = FontWeight.SemiBold,
				)
				Spacer(modifier = Modifier.width(10.dp))
				Button(onClick = {
						onClickTryAgain.invoke()
				}) {
						Text(text = "Try Again")
				}
		}
}
