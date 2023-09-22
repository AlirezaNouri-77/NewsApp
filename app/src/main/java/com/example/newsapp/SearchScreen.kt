package com.example.newsapp

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun SearchScreen(
		modifier: Modifier
) {

		var textFeildValue by remember {
				mutableStateOf("")
		}

		LaunchedEffect(key1 = textFeildValue) {
				snapshotFlow {
						textFeildValue
				}.debounce(700L).collectLatest {

				}
		}

		Column(modifier = modifier) {
				TextField(
						value = textFeildValue,
						onValueChange = { textFeildValue = it },
						modifier = Modifier
								.fillMaxWidth()
								.height(50.dp),
						placeholder = { Text(text = "something like Apple, Sony, USA ...") },
						colors = TextFieldDefaults.textFieldColors(
								textColor = Color.Black,
								placeholderColor = Color.Black.copy(alpha = 0.8f),
								disabledIndicatorColor = Color.Transparent,
								unfocusedIndicatorColor = Color.Transparent,
								focusedIndicatorColor =  Color.Transparent,
								containerColor = Color.Transparent,
						),
				)
		}

}

@Preview
@Composable
fun PreviewSearchScreen() {
		NewsAppTheme {
				SearchScreen(modifier = Modifier)
		}
}