package com.example.newsapp.screenComponent.topBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.R
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun SearchTopBar(
		onSearch: (String) -> Unit,
) {

		var textFiledValue by remember {
				mutableStateOf("")
		}

		LaunchedEffect(key1 = textFiledValue) {
				snapshotFlow {
						textFiledValue
				}.debounce(700L).collectLatest {
						if (it.length > 3) onSearch.invoke(it.trim())
				}
		}

		Column {
				TopAppBar(title = {
						Text(
								text = "Search",
								fontWeight = FontWeight.Bold,
								fontSize = 26.sp,
								textAlign = TextAlign.Start,
						)
				})
				TopAppBar(
						title = {},
						colors = TopAppBarDefaults.smallTopAppBarColors(),
						actions = {
								OutlinedTextField(
										value = textFiledValue,
										onValueChange = { textFiledValue = it },
										modifier = Modifier
												.fillMaxWidth()
												.padding(horizontal = 5.dp, vertical = 2.dp),
										placeholder = { Text(text = "something like Tech, Android, USA ...") },
										singleLine = false,
										colors = TextFieldDefaults.colors(
												focusedTextColor = Color.Black,
												focusedPlaceholderColor = Color.Black.copy(alpha = 0.5f),
												disabledIndicatorColor = Color.Black.copy(alpha = 0.5f),
												unfocusedIndicatorColor = Color.Black.copy(alpha = 0.5f),
												focusedIndicatorColor = Color.Black,
												unfocusedContainerColor = Color.Transparent,
										),
										leadingIcon = {
												Image(
														painter = painterResource(id = R.drawable.iconssearch),
														contentDescription = ""
												)
										},
										trailingIcon = {
												if (textFiledValue.isNotEmpty()) {
														Image(
																painter = painterResource(
																		id = R.drawable.iconclose
																),
																contentDescription = "",
																modifier = Modifier.clickable {
																		textFiledValue = ""
																}
														)
												}
										}
								)
						},
				)
		}
}
